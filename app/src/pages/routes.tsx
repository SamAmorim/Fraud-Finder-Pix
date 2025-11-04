import DefaultLayout from "components/layout/default"
import { createBrowserRouter } from "react-router"
import type { RouteHandle } from "typesrc/pages"
import Inicio from "."
import Pix from "./pix"
import PixValor from "./pix/valor"
import PixContextProvider from "components/providers/pix/PixProvider"

const routes = createBrowserRouter([
    {
        Component: DefaultLayout,
        children: [
            {
                index: true,
                Component: Inicio,
                handle: {
                    title: "Página Inicial"
                } as RouteHandle,
                path: "/",
            },
            {
                path: "/pix",
                Component: PixContextProvider,
                children: [
                    {
                        index: true,
                        Component: Pix,
                        handle: {
                            title: "Pix"
                        } as RouteHandle,
                        path: "/pix",
                    },
                    {
                        Component: PixValor,
                        handle: {
                            title: "Pix"
                        } as RouteHandle,
                        path: "/pix/valor",
                    },
                ]
            },
        ]
    }
])

export default routes