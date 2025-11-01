import Avatar from "@mui/material/Avatar"
import Box from "@mui/material/Box"
import Button from "@mui/material/Button"
import Card from "@mui/material/Card"
import CardContent from "@mui/material/CardContent"
import CardMedia from "@mui/material/CardMedia"
import Link from "@mui/material/Link"
import TextField from "@mui/material/TextField"
import Typography from "@mui/material/Typography"
import HeaderDetail from "components/header-detail"
import Icon from "components/icon"
import { useEffect, useState } from "react"
import { useLocation } from "react-router"
import pixService from "../../services/pixService"
import type { ObterContaPorChavePixResponse } from "typesrc/services/pixService/ObterContaPorChavePixResponse"

export default function PixValor() {

    const { state } = useLocation()

    const [conta, setConta] = useState<ObterContaPorChavePixResponse>()

    async function fetchConta() {
        console.log(state)
        if (state && state.chavePix) {
            const response = await pixService.obterContaPorChavePix(state.chavePix)
            setConta(response)
        }
    }

    useEffect(() => {
        fetchConta()
    }, [])

    return (
            <>
                <HeaderDetail />
                <Box className="flex flex-col h-full">
                    <Box className="flex flex-1 flex-col p-4 gap-6 h-full overflow-auto">
                        <Link color="primary.contrastText">
                            <Icon className="text-[length:inherit]!">info</Icon> Horários, limites e outras informações.
                        </Link>
                        <Card className="flex h-max">
                            <CardMedia className="flex items-center p-4">
                                <Avatar
                                    className="bg-green-100"
                                >
                                    <Icon className="text-green-500">
                                        attach_money
                                    </Icon>
                                </Avatar>
                            </CardMedia>
                            <CardContent>
                                <Typography>
                                    Pix para: <span className="font-semibold">{conta?.nomeCliente}</span>
                                </Typography>
                                <Typography>
                                    CPF/CNPJ: <span className="font-semibold">{conta?.registroNacionalCliente}</span>
                                </Typography>
                                <Typography>
                                    Instituição: <span className="font-semibold">{conta?.nomeInstituicao}</span>
                                </Typography>
                            </CardContent>
                        </Card>
                        <Typography variant="h1">
                            Escolha o valor
                        </Typography>
                        <TextField
                            fullWidth
                            placeholder="E-mail, CPF ou telefone"
                            label="Valor"
                        />
                        <Typography>
                            Saldo disponível: <span className="font-semibold">R$ 1.250,00</span>
                        </Typography>
                        <TextField
                            fullWidth
                            placeholder="Descrição"
                            label="Adicionar descrição (opcional)"
                            rows={4}
                            multiline
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