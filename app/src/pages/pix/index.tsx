import Box from "@mui/material/Box"
import Button from "@mui/material/Button"
import TextField from "@mui/material/TextField"
import Typography from "@mui/material/Typography"
import HeaderDetail from "components/header-detail"

export default function Pix() {

    return (
        <>
            <HeaderDetail height={200} />
            <Box className="flex flex-col h-full">
                <Box className="flex flex-1 flex-col p-6 gap-6 h-full overflow-auto">
                    <Typography
                        variant="h1"
                        className="mb-12"
                        color="primary.contrastText"
                    >
                        Como você quer transferir?
                    </Typography>
                    <TextField
                        fullWidth
                        placeholder="E-mail, CPF ou telefone"
                        label="Digitar a chave Pix"
                    />
                </Box>
                <Box className="p-4">
                    <Button
                        variant="contained"
                        color="primary"
                        size="large"
                        fullWidth
                        className="self-baseline"
                    >
                        Continuar
                    </Button>
                </Box>
            </Box>
        </>
    )
}