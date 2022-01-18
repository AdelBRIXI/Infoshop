package com.example.infoshop

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.content_main.*

class Profile : AppCompatActivity() , onPubClickListener {
    val auth = FirebaseAuth.getInstance()
    var mstorage : StorageReference? = null
    var myarray1 : ArrayList<Pub>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        mstorage = Firebase.storage.reference


        var mail = auth.currentUser?.email
        mstorage!!.child(mail!!).getBytes(1024*1024).addOnSuccessListener(
            OnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                imageView10.setImageBitmap(bitmap)
            })

        val int8 = Intent(this, Home::class.java)
        button6.setOnClickListener(){
            startActivity(int8)
            finish()
        }

        //recyclerview
        rec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        myarray1 = ArrayList()
        val referece = FirebaseDatabase.getInstance().getReference("publications")
        referece.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (e in snapshot.children){
                        var usr = e.getValue(Pub::class.java)
                        myarray1?.add(usr!!)
                    }
                    rec.adapter = myrecadapter(myarray1!!,this@Profile)
                }
            }


            override fun onCancelled(error: DatabaseError) {
            }

        })



        val ref = FirebaseDatabase.getInstance().getReference("users")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (e in snapshot.children){
                        var usr = e.getValue(Users::class.java)
                        if (usr?.email==mail) {
                            textemail.text = usr?.email
                            textuser.text = usr?.username
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }


        })
        BtnPickGallery.setOnClickListener{
            val inte = Intent(Intent.ACTION_PICK)
            inte.type = "image/*"
            startActivityForResult(inte, 3)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==3 && resultCode== RESULT_OK){
            val uri =data?.data
            val filepath = mstorage?.child(auth.currentUser?.email.toString())
            if (uri != null) {
                filepath?.putFile(uri)?.addOnSuccessListener {
                    Toast.makeText(this,"Upload Image", Toast.LENGTH_LONG).show()
                }
            }
            var mail = auth.currentUser?.email
            mstorage!!.child(mail!!).getBytes(1024*1024).addOnSuccessListener(
                OnSuccessListener {
                    val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                    imageView10.setImageBitmap(bitmap)
                })
        }
    }

    override fun onPubClicked_pub(position: Int) {
        Toast.makeText(this, myarray1!![position].titre, Toast.LENGTH_SHORT).show()
        val ine = Intent(this,showpub::class.java)
        ine.putExtra("titre",myarray1!![position].titre.toString())
        ine.putExtra("prix",myarray1!![position].prix.toString())
        ine.putExtra("desc",myarray1!![position].desc.toString())
        ine.putExtra("date",myarray1!![position].datee.toString())
        ine.putExtra("id",myarray1!![position].id_pub.toString())
        ine.putExtra("id_usr",myarray1!![position].id_usr.toString())
        startActivity(ine)
        finish()
    }

    override fun onPubClicked_cat(position: Int) {
    }
}