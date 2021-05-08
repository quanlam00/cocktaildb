package com.quanlam.thecocktaildb.dataservice.model

//Convert a string of measure to float value in ounce (oz)
fun String.toOz(): Float {
    try {
        when {
            this.contains("oz", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("oz", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat()
            }
            this.contains("part", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("part", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat()
            }
            this.contains("shot", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("shot", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 1.5f
            }
            this.contains("ml", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("ml", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 0.03f
            }
            this.contains("cl", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("cl", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 0.33f
            }
            this.contains("tsp", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("tsp", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 0.16f
            }
            this.contains("tbsp", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("tbsp", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 0.16f
            }
            this.contains("tblsp", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("tblsp", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 0.16f
            }
            this.contains("cup", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("cup", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 8.11f
            }
            this.contains("pint", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("pint", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 16f
            }
            this.contains("pt", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("pt", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 16f
            }
            this.contains("quart", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("quart", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 32f
            }
            this.contains("qt", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("qt", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 32f
            }
            this.contains("gal", ignoreCase = true) -> {
                val value = this.substring(0, this.indexOf("gal", ignoreCase = true) - 1).trim()
                return value.fromFractionToFloat() * 128f
            }
            else -> {
                return try {
                    this.fromFractionToFloat()
                } catch (e: Exception) {
                    0f
                }
            }
        }
    } catch (e: Exception) {
    }
    return 0f
}

//Convert a fraction to float
fun String.fromFractionToFloat(): Float {
    try {
        val trimmed = this.trim()
        if (trimmed.contains('/')) {
            var fractionPart = this
            var floatValue = 0f
            if (trimmed.contains(' ')) {
                val integerPart = trimmed.substring(0 until trimmed.indexOfFirst { it == ' ' })
                if (integerPart.length < trimmed.length) {
                    floatValue += integerPart.toFloat()
                }
                fractionPart = trimmed.substring(integerPart.length until trimmed.length).trim()
            }
            val nom =
                fractionPart.substring(0 until fractionPart.indexOfFirst { it == '/' })
            val denom =
                fractionPart.substring(fractionPart.indexOfFirst { it == '/' }+1 until fractionPart.length)
            floatValue += nom.toFloat() / denom.toFloat()
            return floatValue
        } else {
            return trimmed.toFloat()
        }
    } catch (e: Exception) { }
    return 0f
}