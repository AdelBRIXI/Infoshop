package com.example.infoshop

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager


@Suppress("DEPRECATION")
class SplashArt : AppCompatActivity() {

    lateinit var handler:Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_art)

        @Suppress("DEPRECATION")

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN


            )
        }
        val time:Long=2000
        handler=Handler()
        handler.postDelayed({
              val intent=Intent(this,Welcome::class.java)
            startActivity(intent)
            finish()
        },time)


    }
}


