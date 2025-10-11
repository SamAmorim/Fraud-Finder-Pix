import Box from "@mui/material/Box"
import Header from "components/header"
import { Outlet } from "react-router"

export default function DefaultLayout() {

    return (
        <Box overflow="hidden" className="flex flex-col h-screen w-screen">
            <Header />
            <Box
                component="main"
                className="flex-1 w-full md:max-w-md mx-auto overflow-auto"
            >
                <Outlet />
            </Box>
        </Box>
    )
}