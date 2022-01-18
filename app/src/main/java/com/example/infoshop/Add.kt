package com.example.infoshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.back_arrow1
import kotlinx.android.synthetic.main.activity_add.loginbtn
import kotlinx.android.synthetic.main.activity_infor.*
import java.util.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList


class Add : AppCompatActivity() {
    var mstorage : StorageReference? = null
    val auth = FirebaseAuth.getInstance()
    val database = Firebase.database
    var id_usr:String? = null
    var id_pub:String? = null
    val listid : ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        val cat = arrayOf("Ordinateurs Portable" ,"Imprimantes" ,"Pièce Pour PC fixe" ,"Accessoires informatique","Réseau et Connexion",
            "PC Bureau" ,"Apps et Logiciels","Pièce pour PC portable", "Ecrans et Data Show" , "BOUIRA"
            ,"Stockage externe","Ondulateurs et Stabilisateurs")





        val myRef = database.getReference("publications")



        id_pub = myRef.push().key.toString()
        var mail = auth.currentUser?.email




        loginbtn.setOnClickListener(){
            val titre = NAME.text.toString()
            val prix = PRICE.text.toString()
            val categorie = confirm_password.text.toString()
            val desc = description.text.toString()
            var nm =(this.id_pub+"@"+this.id_usr+"@"+confirm_password.text.toString()+"@1").toString()
            if (!categorie.isEmpty() || !titre.isEmpty() || !prix.isEmpty() || !desc.isEmpty() ) {
                insert_pub(id_pub.toString(), true, titre, prix.toInt(), id_usr.toString(), categorie, desc, Calendar.getInstance().time.toString(),nm)
            }else BaseActivity().showErrorSnackBar("Please Complete The Informations First ",true)
        }




        val ref = FirebaseDatabase.getInstance().getReference("users")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (e in snapshot.children){
                        var usr = e.getValue(Users::class.java)
                        if (usr?.email==mail) {
                            id_usr = usr?.id_user
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


        back_arrow1.setOnClickListener(){
            val back = Intent(this,Home::class.java)
            startActivity(back)
            finish()
        }


        mstorage = FirebaseStorage.getInstance().reference

        try {


            loginbttn.setOnClickListener() {
                val titre = NAME.text.toString()
                val prix = PRICE.text.toString()
                val categorie = confirm_password.text.toString()
                val desc = description.text.toString()
                if (!categorie.isEmpty() || !titre.isEmpty() || !prix.isEmpty() || !desc.isEmpty()) {
                    val inte = Intent(Intent.ACTION_PICK)
                    inte.type = "image/*"
                    startActivityForResult(inte, 2)
                } else Toast.makeText(this,"Please Complete The Informations First ",Toast.LENGTH_LONG).show()

            }
        }catch (e:Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var nm:String = this.id_pub+"@"+this.id_usr+"@"+confirm_password.text.toString()+"@${(listid.size)+1}"
        listid.add("adel")
        if (requestCode==2 && resultCode== RESULT_OK){
            val uri =data?.data
            val filepath = mstorage?.child(nm)
            if (uri != null) {
                filepath?.putFile(uri)?.addOnSuccessListener {
                    Toast.makeText(this,"Upload Image",Toast.LENGTH_LONG).show()
                }
            }

        }
    }


    fun insert_pub( id_pub:String , active: Boolean , titre: String , prix: Int , id_usr: String , id_cat: String , desc: String , datee: String , img_p:String){

        // Write a message to the database
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("publications")
        var strdact = active.toString()
        val pub = Pub(
                id_pub,
                strdact,
                titre,
                prix,
                id_usr,
                id_cat,
                desc,
                datee,
                img_p
        )
        myRef.child(id_pub.toString()).setValue(pub).addOnCompleteListener(this, OnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "publication added ...", Toast.LENGTH_LONG).show()
                val back = Intent(this,Home::class.java)
                startActivity(back)
                finish()
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        })

    }



}