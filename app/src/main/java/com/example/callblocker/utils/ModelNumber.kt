package com.example.callblocker.utils

class ModelNumber {
    companion object {
        fun modelNumber(number: String): String {
            var num = number
            num = num.replace(" ", "")
            if (num[0] == '0')
                num = number.substring(0)
            if (!num.contains("+"))
                num = "+91$num"
            return num
        }
    }
}