import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
    plugins: [
        react(),
        tailwindcss(),
    ],
    resolve: {
        alias: {
            assets: "/src/assets",
            components: "/src/components",
            pages: "/src/pages",
            typesrc: "/src/types",
            utils: "/src/utils"
        }
    }
})
