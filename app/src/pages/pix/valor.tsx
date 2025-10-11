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

export default function PixValor() {

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
                                    Pix para: <span className="font-semibold">Fulano de Tal</span>
                                </Typography>
                                <Typography>
                                    CPF/CNPJ: <span className="font-semibold">123.***.***-90</span>
                                </Typography>
                                <Typography>
                                    Instituição: <span className="font-semibold">NU PAGAMENTOS</span>
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