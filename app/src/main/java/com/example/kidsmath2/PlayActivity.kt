package com.example.kidsmath2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_play.*

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        add.setOnClickListener {
            val calInt = Intent(
                this@PlayActivity,
                MainActivity::class.java
            )

            calInt.putExtra("cals", "+")
            startActivity(calInt)
        }

        sub.setOnClickListener {
            val calInt = Intent(
                this@PlayActivity,
                MainActivity::class.java
            )

            calInt.putExtra("cals", "-")
            startActivity(calInt)

        }

        times.setOnClickListener {
            val calInt = Intent(
                this@PlayActivity,
                MainActivity::class.java
            )

            calInt.putExtra("cals", "*")
            startActivity(calInt)

        }

        takeaway.setOnClickListener {
            val calInt = Intent(
                this@PlayActivity,
                MainActivity::class.java
            )

            calInt.putExtra("cals", "÷")
            startActivity(calInt)

        }
    }

}