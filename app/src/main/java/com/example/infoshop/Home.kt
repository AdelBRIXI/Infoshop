package com.example.infoshop

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.close
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.content_main.*


class Home : AppCompatActivity() , onPubClickListener , NavigationView.OnNavigationItemSelectedListener      {


    val myauth = FirebaseAuth.getInstance()
    var mstorage : StorageReference? = null
    lateinit var recyclerView: RecyclerView
    private lateinit var recycleadapter : myrecadapter
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null
    var searchArray : ArrayList<Pub>? = null
    var myarray : ArrayList<Pub>? = null
    var myarray_1 : ArrayList<Pub>? = null
    var myarray_2 : ArrayList<Pub>? = null
    var myarray_3 : ArrayList<Pub>? = null
    var myarray_4 : ArrayList<Pub>? = null
    var myarray_5 : ArrayList<Pub>? = null
    var myarray_6 : ArrayList<Pub>? = null
    var myarray_7 : ArrayList<Pub>? = null
    var myarray_8 : ArrayList<Pub>? = null
    var myarray_9 : ArrayList<Pub>? = null
    var myarray_10 : ArrayList<Pub>? = null
    var myarray_11 : ArrayList<Pub>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val drawerLayout :DrawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)

       val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener (this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)




        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        RecyclerView.layoutManager = layoutManager
        adapter = RecyclerViewAdapter(this@Home)
        RecyclerView.adapter = adapter




         searchArray = ArrayList()
         myarray = ArrayList()
         myarray_1 = ArrayList()
         myarray_2 = ArrayList()
         myarray_3 = ArrayList()
         myarray_4 = ArrayList()
         myarray_5 = ArrayList()
         myarray_6 = ArrayList()
         myarray_7 = ArrayList()
         myarray_8 = ArrayList()
         myarray_9 = ArrayList()
         myarray_10 = ArrayList()
         myarray_11 = ArrayList()



        myrec.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)


        val ref = FirebaseDatabase.getInstance().getReference("publications")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (e in snapshot.children){
                        var usr = e.getValue(Pub::class.java)
                        myarray?.add(usr!!)
                        if (usr!!.id_cat=="Ordinateurs Portables") myarray_1?.add(usr!!)
                        if (usr!!.id_cat=="Imprimantes") myarray_2?.add(usr!!)
                        if (usr!!.id_cat=="Pièces pour PC fixe") myarray_3?.add(usr!!)
                        if (usr!!.id_cat=="Accessoires informatique") myarray_4?.add(usr!!)
                        if (usr!!.id_cat=="Réseau et Connexion") myarray_5?.add(usr!!)
                        if (usr!!.id_cat=="PC bureau") myarray_6?.add(usr!!)
                        if (usr!!.id_cat=="Apps et Logiciels") myarray_7?.add(usr!!)
                        if (usr!!.id_cat=="Pièces pour PC portable") myarray_8?.add(usr!!)
                        if (usr!!.id_cat=="Ecrans et Data Show") myarray_9?.add(usr!!)
                        if (usr!!.id_cat=="Stockage externe") myarray_10?.add(usr!!)
                        if (usr!!.id_cat=="Ondulateurs et Stabilisateurs") myarray_11?.add(usr!!)
                    }
                    myrec.adapter = myrecadapter(myarray!!,this@Home)
                }
            }


            override fun onCancelled(error: DatabaseError) {
            }

        })


        //photo profile
        mstorage = Firebase.storage.reference
        var mail = myauth.currentUser?.email
        mstorage!!.child(mail!!).getBytes(1024*1024).addOnSuccessListener(
                OnSuccessListener {
                    val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                    photoProfil.setImageBitmap(bitmap)
                })



        //search
        recyclerView=findViewById(R.id.myrec)
        recycleadapter= myrecadapter(myarray!!,this@Home)
        recyclerView.adapter = recycleadapter


        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //recycleadapter.filter.filter(newText)
                searchArray?.clear()
                myarray?.clear()
                myarray_1?.clear()
                myarray_2?.clear()
                myarray_3?.clear()
                myarray_4?.clear()
                myarray_5?.clear()
                myarray_6?.clear()
                myarray_7?.clear()
                myarray_8?.clear()
                myarray_9?.clear()
                myarray_10?.clear()
                myarray_11?.clear()
                val ref = FirebaseDatabase.getInstance().getReference("publications")
                ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            for (e in snapshot.children){
                                var usr = e.getValue(Pub::class.java)
                                if ((newText.toString() in usr!!.titre.toString()) || (newText.toString() in usr!!.titre.toString())){
                                    searchArray?.add(usr!!)
                                    myarray?.add(usr!!)
                                    if (usr!!.id_cat=="Ordinateurs Portables") myarray_1?.add(usr!!)
                                    if (usr!!.id_cat=="Imprimantes") myarray_2?.add(usr!!)
                                    if (usr!!.id_cat=="Pièces pour PC fixe") myarray_3?.add(usr!!)
                                    if (usr!!.id_cat=="Accessoires informatique") myarray_4?.add(usr!!)
                                    if (usr!!.id_cat=="Réseau et Connexion") myarray_5?.add(usr!!)
                                    if (usr!!.id_cat=="PC bureau") myarray_6?.add(usr!!)
                                    if (usr!!.id_cat=="Apps et Logiciels") myarray_7?.add(usr!!)
                                    if (usr!!.id_cat=="Pièces pour PC portable") myarray_8?.add(usr!!)
                                    if (usr!!.id_cat=="Ecrans et Data Show") myarray_9?.add(usr!!)
                                    if (usr!!.id_cat=="Stockage externe") myarray_10?.add(usr!!)
                                    if (usr!!.id_cat=="Ondulateurs et Stabilisateurs") myarray_11?.add(usr!!)
                                }
                            }
                            myrec.adapter = myrecadapter(searchArray!!,this@Home)
                        }
                    }


                    override fun onCancelled(error: DatabaseError) {
                    }

                })
                return false
            }

        }

        )


        //val logout = findViewById<id>(R.id.nav_logout)



        val int8 = Intent(this, Profile::class.java)


        photoProfil.setOnClickListener(){
            startActivity(int8)
            finish()

        }
        val int9 = Intent(this, Add::class.java)
        fab.setOnClickListener(){
            startActivity(int9)
            finish()

        }

        val nav = findViewById<View>(R.id.nav_view)

    }

    fun signout(){
        myauth.signOut()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_logout -> {
                Toast.makeText(this,"Logging out ...",Toast.LENGTH_SHORT).show()
                signout()
                myauth.addAuthStateListener {
                    if (myauth.currentUser ==null){
                        val ine = Intent(this,Welcome::class.java)
                        startActivity(ine)
                    }
                }

                finish()
            }
            R.id.nav_profile ->{
                Toast.makeText(this,"profile  ...",Toast.LENGTH_SHORT).show()
                val ine1 = Intent(this,Profile::class.java)
                startActivity(ine1)
            }

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onPubClicked_pub(position: Int) {
        Toast.makeText(this, myarray!![position].titre, Toast.LENGTH_SHORT).show()
        val ine = Intent(this,showpub::class.java)
        ine.putExtra("titre",myarray!![position].titre.toString())
        ine.putExtra("prix",myarray!![position].prix.toString())
        ine.putExtra("desc",myarray!![position].desc.toString())
        ine.putExtra("date",myarray!![position].datee.toString())
        ine.putExtra("id",myarray!![position].id_pub.toString())
        ine.putExtra("id_usr",myarray!![position].id_usr.toString())
        startActivity(ine)
        finish()
    }

    override fun onPubClicked_cat(position: Int) {

        if (position == 0) {
            myarray?.clear()
            val ref = FirebaseDatabase.getInstance().getReference("publications")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (e in snapshot.children){
                            var usr = e.getValue(Pub::class.java)
                                myarray?.add(usr!!)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
            myrec.adapter = myrecadapter(myarray!!,this@Home)
        }


        if (position == 1) {
            myrec.adapter = myrecadapter(myarray_1!!,this@Home)
        }
        if (position == 2) {
            myrec.adapter = myrecadapter(myarray_2!!,this@Home)
        }
        if (position == 3) {
            myrec.adapter = myrecadapter(myarray_3!!,this@Home)
        }
        if (position == 4) {
            myrec.adapter = myrecadapter(myarray_4!!,this@Home)
        }
        if (position == 5) {
            myrec.adapter = myrecadapter(myarray_5!!,this@Home)
        }
        if (position == 6) {
            myrec.adapter = myrecadapter(myarray_6!!,this@Home)
        }
        if (position == 7) {
            myrec.adapter = myrecadapter(myarray_7!!,this@Home)
        }
        if (position == 8) {
            myrec.adapter = myrecadapter(myarray_8!!,this@Home)
        }
        if (position == 9) {
            myrec.adapter = myrecadapter(myarray_9!!,this@Home)

        }
        if (position == 10) {
            myrec.adapter = myrecadapter(myarray_10!!,this@Home)
        }
        if (position == 11) {
            myrec.adapter = myrecadapter(myarray_11!!,this@Home)
        }
    }


}
