import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

export default function Inicio() {

    return (
        <Box
            sx={{
                // linear gradient using main and secondary colors from theme
                background: ({ palette }) => `linear-gradient(135deg, ${palette.primary.main} 0%, ${palette.secondary.main} 100%)`,
            }}
        >
            <Typography variant="h2" color="white" textAlign="center">
                Pix
            </Typography>
        </Box>
    )
}