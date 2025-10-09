import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import Icon from "components/icon";

export default function Header() {

    return (
        <Box
            component="header"
            bgcolor="primary.main"
            className="flex w-full justify-between items-center p-4 gap-4"
        >
            <Box className="w-16">
                <IconButton>
                    <Icon className="text-white">
                        arrow_back
                    </Icon>
                </IconButton>
            </Box>
            <Typography
                component="h1"
                color="primary.contrastText"
                variant="h6"
                className="w-full text-center"
                fontWeight="bold"
            >
                Pix
            </Typography>
            <Box className="w-16">
            </Box>
        </Box>
    )
}