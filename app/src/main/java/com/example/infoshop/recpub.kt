package com.example.infoshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cardview.view.image

class recpub(val myarray : ArrayList<String> ) : RecyclerView.Adapter<recpub.ViewHolder>() {
    val refe = Firebase.storage.reference
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
        val image = itemView.image as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card1,parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(R.drawable.water)
        Picasso.get().load(myarray[position]).into(holder.image)
    }

    override fun getItemCount(): Int {
        return myarray.size
    }



}