package com.example.infoshop

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


open class RecyclerViewAdapter(val OnPubClickListner : onPubClickListener): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val itemTitles = arrayOf("toutes les Categories","Ordinateurs Portables","Imprimantes","Pièces pour PC fixe","Accessoires informatique","Réseau et Connexion","PC bureau","Apps et Logiciels","Pièces pour PC portable","Ecrans et Data Show","Stockage externe","Ondulateurs et Stabilisateurs")
    private val itemImages = intArrayOf(R.drawable.blur1,R.drawable.blur1,R.drawable.blur2,R.drawable.blur3,R.drawable.blur4,R.drawable.blur5,R.drawable.blur6,R.drawable.blur7,R.drawable.blur8,R.drawable.blur9,R.drawable.blur10,R.drawable.blur11)

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var image : ImageView
        var texte : TextView

        init {
            image = itemView.findViewById(R.id.item_image)
            texte = itemView.findViewById(R.id.nom_article)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_model,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.texte.text = itemTitles[position]
        holder.image.setImageResource(itemImages[position])

        holder.itemView.setOnClickListener{v : View ->
            holder.itemView.setOnClickListener {
                OnPubClickListner.onPubClicked_cat(position)
            }
        }
        var b1 = (holder.texte.text == "Ordinateurs Portables")
        var b2 = (holder.texte.text == "Imprimantes")
        var b3 = (holder.texte.text == "Pièces pour PC fixe")
        var b4 = (holder.texte.text == "Accessoires informatique")
        var b5 = (holder.texte.text == "Réseau et Connexion")
        var b6 = (holder.texte.text == "PC bureau")
        var b7 = (holder.texte.text == "Apps et Logiciels")
        var b8 = (holder.texte.text == "Pièces pour PC portable")
        var b9 = (holder.texte.text == "Ecrans et Data Show")
        var b10 = (holder.texte.text == "Stockage externe")
        var b11 = (holder.texte.text == "Ondulateurs et Stabilisateurs")



/*
        val ine = Intent(this,Home::class.java)

        ine.putExtra("b1",b1)
        ine.putExtra("b2",b2)
        ine.putExtra("b3",b3)
        ine.putExtra("b4",b4)
        ine.putExtra("b5",b5)
        ine.putExtra("b6",b6)
        ine.putExtra("b7",b7)
        ine.putExtra("b8",b8)
        ine.putExtra("b9",b9)
        ine.putExtra("b10",b10)
        ine.putExtra("b11",b11)


 */

    }



    override fun getItemCount(): Int {
        return itemTitles.size
    }

}