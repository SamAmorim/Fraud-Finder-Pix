import type { ObterContaPorChavePixResponse } from "typesrc/services/pixService/ObterContaPorChavePixResponse"
import API from "./api"

async function obterContaPorChavePix(chavePix: string): Promise<ObterContaPorChavePixResponse> {
    const { data } = await API.pixApi.get<ObterContaPorChavePixResponse>(`/contas/chavePix/${encodeURIComponent(chavePix)}`)
    return data
}

export default {
    obterContaPorChavePix,
}