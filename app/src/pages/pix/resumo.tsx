import { Alert, CircularProgress } from "@mui/material"
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

export default function PixResumo() {

    const navigate = useNavigate()
    const { setContaDestino } = usePixContext()

    const [chavePix, setChavePix] = useState<string>("")
    const [isValid, setIsValid] = useState<boolean>(false)
    const [isLoading, setIsLoading] = useState<boolean>(false)
    const [errorMessage, setErrorMessage] = useState<string>("")

    useEffect(() => {
        if (errorMessage)
            setErrorMessage("")

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
                        Resumo da transferência
                    </Typography>
                    {errorMessage &&
                        <Alert severity="error">
                            {errorMessage}
                        </Alert>
                    }
                </Box>
            </Box>
        </>
    )
}