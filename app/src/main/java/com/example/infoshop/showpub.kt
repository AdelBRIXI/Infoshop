package com.example.infoshop

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_showpub.*
import kotlinx.android.synthetic.main.recyclerview_model.*

class showpub : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showpub)


        val ine = intent
        val id_pub = ine.getStringExtra("id").toString()
        val id_usrr = ine.getStringExtra("id_usr").toString()


        var list : ArrayList<String> = ArrayList()
        val refe = FirebaseStorage.getInstance().reference
        val listres :Task<ListResult> = refe.listAll()
        listres.addOnSuccessListener { result ->


            val items : List<StorageReference> = result.items
            items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {
                    if (id_pub in it.toString()) list.add(it.toString())
                }.addOnCompleteListener {
                    myrecpub.layoutManager = LinearLayoutManager(this)
                    myrecpub.adapter = recpub(list)
                }
            }
        }
        val ref = FirebaseDatabase.getInstance().getReference("users")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (e in snapshot.children){
                        val usr = e.getValue(Users::class.java)
                        if (usr?.id_user.toString()==id_usrr) {
                            println("ranii dkhalt")
                            useradr.text = usr?.adresse.toString()
                            useremail.text = usr?.email.toString()
                            usernom.text = usr?.username.toString()
                            usernum.text = "0"+usr?.phone.toString()

                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }


        })


        val titre = ine.getStringExtra("titre").toString()
        val prix = ine.getStringExtra("prix").toString()
        val desc = ine.getStringExtra("desc").toString()
        val date = ine.getStringExtra("date").toString()

        titre1.text = titre
        prix1.text = prix
        desc1.text = desc
        date1.text = date

    }
}