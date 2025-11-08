import azure.functions as func
import logging
import sys
import json
from pydantic import ValidationError
from application.services import analise_service
from pathlib import Path
from application.models.transacao import Transacao

# Add project root to Python path
project_root = str(Path(__file__).resolve().parents[2])  # Adjust the number based on your folder structure
if project_root not in sys.path:
    sys.path.append(project_root)

app = func.FunctionApp()

@app.route(route="analisar", methods=["POST"])
def analisar(req: func.HttpRequest) -> func.HttpResponse:
    logging.info('Python HTTP trigger function processed a request.')

    try:
        req_body = req.get_json()
    except ValueError:
        return func.HttpResponse(
            json.dumps({
                "status": "error",
                "message": "Invalid JSON payload. Request body must be a valid JSON object."
            }),
            status_code=400,
            mimetype="application/json"
        )

    try:
        data = Transacao(**req_body)
    except ValidationError as e:
        return func.HttpResponse(
            json.dumps({
                "status": "error",
                "message": "Invalid input.",
                "details": e.errors()
            }),
            status_code=400,
            mimetype="application/json"
        )

    analise_service.rodar(data)

    return func.HttpResponse(
        json.dumps({
            "status": "success",
            "message": "Account data received.",
            "data": data.model_dump()
        }),
        status_code=200,
        mimetype="application/json"
    )
