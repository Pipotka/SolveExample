package com.example.solveexample

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.solveexample.databinding.ActivityMainBinding
import kotlin.random.Random

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
        binding.correctButton.isEnabled = true
        binding.incorrectButton.isEnabled = true
    }

    fun onClickButton(control : View){
        var userChoice = when (control.id){
            binding.correctButton.id -> true
            binding.incorrectButton.id -> false
            else -> false
        }


        binding.correctButton.isEnabled = false
        binding.incorrectButton.isEnabled = false

        var answer = binding.answer.text.toString().toInt()

        if (answer == problemBook.getAnswer(problem)){
            problemBook.correctlySolvedProblems++
            animateBackground(ContextCompat.getColor(this, R.color.green),
                ContextCompat.getColor(this, R.color.white),
                600)
        } else{
            problemBook.incorrectlySolvedProblems++
            animateBackground(ContextCompat.getColor(this, R.color.red),
                ContextCompat.getColor(this, R.color.white),
                600)
        }

        showState()
        runNewProblem()
        binding.correctButton.isEnabled = true
        binding.incorrectButton.isEnabled = true
    }

    private fun animateBackground(startColor: Int,
                                  endColor: Int,
                                  animDuration : Long) {
        binding.root.setBackgroundColor(startColor)
        ObjectAnimator.ofArgb(
            binding.root,
            "backgroundColor",
            startColor,
            endColor
        ).apply {
            duration = animDuration
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun generateAnswer(): Int{
        if (Random.nextInt(0, 2) == 1){
            return problemBook.getAnswer(problem)
        }
        return Random.nextInt(10, 100)
    }

    private fun runNewProblem(){
        problem = problemBook.generateProblem()
        binding.firstNumber.text = problem.firstOperand.toString()
        binding.operation.text = problem.operator.toString()
        binding.secondNumber.text = problem.secondOperand.toString()
        binding.answer.text = generateAnswer().toString()
    }

    private fun showState(){
        binding.totalCount.text = "${problemBook.correctlySolvedProblems + problemBook.incorrectlySolvedProblems}"
        binding.correctCount.text = problemBook.correctlySolvedProblems.toString()
        binding.incorrectCount.text = problemBook.incorrectlySolvedProblems.toString()
        binding.percentCorrect.text = "${"%.2f".format(problemBook.getPercentageOfCorrectAnswers())}%"
    }
}