import type { ObterContaPorChavePixResponse } from "typesrc/services/pixService/ObterContaPorChavePixResponse"
import API from "./api"

async function obterContaPorChavePix(chavePix: string): Promise<ObterContaPorChavePixResponse> {
    try {
        const { data } = await API.pixApi.get<ObterContaPorChavePixResponse>(`/contas/chavePix/${encodeURIComponent(chavePix)}`)
        return data
    }
    catch (error: unknown) {
        console.error("Erro ao obter conta por chave Pix:", error)
        if (error instanceof Error && (error as any).response?.status === 404) {
            throw new Error('Conta não encontrada para a chave informada.')
        }
        throw new Error('Erro ao obter conta por chave Pix.')
    }
}

export default {
    obterContaPorChavePix,
}