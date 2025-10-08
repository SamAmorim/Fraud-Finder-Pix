import { createBrowserRouter } from "react-router"
import Inicio from "."

const routes = createBrowserRouter([
    {
        Component: Inicio,
        path: "/"
    }
])

export default routes