import { createContext, useContext } from "react"
import type { IPixContext } from "typesrc/context/pix/pixContext"

export const PixContext = createContext<IPixContext>({
    conta: undefined,
    setConta: () => { },
})

export const usePixContext = () => useContext(PixContext)
