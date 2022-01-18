package com.example.infoshop

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Pub(
        val id_pub: String?=null, val active: String?=null, val titre: String?=null,
        val prix: Int?=null, val id_usr: String?=null, val id_cat: String?=null,
        val desc: String?=null, val datee: String?=null , val img_p:String?=null
        )