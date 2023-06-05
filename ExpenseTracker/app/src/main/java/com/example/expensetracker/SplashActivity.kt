package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.lang.String
import java.util.*
import kotlin.Long


class SplashActivity : AppCompatActivity() {

    private var runnable = Runnable { gotoMainActivity() }

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed(runnable, 10000)

//        object : CountDownTimer((20 * 60000).toLong(), 10000) {
//            override fun onTick(millisUntilFinished: Long) {
//                textViewTimer.setText(
//                    "seconds remaining: " + SimpleDateFormat("mm:ss:SS").format(
//                        Date(
//                            millisUntilFinished
//                        )
//                    )
//                )
//            }
//
//            override fun onFinish() {
//                textViewTimer.text = "done!"
//            }
//        }.start()

        var counter = 10

        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textViewTimer.text = "$counter seconds"
                counter--
            }

            override fun onFinish() {
            }
        }.start()
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }

     fun skip(view: View) {
        gotoMainActivity()
    }

    private fun gotoMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}