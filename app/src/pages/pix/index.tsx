import Box from "@mui/material/Box"
import Button from "@mui/material/Button"
import TextField from "@mui/material/TextField"
import Typography from "@mui/material/Typography"
import HeaderDetail from "components/header-detail"
import { usePixContext } from "context/pix/pixContext"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router"
import pixService from "services/pixService"
import { validateChavePix } from "utils/validations/index"

export default function Pix() {

    const navigate = useNavigate()
    const { setConta } = usePixContext()

    const [chavePix, setChavePix] = useState<string>("")
    const [isValid, setIsValid] = useState<boolean>(false)

    async function handleContinue() {
        if (!isValid) return

        const response = await pixService.obterContaPorChavePix(chavePix)
        if (response && response.id) {
            setConta(response)
            navigate("/pix/valor")
        }
    }

    useEffect(() => {
        const isValidChave = validateChavePix(chavePix)
        setIsValid(isValidChave)
    }, [chavePix])

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
                        value={chavePix}
                        onChange={(e) => setChavePix(e.target.value)}
                    />
                </Box>
                <Box className="p-4">
                    <Button
                        variant="contained"
                        color="primary"
                        size="large"
                        fullWidth
                        className="self-baseline"
                        disabled={!isValid}
                        onClick={handleContinue}
                    >
                        Continuar
                    </Button>
                </Box>
            </Box>
        </>
    )
}