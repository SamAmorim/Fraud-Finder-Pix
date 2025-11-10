import { PixContext } from "context/pix/pixContext"
import { useState } from "react"
import type { IPixContext } from "typesrc/context/pix/pixContext"

export default function PixContextProvider({
    children
}: {
    children: React.ReactNode
}) {

    const [contaDestino, setContaDestino] = useState<IPixContext["contaDestino"]>({
        id: "teste",
        nomeCliente: "João da Silva",
        registroNacionalCliente: "123.456.789-00",
        nomeInstituicao: "Banco Exemplo",
        ispbInstituicao: "12345678",
    })
    const [contaOrigemSelecionada, setContaOrigemSelecionada] = useState<IPixContext["contaOrigemSelecionada"]>(undefined)

    return (
        <PixContext.Provider
            value={{
                contaDestino,
                setContaDestino,
                contaOrigemSelecionada,
                setContaOrigemSelecionada,
            }}
        >
            {children}
        </PixContext.Provider>
    )
}
