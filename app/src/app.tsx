import CssBaseline from '@mui/material/CssBaseline'
import GlobalStyles from '@mui/material/GlobalStyles'
import { StyledEngineProvider, ThemeProvider } from '@mui/material/styles'
import moment from 'moment'
import routes from 'pages/routes'
import { RouterProvider } from 'react-router'
import theme from './theme'

function App() {

    moment.locale('pt-br')

    return (
        <StyledEngineProvider enableCssLayer>
            <ThemeProvider theme={theme}>
                <GlobalStyles styles="@layer theme, base, mui, components;" />
                <CssBaseline />
                <RouterProvider router={routes} />
            </ThemeProvider>
        </StyledEngineProvider>
    )
}

export default App
