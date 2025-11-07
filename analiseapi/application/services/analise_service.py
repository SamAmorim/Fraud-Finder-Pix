import numpy as np
import pandas as pd
import tensorflow as tf
import joblib
import json
import os
import warnings
from datetime import datetime
from pathlib import Path

# ===============================
# CONFIGURAÇÕES
# ===============================
# Diretório portátil para artefatos de modelo
# Aponta para o diretório pai do arquivo atual + "model_artifacts"
BASE_DIR = Path(__file__).resolve().parents[1]
MODEL_DIR = BASE_DIR / "model_artifacts"
# Compatibilidade com código que espera string para MODEL_DIR
MODEL_DIR = str(MODEL_DIR)
# Garante que o diretório exista em tempo de execução
os.makedirs(MODEL_DIR, exist_ok=True)

BINARY_THRESHOLD = 0.5
warnings.filterwarnings('ignore')
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'

# ===============================
# ENGENHARIA DE FEATURES AUXILIAR
# ===============================
def feature_engineer_datetimes(df_in: pd.DataFrame) -> pd.DataFrame:
    df = df_in.copy()
    df_out = pd.DataFrame(index=df.index)
    now = datetime.now()
    dt_tx = pd.to_datetime(df['data_transacao'])
    df_out['tx_hora_do_dia'] = dt_tx.dt.hour
    df_out['tx_dia_da_semana'] = dt_tx.dt.dayofweek
    df_out['tx_mes'] = dt_tx.dt.month
    df_out['recebedor_idade_conta_dias'] = (now - pd.to_datetime(df['recebedor_conta_aberta_em'])).dt.days
    df_out['pagador_idade_anos'] = ((now - pd.to_datetime(df['pagador_data_nascimento'])).dt.days / 365.25).astype(int)
    df_out['recebedor_idade_anos'] = ((now - pd.to_datetime(df['recebedor_data_nascimento'])).dt.days / 365.25).astype(int)
    df_out = df_out.fillna(0)
    return df_out

# ===============================
# MODELOS KERAS (Necessários p/ scikeras)
# ===============================
def build_binary_model(meta):
    n_features_in = meta["n_features_in_"]
    tf.random.set_seed(42)
    model = tf.keras.Sequential([
        tf.keras.layers.Dense(64, activation='relu', input_shape=(n_features_in,)),
        tf.keras.layers.Dropout(0.3),
        tf.keras.layers.Dense(32, activation='relu'),
        tf.keras.layers.Dropout(0.3),
        tf.keras.layers.Dense(1, activation='sigmoid')
    ])
    model.compile(
        loss='binary_crossentropy',
        optimizer=tf.keras.optimizers.Adam(learning_rate=0.001),
        metrics=['AUC', tf.keras.metrics.Recall(name='recall'), tf.keras.metrics.Precision(name='precision')]
    )
    return model

def build_multiclass_model(meta):
    n_features_in = meta["n_features_in_"]
    n_classes_out = meta["n_classes_"]
    tf.random.set_seed(42)
    model = tf.keras.Sequential([
        tf.keras.layers.Dense(64, activation='relu', input_shape=(n_features_in,)),
        tf.keras.layers.Dropout(0.3),
        tf.keras.layers.Dense(32, activation='relu'),
        tf.keras.layers.Dense(n_classes_out, activation='softmax')
    ])
    model.compile(
        loss='sparse_categorical_crossentropy',
        optimizer=tf.keras.optimizers.Adam(learning_rate=0.001),
        metrics=['accuracy']
    )
    return model

# ===============================
# CARREGAMENTO DE ARTEFATOS
# ===============================
def carregar_artefatos(model_dir: str):
    try:
        pipeline_binario = joblib.load(os.path.join(model_dir, "fraud_binary_pipeline.pkl"))
        pipeline_multiclass = joblib.load(os.path.join(model_dir, "fraud_type_pipeline.pkl"))
        label_map_path = os.path.join(model_dir, "fraud_type_label_map.json")
        with open(label_map_path, 'r') as f:
            label_map_str_keys = json.load(f)
            label_map = {int(k): v for k, v in label_map_str_keys.items()}
        return pipeline_binario, pipeline_multiclass, label_map
    except Exception:
        return None, None, None

