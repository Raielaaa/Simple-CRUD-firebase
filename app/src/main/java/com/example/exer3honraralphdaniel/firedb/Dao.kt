package com.example.exer3honraralphdaniel.firedb

import com.example.exer3honraralphdaniel.model.RvListModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object Dao {

    //  firebase realtime database SINGLETON initialization using object class
    var databaseReference: DatabaseReference

    init {
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference(RvListModel::class.simpleName!!)
    }

    fun add(rvListModel: RvListModel, childNodeID: String) = databaseReference.child(childNodeID).setValue(rvListModel)

    fun deleteFromDB(rvListModel: RvListModel) {
        databaseReference.child("${rvListModel.tvName}-${rvListModel.tvYear}-${rvListModel.tvNumber}").removeValue()
    }
}