package com.example.infoshop

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.cardview.view.*

import kotlin.collections.ArrayList

class myrecadapter(var myarray : ArrayList<Pub>, val OnPubClickListner : onPubClickListener) : RecyclerView.Adapter<myrecadapter.ViewHolder>() {
    val refe = Firebase.storage.reference

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
        val textt = itemView.textt as TextView
        val image = itemView.image as ImageView
        val prix = itemView.prixxx as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview,parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pub:Pub = myarray[position]
        holder.textt.text = pub.titre
        holder.image.setImageResource(R.drawable.water)
        holder.prix.text = pub.prix.toString()+"DA"

        refe.child(myarray[position].img_p!!).getBytes(1024*1024).addOnSuccessListener(
            OnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                holder.image.setImageBitmap(bitmap)
            })
        holder.itemView.setOnClickListener {
            OnPubClickListner.onPubClicked_pub(position)
        }

    }

    override fun getItemCount(): Int {
        return myarray.size
    }
}