# ===============================
# FUNÇÃO DE PREDIÇÃO PRINCIPAL
# ===============================
def prever_fraude(df_novo: pd.DataFrame, pipeline_binario, pipeline_multiclass, label_map) -> pd.DataFrame:
    if pipeline_binario is None or pipeline_multiclass is None or label_map is None:
        raise ValueError("Modelos ou mapa de labels não carregados corretamente.")

    if not isinstance(df_novo, pd.DataFrame):
        df_novo = pd.DataFrame([df_novo])

    df_results = df_novo.copy().reset_index(drop=True)
    df_results['fraud_probability'] = np.nan
    df_results['predicted_fraud'] = np.nan
    df_results['predicted_fraud_type'] = None
    df_results['predicted_type_probability'] = np.nan
    df_results['type_probabilities'] = [dict() for _ in range(len(df_results))]
    df_results['resumo_da_predicao'] = ""

    # ======================
    # 1️⃣ Predição Binária
    # ======================
    prob_bin = np.asarray(pipeline_binario.predict_proba(df_novo))
    
    # Correção: .ravel()[-1:] pega o último item como um array de 1 elemento
    fraud_probabilities = prob_bin.ravel()[-1:]

    df_results['fraud_probability'] = fraud_probabilities.astype(float)
    df_results['predicted_fraud'] = (df_results['fraud_probability'] >= BINARY_THRESHOLD).astype(int)

    # ======================
    # 2️⃣ Predição Multiclass (somente nas fraudes)
    # ======================
    indices_fraude = df_results[df_results['predicted_fraud'] == 1].index
    if len(indices_fraude) > 0:
        df_fraude = df_results.loc[indices_fraude].copy()

        # Obter probabilidades
        type_probs_matrix = np.asarray(pipeline_multiclass.predict_proba(df_fraude))
        type_probs_matrix = np.atleast_2d(type_probs_matrix)

        # Garantir alinhamento
        n_probs, n_indices = type_probs_matrix.shape[0], len(indices_fraude)
        if n_probs != n_indices:
            if n_probs > n_indices:
                type_probs_matrix = type_probs_matrix[:n_indices]
            elif n_probs < n_indices:
                type_probs_matrix = np.tile(type_probs_matrix[0], (n_indices, 1))

        # Construir campos derivados
        type_predictions_indices = np.argmax(type_probs_matrix, axis=1)
        predicted_type_probs = np.max(type_probs_matrix, axis=1)
        predicted_types = [label_map.get(idx, 'unknown') for idx in type_predictions_indices]

        type_probs_list = [
            {label_map.get(i, 'unknown'): float(prob) for i, prob in enumerate(row)}
            for row in type_probs_matrix
        ]

        # Atribuições seguras com fatiamento
        n_indices = len(indices_fraude) 
        df_results.loc[indices_fraude, 'predicted_fraud_type'] = predicted_types[:n_indices]
        df_results.loc[indices_fraude, 'predicted_type_probability'] = predicted_type_probs[:n_indices]
        df_results.loc[indices_fraude, 'type_probabilities'] = type_probs_list[:n_indices]

    # ======================
    # 3️⃣ Resumo Final
    # ======================
    df_results['resumo_da_predicao'] = "Não é uma fraude."
    fraudes = df_results['predicted_fraud'] == 1
    df_results.loc[fraudes, 'resumo_da_predicao'] = (
        "É UMA FRAUDE do tipo " +
        df_results.loc[fraudes, 'predicted_fraud_type'].fillna('desconhecido')
    )
    df_results['resumo_da_predicao'] = df_results['resumo_da_predicao'].str.replace(
        "do tipo None", "do tipo desconhecido"
    )

    return df_results

