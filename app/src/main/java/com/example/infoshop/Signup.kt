package com.example.infoshop

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.back_arrow1
import kotlinx.android.synthetic.main.activity_signup.full_name
import kotlinx.android.synthetic.main.activity_signup.loginbtn
import kotlinx.android.synthetic.main.activity_signup.password
import java.net.URL

class
Signup : BaseActivity() {
    var myauth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signup)



    //contact
        email_id.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"+"a.chachoua@esi-sba.dz"+"?subject="+"infoshop")))
        }
        insta_id.setOnClickListener {
            val uriForApp: Uri = Uri.parse("http://instagram.com/_u/ama_ni_ch21")
            val forApp = Intent(Intent.ACTION_VIEW, uriForApp)


            val uriForBrowser: Uri = Uri.parse("http://instagram.com/ama_ni_ch21")
            val forBrowser = Intent(Intent.ACTION_VIEW, uriForBrowser)

            try {
                startActivity(forApp)
            }catch (e:ActivityNotFoundException){
                startActivity(forBrowser)

            }
        }

        fb_id.setOnClickListener {
            val uriForApp:Uri=Uri.parse("http://www.facebook.com/mini.amouna.3")
            val forApp=Intent(Intent.ACTION_VIEW, uriForApp)

            val uriForBrowser: Uri = Uri.parse("http://www.facebook.com/mini.amouna.3")
            val forBrowser = Intent(Intent.ACTION_VIEW, uriForBrowser)

            try {
                startActivity(forApp)
            }catch (e:ActivityNotFoundException){
                startActivity(forBrowser)

            }
        }

       //back arrow
        val int3 = Intent(this,Welcome::class.java)

        back_arrow1.setOnClickListener{
            startActivity(int3)
            finish()
        }
        //button register
        loginbtn.setOnClickListener {
            //validateregisterdetails()
            val ee = email_adress.text.toString().trim()
            val pas = this.password.text.toString().trim()
            val user = full_name.text.toString().trim()



            if (ee.isEmpty() || pas.isEmpty() || user.isEmpty()) showErrorSnackBar("Error : Your Email Or Username Or Password Is Empty ,Please Enter It",true)
            else {



                val ine = Intent(this,infor::class.java)

                ine.putExtra("username",user)
                ine.putExtra("email",ee)
                ine.putExtra("password",pas)

                startActivity(ine)
                finish()
            }


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


    // a function to validate the entries of a new user
    private fun validateregisterdetails():Boolean{
        return when {
            TextUtils.isEmpty(email_adress.text.toString().trim{it <=' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_enter_mail),true)
                false
            }
            TextUtils.isEmpty(full_name.text.toString().trim{it <=' ' }) ||full_name.length()<=4  -> {
                showErrorSnackBar(resources.getString(R.string.err_enter_full_name),true)
                false
            }
            TextUtils.isEmpty(password.text.toString().trim{it <=' ' })||password.length()<=6 -> {
                showErrorSnackBar(resources.getString(R.string.err_enter_password),true)
                false
            }
            TextUtils.isEmpty(confirm_password.text.toString().trim{it <=' ' }) || confirm_password.length()<=6-> {
                showErrorSnackBar(resources.getString(R.string.err_enter_confirm_password),true)
                false
            }
            password.text.toString().trim{it <=' ' } != confirm_password.text.toString().trim{it <=' ' } ->{
                showErrorSnackBar(resources.getString(R.string.err_password_and_confirm_password_mismatch),true)
                false
            }
            else -> {
                showErrorSnackBar("your details are valid",false)
                true
            }
        }
    }
}