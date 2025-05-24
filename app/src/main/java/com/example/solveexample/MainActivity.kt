package com.example.solveexample

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
            animateBackground(ContextCompat.getColor(this, R.color.red),
                ContextCompat.getColor(this, R.color.white),
                600)
            return
        }

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
        binding.answer.isEnabled = true
        binding.checkAnswer.isEnabled = true
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

    private fun clearBackground(){
        binding.root.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
    }
}