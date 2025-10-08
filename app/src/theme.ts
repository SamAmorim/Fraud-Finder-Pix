import { createTheme } from "@mui/material/styles"

const theme = createTheme({
    palette: {
        mode: "light",
        primary: {
            main: "#e1173f",
            contrastText: "#ffffff"
        },
        secondary: {
            main: "#b81570",
            contrastText: "#ffffff"
        },
        background: {
            default: "#ffffff",
            paper: "#f0f1f5"
        },
        text: {
            primary: "#535353",
            secondary: "#47484c"
        },
        info: {
            main: "#ffffff",
            contrastText: "#b81570"
        }
    },
})

export default theme