import { PixContext } from "context/pix/pixContext"
import { useState } from "react"
import { Outlet } from "react-router"
import type { IPixContext } from "typesrc/context/pix/pixContext"

export default function PixContextProvider() {

    const [pixConta, setPixConta] = useState<IPixContext["conta"]>({
        id: "teste",
        nomeCliente: "João da Silva",
        registroNacionalCliente: "123.456.789-00",
        nomeInstituicao: "Banco Exemplo",
        ispbInstituicao: "12345678",
    })

    return (
        <PixContext.Provider
            value={{
                conta: pixConta,
                setConta: setPixConta,
            }}
        >
            <Outlet />
        </PixContext.Provider>
    )
}