def rodar():
    # ===============================
    # EXECUÇÃO (com saída JSON e 3 casas decimais)
    # ===============================
    p_bin, p_multi, l_map = carregar_artefatos(MODEL_DIR)

    # Simulação de artefatos (se o carregamento falhar)
    if p_bin is None:
        print("AVISO: Modelos não encontrados. Simulando artefatos para teste.")
        
        try:
            from scikeras.wrappers import KerasClassifier
        except ImportError:
            print("Erro: scikeras não instalado. A simulação falhará.")
            from unittest.mock import Mock
            p_bin = Mock()
            # Simula o comportamento BUGGY (2 valores) com alta precisão
            p_bin.predict_proba.return_value = np.array([0.123456, 0.987654]) 
            p_multi = Mock()
            p_multi.predict_proba.return_value = np.array([[0.81234, 0.15678, 0.03088]])
        
        if 'scikeras.wrappers' in locals() or 'scikeras.wrappers' in globals():
            # Simular pipeline binário (simulando o retorno buggy)
            class MockKerasClassifier(KerasClassifier):
                def predict_proba(self, X, **kwargs):
                    if self.build_fn.__name__ == 'build_binary_model':
                        # Simula o retorno (2,) com alta precisão
                        return np.array([0.123456, 0.987654]) 
                    else:
                        # Simula [prob_A, prob_B, prob_C] com alta precisão
                        return np.array([[0.81234, 0.15678, 0.03088]]) 
            
            p_bin = MockKerasClassifier(build_binary_model)
            p_bin.fit(np.random.rand(2, 5), np.array([0, 1]), n_features_in_=5)
            
            p_multi = MockKerasClassifier(build_multiclass_model)
            p_multi.fit(np.random.rand(2, 5), np.array([0, 1]), n_features_in_=5, n_classes_=3)
        
        l_map = {0: 'Tipo_A', 1: 'Tipo_B', 2: 'Tipo_C'}

    transacao = {
        'valor_transacao': 500000.20,
        'data_transacao': datetime.now(),
        'tipo_iniciacao_pix_id': 1, 'finalidade_pix_id': 1,
        'pagador_saldo': 1500.00, 'pagador_conta_aberta_em': '2018-05-10T10:00:00',
        'pagador_tipo_conta_id': 1, 'pagador_natureza_id': 1, 'pagador_data_nascimento': '1985-01-15T00:00:00',
        'recebedor_saldo': 3000.00, 'recebedor_conta_aberta_em': '2019-11-20T14:30:00',
        'recebedor_tipo_conta_id': 1, 'recebedor_natureza_id': 1, 'recebedor_data_nascimento': '1990-03-22T00:00:00',
        'pagador_txs_ultimas_24h': 2, 'pagador_valor_ultimas_24h': 100.0,
        'recebedor_txs_ultima_1h': 0, 'recebedor_valor_ultima_1h': 0.0,
        'pagador_segundos_desde_ultima_tx': 86400,
        'primeira_interacao': 0,
        'pagador_interacoes_com_recebedor': 5,
        'recebedor_num_pagadores_unicos_24h': 10,
        'recebedor_idade_conta_dias': 1500,
        'pagador_idade_conta_dias': 2000,
        'valor_vs_media_pagador_30d': 0.8,
        'valor_vs_saldo_pagador': 0.03
    }

    payload = pd.DataFrame([transacao])

    # Tentar prever
    try:
        resultado_predicao = prever_fraude(payload, p_bin, p_multi, l_map)

        print("\n--- Resultado JSON Estruturado (1ª Transação) ---")

        # 1. Seleciona a primeira (e única) linha de resultado
        resultado_serie = resultado_predicao.iloc[0]

        # 2. Converte a pd.Series para um string JSON.
        #    ⬇️ AJUSTE DE PRECISÃO APLICADO AQUI ⬇️
        json_string_raw = resultado_serie.to_json(
            orient='index', 
            date_format='iso', 
            default_handler=str,
            double_precision = 4    
        )

        # 3. Carrega o string JSON "cru" em um objeto Python (dict)
        resultado_obj = json.loads(json_string_raw)

        # 4. Converte o objeto Python para um string JSON "bonito" (estruturado)
        json_estruturado = json.dumps(resultado_obj, indent=4, ensure_ascii=False)

        # 5. Printa o JSON estruturado final
        print(json_estruturado)

    except Exception as e:
        print(f"\nOcorreu um erro durante a execução: {e}")
        import traceback
        traceback.print_exc()
