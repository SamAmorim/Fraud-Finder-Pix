import os
import azure.functions as func
from datetime import datetime
import pandas as pd
import numpy as np
import mlflow
from pathlib import Path
import json
import logging

BASE_DIR = str(Path(__file__).parent)
BINARY_MODEL_PATH = os.path.join(BASE_DIR, "fraud_binary_pipeline")
LABEL_MAP_PATH = os.path.join(BASE_DIR, "fraud_type_label_map.json")

# ==========================================================
# 2. Carregamento dos modelos e metadados
# ==========================================================
logging.info(f"Carregando modelo binário de: {BINARY_MODEL_PATH}...")
model_binary = mlflow.sklearn.load_model(BINARY_MODEL_PATH)
logging.info("Modelo binário carregado com sucesso.")

logging.info(f"Carregando mapa de labels de: {LABEL_MAP_PATH}...")
with open(LABEL_MAP_PATH, "r") as f:
    label_map_raw = json.load(f)

# Normalizar o mapa de labels para int -> nome e criar lista ordenada de classes
label_map_int = {int(k): v for k, v in label_map_raw.items()}
multiclass_class_names = [label_map_int[k] for k in sorted(label_map_int.keys())]
logging.info(f"Mapa de labels carregado. Classes ordenadas: {multiclass_class_names}")

app = func.FunctionApp()

