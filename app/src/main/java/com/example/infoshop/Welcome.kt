package com.example.infoshop

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_welcome2.*

class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome2)
        val int1= Intent(this,login::class.java)
        val int2= Intent(this,Signup::class.java)
        loginbtn.setOnClickListener {
            startActivity(int1)
            finish()
        }
        reg.setOnClickListener {
            startActivity(int2)
            finish()
        }
        @Suppress("DEPRECATION")

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN


            )
        }
    }
}