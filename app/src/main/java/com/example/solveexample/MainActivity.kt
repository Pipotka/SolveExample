package com.example.solveexample

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
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
    private var countDownTimer: CountDownTimer? = null
    private var miliSeconds = 0
    private var minTime = 10000000.0f
    private var maxTime = 0.00f
    private var avrTime = 0.00f
    private var timeRecords = ArrayList<Float>()


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
        startTimer()
    }

    fun onClickButton(control : View){
        var userChoice = when (control.id){
            binding.correctButton.id -> true
            binding.incorrectButton.id -> false
            else -> false
        }
        stopTimer()
        timeRecords.add(getMiliseconds())
        binding.correctButton.isEnabled = false
        binding.incorrectButton.isEnabled = false

        var answer = binding.answer.text.toString().toInt()

        if (userChoice){
            if (answer == problemBook.getAnswer(problem)){
                rightInput()
            } else{
                badInput()
            }
        } else{
            if (answer != problemBook.getAnswer(problem)){
                rightInput()
            } else{
                badInput()
            }
        }

        showState()
        showTimerState()
        runNewProblem()
        binding.correctButton.isEnabled = true
        binding.incorrectButton.isEnabled = true
        resetTimer()
        startTimer()
    }

    private fun rightInput(){
        problemBook.correctlySolvedProblems++
        animateBackground(ContextCompat.getColor(this, R.color.green),
            ContextCompat.getColor(this, R.color.white),
            600)
        updateTimerState()
    }

    private fun badInput(){
        problemBook.incorrectlySolvedProblems++
        animateBackground(ContextCompat.getColor(this, R.color.red),
            ContextCompat.getColor(this, R.color.white),
            600)
        updateTimerState()
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
        binding.percentCorrect.text = formatFloat(problemBook.getPercentageOfCorrectAnswers()) + '%'
    }

    private fun showTimerState(){
        binding.maxTime.text = maxTime.toString()
        binding.minTime.text = minTime.toString()
        binding.avgTime.text = formatFloat(avrTime)
    }

    private fun updateTimerState(){
        if (minTime > getMiliseconds()) minTime = getMiliseconds()
        if (maxTime < getMiliseconds()) maxTime = getMiliseconds()
        avrTime = if (timeRecords.count() == 0) 0.0f else timeRecords.sum().toFloat() / timeRecords.count().toFloat()
    }

    private fun formatFloat(number : Float) : String{
        return "%.2f".format(number)
    }

    private fun startTimer() {
        if (countDownTimer != null) return

        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1) {
            override fun onTick(millisUntilFinished: Long) {
                miliSeconds++
            }

            override fun onFinish() {}
        }

        countDownTimer?.start()
    }

    private fun getMiliseconds() : Float{
        return miliSeconds.toFloat() / 1000f
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }

    private fun resetTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
        miliSeconds = 0
    }
}