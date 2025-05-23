package com.example.solveexample

enum class Operators {
    PLUS,
    MINUS,
    DIVIDE,
    MULTIPLY,
    NONE;

    fun performCalculation(firstOperand : Int, secondOperand : Int) : Int{
        return when(this){
            PLUS -> firstOperand + secondOperand
            MINUS -> firstOperand - secondOperand
            DIVIDE -> firstOperand / secondOperand
            MULTIPLY -> firstOperand * secondOperand
            else -> 0
        }
    }

    override fun toString(): String {
        return when(this){
            PLUS -> "+"
            MINUS -> "-"
            DIVIDE -> "/"
            MULTIPLY -> "*"
            else -> ""
        }
    }
}