@app.route(route="analisar", auth_level=func.AuthLevel.ANONYMOUS, methods=["POST"])
def analisar(req: func.HttpRequest) -> func.HttpResponse:
    logging.info('Processing request...')

    try:
        req_body = req.get_json()
    except ValueError:
        return func.HttpResponse(
            json.dumps({
                "success": False,
                "message": "Invalid JSON payload. Request body must be a valid JSON object.",
                "details": None
            }),
            status_code=400,
            mimetype="application/json"
        )

    def _val(attr, default):
        v = getattr(req_body, attr, default)
        if isinstance(v, datetime):
            return v.isoformat()
        return v

    data = {
        "id_transacao": [_val("idTransacao", "t2-teste-real-fraude")],
        "pagador_conta_aberta_em": [_val("pagadorContaAbertaEm", "2023-05-10T10:00:00")],
        "pagador_segundos_desde_ultima_tx": [_val("pagadorSegundosDesdeUltimaTx", 300)],
        "pagador_data_nascimento": [_val("pagadorDataNascimento", "1999-07-15T00:00:00")],
        "valor_transacao": [_val("valorTransacao", 1200000.0)],
        "tipo_iniciacao_pix_id": [_val("tipoIniciacaoPixId", 2)],
        "recebedor_txs_ultima_1h": [_val("recebedorTxsUltima1h", 5)],
        "recebedor_idade_conta_dias": [_val("recebedorIdadeContaDias", 10)],
        "pagador_tipo_conta_id": [_val("pagadorTipoContaId", 2)],
        "pagador_valor_ultimas_24h": [_val("pagadorValorUltimas24h", 19500.0)],
        "pagador_interacoes_com_recebedor": [_val("pagadorInteracoesComRecebedor", 1)],
        "finalidade_pix_id": [_val("finalidadePixId", 3)],
        "recebedor_natureza_id": [_val("recebedorNaturezaId", 1)],
        "recebedor_valor_ultima_1h": [_val("recebedorValorUltima1h", 19500.0)],
        "recebedor_saldo": [_val("recebedorSaldo", 19500.0)],
        "recebedor_tipo_conta_id": [_val("recebedorTipoContaId", 2)],
        "pagador_idade_conta_dias": [_val("pagadorIdadeContaDias", 90)],
        "primeira_interacao": [_val("primeiraInteracao", 1)],
        "valor_vs_saldo_pagador": [_val("valorVsSaldoPagador", 0.9)],
        "recebedor_data_nascimento": [_val("recebedorDataNascimento", "2001-01-01T00:00:00")],
        "pagador_saldo": [_val("pagadorSaldo", 20000.0)],
        "pagador_txs_ultimas_24h": [_val("pagadorTxsUltimas24h", 1)],
        "recebedor_num_pagadores_unicos_24h": [_val("recebedorNumPagadoresUnicos24h", 1)],
        "recebedor_conta_aberta_em": [_val("recebedorContaAbertaEm", "2025-11-01T00:00:00")],
        "pagador_natureza_id": [_val("pagadorNaturezaId", 1)],
        "data_transacao": [_val("dataTransacao", "2025-11-08T23:30:00")],
        "valor_vs_media_pagador_30d": [_val("valorVsMediaPagador30d", 25.8)],
    }

    df_input = pd.DataFrame(data)
    fraud_threshold = 0.5

    logging.info(f"{len(df_input)} transação(s) recebida(s) para análise...")
    logging.info("DEBUG - df_input.dtypes:")
    logging.info(df_input.dtypes)
    logging.info("DEBUG - df_input.head():")
    logging.info(df_input.head().to_string())

    # ======================================================
    # Etapa 1: Modelo Binário (Detecção de Fraude)
    # ======================================================
    logging.info("\nExecutando modelo binário...")
    pred_binary_proba_array = model_binary.predict_proba(df_input)
    logging.info(f"DEBUG - Probabilidades retornadas pelo modelo binário: {pred_binary_proba_array}")

    # --- CORREÇÃO ROBUSTA PARA VÁRIOS FORMATOS DE SAÍDA ---
    proba = pred_binary_proba_array

    if isinstance(proba, np.ndarray) and proba.ndim == 2:
        # formato esperado: [[p0, p1]]
        pred_binary_proba_float = float(round(proba[0][1], 4))
    elif isinstance(proba, np.ndarray) and proba.ndim == 1 and len(proba) == 2:
        # formato: [p0, p1]
        pred_binary_proba_float = float(round(proba[1], 4))
    elif isinstance(proba, np.ndarray) and proba.ndim == 1 and len(proba) == 1:
        # formato: [p1] - probabilidade direta
        pred_binary_proba_float = float(round(proba[0], 4))
    else:
        # Pode ser float ou outro tipo; forçar float
        pred_binary_proba_float = float(round(float(proba), 4))

    logging.info(f"DEBUG - Probabilidade normalizada (fraude=1): {pred_binary_proba_float}")

    pred_binary_label = int(pred_binary_proba_float >= fraud_threshold)
    logging.info(f"Probabilidade de fraude (classe=1): {pred_binary_proba_float}")
    logging.info(f"Predição binária final: {'FRAUDE' if pred_binary_label == 1 else 'LEGÍTIMA'}")

    # ======================================================
    # Etapa 2: Modelo Multiclasse (Classificação do Tipo de Fraude)
    # ======================================================
    pred_multi_label = None
    pred_multi_proba = None
    pred_multi_proba_dict = None

    if pred_binary_label == 1:
        logging.info("\nExecutando modelo multiclasse (tipo de fraude)...")
        pred_multi_proba_array = [0.3] # model_multiclass.predict_proba(df_input)
        logging.info(f"DEBUG - Probabilidades retornadas pelo modelo multiclasse: {pred_multi_proba_array}")

        # Normalizar para vetor de probabilidades por classes
        if isinstance(pred_multi_proba_array, np.ndarray) and pred_multi_proba_array.ndim == 2:
            prob_array = pred_multi_proba_array[0]
        elif isinstance(pred_multi_proba_array, np.ndarray) and pred_multi_proba_array.ndim == 1:
            prob_array = pred_multi_proba_array
        else:
            prob_array = np.array(pred_multi_proba_array).ravel()

        # Caso o número de probabilidades não bata com o número de classes,
        # cortamos/expandimos com zeros (defensivo).
        n_classes = len(multiclass_class_names)
        if prob_array.size != n_classes:
            logging.info(f"Aviso: número de probabilidades ({prob_array.size}) != número de classes ({n_classes}). Ajustando de forma defensiva.")
            # Ajustar: se houver mais probs, truncar; se houver menos, preencher com zeros
            if prob_array.size > n_classes:
                prob_array = prob_array[:n_classes]
            else:
                prob_array = np.concatenate([prob_array, np.zeros(n_classes - prob_array.size)])

        # Mapear nome_da_classe -> probabilidade
        pred_multi_proba_dict = {
            multiclass_class_names[i]: float(round(float(prob_array[i]), 4))
            for i in range(len(multiclass_class_names))
        }

        # Escolher label e confiança
        argmax_idx = int(np.argmax(prob_array))
        pred_multi_label = multiclass_class_names[argmax_idx]
        pred_multi_proba = float(round(float(prob_array[argmax_idx]), 4))

        logging.info(f"Tipo de fraude previsto: {pred_multi_label} (confiança={pred_multi_proba})")
        logging.info(f"Probabilidades por tipo: {pred_multi_proba_dict}")
    else:
        logging.info("Transação legítima — modelo multiclasse não executado.")

    result = {
        "path": LABEL_MAP_PATH,
        "isFraud": bool(pred_binary_label),
        "fraudProbability": pred_binary_proba_float,
        "fraudType": pred_multi_label,
        "fraudTypeConfidence": pred_multi_proba,
        "fraudTypeProbabilities": pred_multi_proba_dict,
        "timestampInferencia": datetime.now().isoformat()
    }

    logging.info('Resultado final:')
    logging.info(json.dumps(data, indent=4, ensure_ascii=False))

    return func.HttpResponse(
        json.dumps({
            "success": True,
            "message": "Analysis completed.",
            "details": result
        }),
        status_code=200,
        mimetype="application/json"
    )
