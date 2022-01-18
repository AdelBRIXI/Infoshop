package com.example.infoshop

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login2.*

class login : BaseActivity() {
    val myauth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)







        //forget password
        val int=Intent(this,forget_password::class.java)
        forget.setOnClickListener {
            startActivity(int)
            finish()
        }
        val int3 = Intent(this,Welcome::class.java)
        back_arrow1.setOnClickListener{
            startActivity(int3)
            finish()
        }
        val int4 = Intent(this,Signup::class.java)
        reg.setOnClickListener{
            startActivity(int4)
            finish()
        }

        val int7 = Intent(this, Home::class.java)

        loginbtn.setOnClickListener {

            val ee = full_name.text.toString().trim()
            val pas = password1.text.toString().trim()
            if (ee.isEmpty() || pas.isEmpty()) showErrorSnackBar("Error : Your Email Or Password Is Empty ,Please Enter It",true)
            else (Login(ee,pas))



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
    private fun Login(emaill:String,Passwd:String){
        Toast.makeText(this,"Authentification ...",Toast.LENGTH_LONG).show()
        myauth.signInWithEmailAndPassword(emaill,Passwd).addOnCompleteListener(this,
            OnCompleteListener {
                if (it.isSuccessful){
                    verificationEmail()
                }else showErrorSnackBar("This Account Doesn't Exist",true)
            })

    }
    private fun verificationEmail(){

        val user = myauth?.currentUser
        if (user!!.isEmailVerified){
            showErrorSnackBar("Connection Succeeded ...",false)
            Thread.sleep(800)
            val intt= Intent(this,Home::class.java)
            startActivity(intt)

        }else{
            showErrorSnackBar("Please Verify Your Email Before Use This Accout ...",true)
        }
    }
}