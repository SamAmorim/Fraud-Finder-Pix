import Box from "@mui/material/Box"
import Button from "@mui/material/Button"
import TextField from "@mui/material/TextField"
import Typography from "@mui/material/Typography"
import HeaderDetail from "components/header-detail"
import { useEffect, useState } from "react"
import { validateChavePix } from "../../util/validations/index"
import { useNavigate } from "react-router"

export default function Pix() {

    const navigate = useNavigate()
    
    const [chavePix, setChavePix] = useState<string>("")
    const [isValid, setIsValid] = useState<boolean>(false)

    function handleContinue() {
        if (isValid)
            navigate("/pix/valor", { state: { chavePix } })
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