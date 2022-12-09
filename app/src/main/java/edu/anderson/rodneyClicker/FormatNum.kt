package edu.anderson.rodneyClicker

object FormatNum {
    fun formatNumber(num: Long): String {
        return when {
            num >= 1000000000000000000 -> String.format("R$%.2fQ", num / 1000000000000000000.0)
            num >= 1000000000000 -> String.format("R$%.2fT", num / 1000000000000.0)
            num >= 1000000000 -> String.format("R$%.2fB", num / 1000000000.0)
            num >= 1000000 -> String.format("R$%.2fM", num / 1000000.0)
            num >= 1000 -> String.format("R$%.2fK", num / 1000.0)
            else -> String.format("R$$num")
        }
    }
    fun formatNumberTRD(num: Long): String {
        return when {
            num >= 1000000000000000000 -> String.format("Total Raven Dollars: %.2fQ", num / 1000000000000000000.0)
            num >= 1000000000000 -> String.format("Total Raven Dollars: %.2fT", num / 1000000000000.0)
            num >= 1000000000 -> String.format("Total Raven Dollars: %.2fB", num / 1000000000.0)
            num >= 1000000 -> String.format("Total Raven Dollars: %.2fM", num / 1000000.0)
            num >= 1000 -> String.format("Total Raven Dollars: %.2fK", num / 1000.0)
            else -> String.format("Total Raven Dollars: $num")
        }
    }

    fun formatNumberRDPS(num: Long): String {
        return when {
            num >= 1000000000000000000 -> String.format("+%.2fQ/s", num / 1000000000000000000.0)
            num >= 1000000000000 -> String.format("+%.2fT/s", num / 1000000000000.0)
            num >= 1000000000 -> String.format("+%.2fB/s", num / 1000000000.0)
            num >= 1000000 -> String.format("+%.2fM/s", num / 1000000.0)
            num >= 1000 -> String.format("+%.2fK/s", num / 1000.0)
            else -> String.format("+$num/s")
        }
    }
}
