import azure.functions as func
import logging
import json
from pydantic import BaseModel, ValidationError
from application.services import analise_service

app = func.FunctionApp()

class AccountData(BaseModel):
    id: int
    name: str
    account_type: str

@app.route(route="analisar", methods=["POST"])
def analisar(req: func.HttpRequest) -> func.HttpResponse:
    logging.info('Python HTTP trigger function processed a request.')

    analise_service.rodar()

    # try:
    #     req_body = req.get_json()
    # except ValueError:
    #     return func.HttpResponse(
    #         json.dumps({
    #             "status": "error",
    #             "message": "Invalid JSON payload. Request body must be a valid JSON object."
    #         }),
    #         status_code=400,
    #         mimetype="application/json"
    #     )

    # try:
    #     data = AccountData(**req_body)
    # except ValidationError as e:
    #     return func.HttpResponse(
    #         json.dumps({
    #             "status": "error",
    #             "message": "Invalid input.",
    #             "details": e.errors()
    #         }),
    #         status_code=400,
    #         mimetype="application/json"
    #     )

    # return func.HttpResponse(
    #     json.dumps({
    #         "status": "success",
    #         "message": "Account data received.",
    #         "data": data.model_dump()
    #     }),
    #     status_code=200,
    #     mimetype="application/json"
    # )
