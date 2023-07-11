package com.example.kidsmath2

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var TimeTextView: TextView? = null
    var QuestionTextView: TextView? = null
    var ScoreTextView: TextView? = null
    var AlertTextView: TextView? = null
    var FinalScoreTextView: TextView? = null
    var btn0: Button? = null
    var btn1: Button? = null
    var btn2: Button? = null
    var btn3: Button? = null

    var CountDownTimer: CountDownTimer? = null
    var random: Random = Random
    var a = 0
    var b = 0
    var indexOfCorrectAnswer = 0
    var answers = ArrayList<Int>()
    var points = 0
    var totalQuestions = 0
    var cals = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calInt = intent.getStringExtra("cals")
        if (calInt != null) {
            cals = calInt
        }
        TimeTextView = findViewById(R.id.TimeTextView)
        QuestionTextView = findViewById(R.id.QuestionTextText)
        ScoreTextView = findViewById(R.id.ScoreTextView)
        AlertTextView = findViewById(R.id.AlertTextView)

        btn0 = findViewById(R.id.button0)
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)

        start()

    }

    fun NextQuestion(cal: String) {
        a = random.nextInt(10)
        b = random.nextInt(10)
        QuestionTextView!!.text = "$a $cal $b"
        indexOfCorrectAnswer = random.nextInt(4)

        answers.clear()

        for (i in 0..3) {
            if (indexOfCorrectAnswer == i)
            else {
                var wrongAnswer = random.nextInt(20)
                try {
                    while (
                        wrongAnswer == a+b
                        || wrongAnswer == a-b
                        || wrongAnswer == a*b
                        || wrongAnswer == a/b
                    ) {
                        wrongAnswer = random.nextInt(20)
                    }
                    answers.add(wrongAnswer)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            when (cal) {
                "+" -> {
                    answers.add(a+b)
                }
                "-" -> {
                    answers.add(a - b)
                }
                "*" -> {
                    answers.add(a * b)
                }
                "÷" -> {
                    try {
                        answers.add(a / b)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        try {
            btn0!!.text = "${answers[0]}"
            btn1!!.text = "${answers[1]}"
            btn2!!.text = "${answers[2]}"
            btn3!!.text = "${answers[3]}"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun optionSelect(view: View?) {
        totalQuestions++
        if (indexOfCorrectAnswer.toString() == view!!.tag.toString()) {

            points++
            AlertTextView!!.text = "Correct"
            Log.e("TAG", "Playing sound...");
            val mediaPlayer = MediaPlayer.create(this, R.raw.success)
            mediaPlayer.start()
        } else {
            AlertTextView!!.text = "Wrong"
            val mediaPlayer = MediaPlayer.create(this, R.raw.failed)
            mediaPlayer.start()
        }
        ScoreTextView!!.text = "$points/$totalQuestions"
        NextQuestion(cals)
    }

    fun PlayAgain(view: View?) {
        points = 0
        totalQuestions = 0
        ScoreTextView!!.text = "$points/$totalQuestions"
        CountDownTimer!!.start()
    }

    private fun start() {
        NextQuestion(cals)
        CountDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                TimeTextView!!.text = (p0 / 1000).toString() + "s"
            }

            override fun onFinish() {
                TimeTextView!!.text = "Time Up !"
                openDilog()
            }

        }.start()
    }

    private fun openDilog() {
        val inflate = LayoutInflater.from(this)
        val winDialog = inflate.inflate(R.layout.win_layout,null)
        FinalScoreTextView = winDialog.findViewById(R.id.FinalScoreTextView)
        val btnPlayAgain = winDialog.findViewById<Button>(R.id.buttonPlayAgain)
        val btnBack = winDialog.findViewById<Button>(R.id.btnBack)
        val dialog = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setView(winDialog)
        FinalScoreTextView!!.text = "$points/$totalQuestions"
       val showDialog = dialog.create()
        showDialog.show()
        btnPlayAgain.setOnClickListener {
            showDialog.dismiss()
            PlayAgain(it)
        }
        btnBack.setOnClickListener { finish() }

    }

    override fun onDestroy() {
        super.onDestroy()
        CountDownTimer?.cancel()
    }
}