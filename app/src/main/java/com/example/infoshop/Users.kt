package com.example.infoshop

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Users(
        val id_user: String?=null, val nom: String?=null, val prenom: String?=null,
        val username: String?=null, val email: String?=null, val password: String?=null,
        val adresse: String?=null, val phone: Int?=null, val wilaya: String?=null)




