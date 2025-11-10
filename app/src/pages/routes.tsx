import DefaultLayout from "components/layout/default"
import { createBrowserRouter } from "react-router"
import type { RouteHandle } from "typesrc/pages"
import Inicio from "."
import Pix from "./pix"
import PixResumo from "./pix/resumo"
import PixValor from "./pix/valor"

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
                    {
                        Component: PixResumo,
                        handle: {
                            title: "Pix"
                        } as RouteHandle,
                        path: "/pix/resumo",
                    },
                ]
            },
        ]
    }
])

export default routes