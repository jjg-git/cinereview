package com.mobdeve.s15.gironasayasranario.cinereview

import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class DBController {
    companion object {
        private val db = Firebase.firestore
        fun getData(collection: String): Task<QuerySnapshot> {
            return db.collection(collection).get()
        }

        fun getDataWhere(collection: String, field: String, value:Any): Task<QuerySnapshot> {
            return db.collection(collection).whereEqualTo(field, value).get()
        }

        fun getDocument(collection: String): CollectionReference {
            return db.collection(collection)
        }

        fun insertData(collection: String, data: HashMap<String, Any?>): Task<DocumentReference> {
            return db.collection(collection).add(data)
        }

        fun updateData(collection: String, doc: String, newData: Map<String, Any>): Task<Void> {
            return db.collection(collection).document(doc).update(newData)
        }

        fun deleteData(collection: String, doc: String): Task<Void> {
            return db.collection(collection).document(doc).delete()
        }
    }
}