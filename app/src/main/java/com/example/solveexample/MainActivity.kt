package com.example.solveexample

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.solveexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var problem = Problem()
    private var problemBook = ProblemBook()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun onClickStart(control : View){
        runNewProblem()
        binding.start.isEnabled = false
        binding.checkAnswer.isEnabled = true
    }

    fun onClickCheck(control : View){
        binding.answer.isEnabled = false
        binding.checkAnswer.isEnabled = false
        var answer = 0
        try {
            answer = binding.answer.text.toString().toInt()
        }
        catch (ex: Exception)
        {
            binding.answer.isEnabled = true
            binding.checkAnswer.isEnabled = true
            return
        }

        if (answer == problemBook.getAnswer(problem)){
            problemBook.correctlySolvedProblems++
            greenLight()
        } else{
            problemBook.incorrectlySolvedProblems++
            redLight()
        }

        showState()
        runNewProblem()
        binding.answer.isEnabled = true
        binding.checkAnswer.isEnabled = true
    }

    private fun runNewProblem(){
        problem = problemBook.generateProblem()
        binding.firstNumber.text = problem.firstOperand.toString()
        binding.operation.text = problem.operator.toString()
        binding.secondNumber.text = problem.secondOperand.toString()
        binding.answer.setText("")
    }

    private fun showState(){
        binding.totalCount.text = "${problemBook.correctlySolvedProblems + problemBook.incorrectlySolvedProblems}"
        binding.correctCount.text = problemBook.correctlySolvedProblems.toString()
        binding.incorrectCount.text = problemBook.incorrectlySolvedProblems.toString()
        binding.percentCorrect.text = "${"%.2f".format(problemBook.getPercentageOfCorrectAnswers())}%"
    }

    private fun greenLight(){

    }

    private fun redLight(){

    }
}