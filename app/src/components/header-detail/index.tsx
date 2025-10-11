import Box from "@mui/material/Box"
import { useTheme } from "@mui/material/styles"
import type { HeaderDetailProps } from "typesrc/components/header-detail"

export default function HeaderDetail({
    height = 220
}: HeaderDetailProps) {

    const theme = useTheme()

    return (
        <Box className="absolute top-0 left-0 w-screen -z-10" style={{ height }}>
            <svg
                width="100%"
                height={height}
                viewBox="0 0 1440 120"
                preserveAspectRatio="none"
                xmlns="http://www.w3.org/2000/svg"
            >
                <defs>
                    <linearGradient id="rectWaveGradient" x1="50%" y1="0%" x2="50%" y2="100%">
                        <stop offset="0%" stopColor={theme.palette.primary.main} />
                        <stop offset="100%" stopColor={theme.palette.secondary.main} />
                    </linearGradient>
                </defs>
                <path
                    d="M0,0 H1440 V100 Q1080,120 720,100 Q360,80 0,100 Z"
                    fill="url(#rectWaveGradient)"
                />
            </svg>
        </Box>
    )
}