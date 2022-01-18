package com.example.infoshop

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_infor.*
import kotlinx.android.synthetic.main.activity_infor.email_adress1
import kotlinx.android.synthetic.main.activity_infor.adresse
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_infor.back_arrow1
import kotlinx.android.synthetic.main.activity_infor.loginbtn
import kotlinx.android.synthetic.main.activity_signup.*


class infor : BaseActivity() {
    var myauth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infor)

        val sugg = arrayOf("ADRAR" ,"CHLEF" ,"LAGHOUAT" ,"OUM BOUAGHI","BATNA","BEJAIA" ,"BISKRA","BECHAR", "BLIDA" , "BOUIRA"
            ,"TAMANRASSET","TEBESSA", "TLEMCEN","TIARET","TIZI OUZOU","ALGER","DJELFA", "JIJEL","SETIF","SAIDA"
            ,"SKIKDA","SIDI BEL ABBES","ANNABA","GUELMA","CONSTANTINE", "MEDEA" , "MOSTAGANEM","M'SILA","MASCARA",
            "OUARGLA","ORAN","EL BAYDH","ILLIZI","BORDJ BOU ARRERIDJ","BOUMERDES","EL TAREF","TINDOUF","TISSEMSILT","EL OUED"
            ,"KHENCHLA","SOUK AHRASS","TIPAZA","MILA AÏN DEFLA","NÂAMA","AÏN TEMOUCHENT","GHARDAÏA", "RELIZANE",)


        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,sugg)
        Wilaya.threshold=0
        Wilaya.setAdapter(adapter)
        Wilaya.setOnFocusChangeListener({ view, b ->  if(b) Wilaya.showDropDown()})


        //contact
        imageView5.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"+"a.chachoua@esi-sba.dz"+"?subject="+"infoshop")))
        }
        imageView3.setOnClickListener {
            val uriForApp: Uri = Uri.parse("http://instagram.com/_u/ama_ni_ch21")
            val forApp = Intent(Intent.ACTION_VIEW, uriForApp)


            val uriForBrowser: Uri = Uri.parse("http://instagram.com/ama_ni_ch21")
            val forBrowser = Intent(Intent.ACTION_VIEW, uriForBrowser)

            try {
                startActivity(forApp)
            }catch (e: ActivityNotFoundException){
                startActivity(forBrowser)

            }
        }

        imageView4.setOnClickListener {
            val uriForApp: Uri = Uri.parse("http://www.facebook.com/mini.amouna.3")
            val forApp=Intent(Intent.ACTION_VIEW, uriForApp)

            val uriForBrowser: Uri = Uri.parse("http://www.facebook.com/mini.amouna.3")
            val forBrowser = Intent(Intent.ACTION_VIEW, uriForBrowser)

            try {
                startActivity(forApp)
            }catch (e: ActivityNotFoundException){
                startActivity(forBrowser)

            }
        }

        val ine = intent
        val ee = ine.getStringExtra("email").toString()
        val pas = ine.getStringExtra("password").toString()
        val user = ine.getStringExtra("username").toString()



        loginbtn.setOnClickListener(){
            val pren = prenomm.text.toString()
            val nme = email_adress1.text.toString()
            val num = phone.text.toString().toInt()
            val wilayaa = Wilaya.text.toString()
            val adr = adresse.text.toString()
           if (pren.isEmpty() || nme.isEmpty() || num.toString().isEmpty() || wilayaa.isEmpty() || adr.isEmpty()) showErrorSnackBar("Error : Your Informations Is Empty ,Please Enter It",true)
            else {
                if (!(test_username(user))) {
                    insert_users(
                        user,
                        ee,
                        pas,
                        pren,
                        nme,
                        num,
                        wilayaa,
                        adr
                    )
                    Signup(ee, pas)
                }else showErrorSnackBar("This Username Exist ... Please Change It",true)
            }
        }

        back_arrow1.setOnClickListener(){
            val signupp = Intent(this,Signup::class.java)
            startActivity(signupp)
            finish()
        }

    }

    private fun Signup(email : String,passwd:String){
        myauth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener(this, OnCompleteListener {
            if (it.isSuccessful){
                showErrorSnackBar("Signup Succeded ... Please Confirme Your Email",false)
                Thread.sleep(800)
                val int107 = Intent(this, login::class.java)
                startActivity(int107)
                val user = myauth.currentUser
                user?.sendEmailVerification()?.addOnCompleteListener(this, OnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this,"Verification Email Is Sent", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                })

            }else{
                Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show()
                showErrorSnackBar(it.exception?.message.toString(),true)
                println("adel"+it.exception?.message.toString())
                println("adel"+it.exception?.message.toString())
                println("adel"+it.exception?.message.toString())
                Thread.sleep(4000)
                val ine= Intent(this,Signup::class.java)
                startActivity(ine)

            }

        })



    }


//, pren:String , nme:String , num:Int , wilaya:String , adr:String
    fun insert_users(username:String , email:String , password:String , pren:String , nme:String , num:Int , wilaya:String , adr:String ){

    // Write a message to the database
    // Write a message to the database
    val database = Firebase.database
    val myRef = database.getReference("users")
    val userid = myRef.push().key
    val user = Users(
                        userid,
                        nme,
                        pren,
                        username,
                        email,
                        password,
                        adr,
                        num,
                        wilaya
                    )
        myRef.child(userid.toString()).setValue(user).addOnCompleteListener(this, OnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "saved ...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun test_username(username:String):Boolean{
        var b:Boolean = false
        val ref = FirebaseDatabase.getInstance().getReference("users")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (e in snapshot.children){
                        var usr= e.getValue(Users::class.java)
                        if (usr?.username==username){
                            b=true
                            continue
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return b
    }
}