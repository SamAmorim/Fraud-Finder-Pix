import type { UIMatch } from "react-router";

export interface RouteLoaderData {
    pageTitle: string
}

export interface RouteHandle {
    title: string | ((data: RouteLoaderData) => string)
}

export interface RouteMatch extends UIMatch {
    handle: RouteHandle
}