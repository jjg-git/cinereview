package com.mobdeve.s15.gironasayasranario.cinereview

import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class DBController {
    companion object {
        private val db = Firebase.firestore
        fun getData(collection: String): Task<QuerySnapshot> {
            return db.collection(collection).get()
        }
    }
}