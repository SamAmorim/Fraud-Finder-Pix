export interface IPixConta {
    id: string
    nomeCliente: string
    registroNacionalCliente: string
    ispbInstituicao: string
    nomeInstituicao: string
}

export interface IPixContext {
    conta?: IPixConta
    setConta: (conta?: IPixConta) => void
}