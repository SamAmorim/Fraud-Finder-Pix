import type { AxiosInstance } from "axios"
import axios from "axios"

export default class API {

    private static _pixApi: AxiosInstance

    public static get pixApi(): AxiosInstance {
        if (!this._pixApi) {
            this._pixApi = this.createInstance(import.meta.env.VITE_API_PIXAPI_URL)
        }
        return this._pixApi
    }

    private static createInstance(baseUrl: string): AxiosInstance {
        const instance = axios.create({
            baseURL: baseUrl,
        })

        return instance
    }
}