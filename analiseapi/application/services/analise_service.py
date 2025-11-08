# =============================================================================
# SCRIPT DE INFERÊNCIA COMPLETO (COM CORREÇÃO DE DIMENSÃO)
# =============================================================================
import numpy as np
import pandas as pd
import tensorflow as tf
import joblib
import json
import os
import warnings
from datetime import datetime
from pathlib import Path
from sklearn.base import BaseEstimator, TransformerMixin
from application.utilities.feature_engineer import DatetimeFeatureEngineer
from application.models.transacao import Transacao

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
# DEFINIÇÕES DOS MODELOS KERAS
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
# FUNÇÃO DE CARREGAMENTO DE ARTEFATOS
# ===============================
def carregar_artefatos(model_dir: str):
    try:
        p_binario_path = os.path.join(model_dir, "fraud_binary_pipeline.pkl")
        p_multi_path = os.path.join(model_dir, "fraud_type_pipeline.pkl")
        label_map_path = os.path.join(model_dir, "fraud_type_label_map.json")

        pipeline_binario = joblib.load(p_binario_path)
        pipeline_multiclass = joblib.load(p_multi_path)
        
        with open(label_map_path, 'r') as f:
            label_map_str_keys = json.load(f)
            label_map = {int(k): v for k, v in label_map_str_keys.items()}
            
        print("Artefatos carregados com sucesso.")
        return pipeline_binario, pipeline_multiclass, label_map
        
    except FileNotFoundError as e:
        print(f"Erro: Arquivo não encontrado. Verifique o MODEL_DIR. Detalhes: {e}")
        return None, None, None
    except Exception as e:
        print(f"Erro inesperado ao carregar artefatos: {e}")
        import traceback
        traceback.print_exc()
        return None, None, None

# ===============================
# FUNÇÃO DE PREDIÇÃO PRINCIPAL (CORRIGIDA)
# ===============================
def prever_fraude(df_novo: pd.DataFrame, pipeline_binario, pipeline_multiclass, label_map) -> pd.DataFrame:
    """
    Executa o pipeline de predição de dois estágios em um novo DataFrame de transações.
    """
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
    # O predict_proba retorna [prob_classe_0, prob_classe_1]
    prob_bin_matrix = np.asarray(pipeline_binario.predict_proba(df_novo))
    
    if prob_bin_matrix.ndim == 1:

        fraud_probabilities = prob_bin_matrix[1:] # Retorna [0.9] (um array 1D)
    else:

        fraud_probabilities = prob_bin_matrix[:, 1]

    df_results['fraud_probability'] = fraud_probabilities.astype(float)
    df_results['predicted_fraud'] = (df_results['fraud_probability'] >= BINARY_THRESHOLD).astype(int)

    # ======================
    # 2️⃣ Predição Multiclass (somente nas fraudes)
    # ======================
    indices_fraude = df_results[df_results['predicted_fraud'] == 1].index
    
    if len(indices_fraude) > 0:
        df_fraude_para_prever = df_novo.loc[indices_fraude].copy()

        type_probs_matrix = np.asarray(pipeline_multiclass.predict_proba(df_fraude_para_prever))
        type_probs_matrix = np.atleast_2d(type_probs_matrix) 

        type_predictions_indices = np.argmax(type_probs_matrix, axis=1)
        predicted_type_probs = np.max(type_probs_matrix, axis=1)
        predicted_types = [label_map.get(idx, 'unknown') for idx in type_predictions_indices]

        type_probs_list = [
            {label_map.get(i, 'unknown'): float(prob) for i, prob in enumerate(row)}
            for row in type_probs_matrix
        ]

        df_results.loc[indices_fraude, 'predicted_fraud_type'] = predicted_types
        df_results.loc[indices_fraude, 'predicted_type_probability'] = predicted_type_probs
        for i, idx_loc in enumerate(indices_fraude):
            df_results.at[idx_loc, 'type_probabilities'] = type_probs_list[i]

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

def rodar(transacao: Transacao):
    print("Tentando carregar artefatos...")
    p_bin, p_multi, l_map = carregar_artefatos(MODEL_DIR)
    
    payload_df = pd.DataFrame([transacao])

    if p_bin is not None:
        try:
            print("\nExecutando predição...")
            resultado_predicao = prever_fraude(payload_df, p_bin, p_multi, l_map)

            print("\n--- Resultado JSON Estruturado (1ª Transação) ---")

            resultado_serie = resultado_predicao.iloc[0]

            json_string_raw = resultado_serie.to_json(
                orient='index', 
                date_format='iso', 
                default_handler=str,
                double_precision = 4    
            )

            resultado_obj = json.loads(json_string_raw)
            json_estruturado = json.dumps(resultado_obj, indent=4, ensure_ascii=False)
            print(json_estruturado)

        except Exception as e:
            print(f"\nOcorreu um erro durante a predição: {e}")
            import traceback
            traceback.print_exc()
    else:
        print("\nErro: Modelos não foram carregados. A predição não pode ser executada.")
        print("Verifique o caminho em MODEL_DIR e se os arquivos .pkl e .json existem.")