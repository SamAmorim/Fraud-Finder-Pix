import React, { useEffect, useState } from "react"
import TextField from "@mui/material/TextField"
import type { TextFieldProps } from "@mui/material/TextField"
import InputAdornment from "@mui/material/InputAdornment"
import Icon from "components/icon"

export interface CurrencyFieldProps extends Omit<TextFieldProps, "value" | "onChange"> {
    /** numeric value in units (e.g. 1234.56) or null/undefined for empty */
    value?: number | null
    /** callback receives numeric value in units (e.g. 1234.56) or null when empty */
    onChange?: (value: number | null) => void
}

const formatter = new Intl.NumberFormat("pt-BR", {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
})

function parseToNumber(input: string): number | null {
    if (!input) return null
    // Keep digits, dot and comma
    let s = String(input).trim()
    // If contains comma, assume comma is decimal separator and dots are thousands
    if (s.includes(",")) {
        s = s.replace(/\./g, "") // remove thousands dots
        s = s.replace(/,/g, ".") // convert decimal comma to dot
    } else {
        // no comma - remove commas that may be thousands separators
        s = s.replace(/,/g, "")
    }
    // Remove any characters other than digits and dot
    s = s.replace(/[^\d.]/g, "")
    if (!s) return null
    const n = parseFloat(s)
    if (Number.isNaN(n)) return null
    return n
}

export default function CurrencyField(props: CurrencyFieldProps) {
    const { value, onChange, InputProps, ...rest } = props
    const [display, setDisplay] = useState("")

    // keep display in sync when external numeric value changes
    useEffect(() => {
        if (value == null || Number.isNaN(value)) {
            setDisplay("")
        } else {
            setDisplay(formatter.format(value))
        }
    }, [value])

    const handleChange: React.ChangeEventHandler<HTMLTextAreaElement | HTMLInputElement> = (e) => {
        const raw = e.target.value

        // Prevent typing '-' entirely
        if (raw.includes("-")) {
            return
        }

        // try parse
        const parsed = parseToNumber(raw)
        if (parsed == null) {
            // keep what user typed (helps when clearing) but notify as null
            setDisplay(raw)
            onChange?.(null)
            return
        }

        // prevent negative values
        if (parsed < 0) {
            return
        }

        // commit formatted value
        setDisplay(formatter.format(parsed))
        onChange?.(parsed)
    }

    return (
        <TextField
            {...rest}
            value={display}
            onChange={handleChange}
            inputMode="decimal"
            InputProps={{
                ...InputProps,
                startAdornment: (
                    <InputAdornment position="start">
                        <Icon className="text-[length:inherit]!">attach_money</Icon>
                    </InputAdornment>
                ),
            }}
        />
    )
}
