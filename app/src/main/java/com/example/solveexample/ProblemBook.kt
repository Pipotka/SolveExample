package com.example.solveexample

import kotlin.random.Random

public class ProblemBook {
    public var correctlySolvedProblems = 0;
    public var incorrectlySolvedProblems = 0;

    public fun getPercentageOfCorrectAnswers() : Float{
        if (incorrectlySolvedProblems == 0
            && correctlySolvedProblems == 0) return 0.00f

        return correctlySolvedProblems /
                ((correctlySolvedProblems + incorrectlySolvedProblems) / 100.0f)
    }

    public fun generateProblem() : Problem{
        var result = Problem()
        result.firstOperand = generateFirstOperand()
        result.operator = generateOperator()
        result.secondOperand = generateSecondOperand(result)
        return result
    }

    public fun generateFirstOperand() : Int{
        return Random.nextInt(10, 100)
    }

    public fun generateOperator() : Operators {
        val number = Random.nextInt(1, 5)
        return when(number){
            1 -> Operators.PLUS
            2 -> Operators.MINUS
            3 -> Operators.MULTIPLY
            4 -> Operators.DIVIDE
            else -> Operators.NONE
        }
    }

    public fun generateSecondOperand(problem: Problem) : Int{
        var result = 0
        when(problem.operator){
            Operators.PLUS, Operators.MINUS, Operators.MULTIPLY ->{
                result = Random.nextInt(10, 100)
            }
            Operators.DIVIDE ->{
                val list = findDivisors(problem.firstOperand)
                result = list[Random.nextInt(0, list.count())]
            }
            else -> {}
        }
        return result
    }

    public fun getAnswer(problem: Problem) : Int{
        return problem.operator.performCalculation(problem.firstOperand, problem.secondOperand)
    }

    fun findDivisors(number: Int): List<Int> {
        if (number == 0) return emptyList()
        val divisors = mutableListOf<Int>()
        val absNumber = kotlin.math.abs(number)

        for (i in 10..99) {
            if (absNumber % i == 0) {
                divisors.add(i)

                if (i != absNumber / i) divisors.add(absNumber / i)
            }
        }

        return divisors.filter { it >= 10 && it <= 99 }.distinct()
    }
}