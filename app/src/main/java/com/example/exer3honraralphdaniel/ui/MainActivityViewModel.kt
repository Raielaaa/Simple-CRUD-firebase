package com.example.exer3honraralphdaniel.ui

import android.annotation.SuppressLint
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exer3honraralphdaniel.firedb.Dao
import com.example.exer3honraralphdaniel.model.RvListModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivityViewModel: ViewModel() {
    val dataRetrievedFromDB: MutableLiveData<ArrayList<RvListModel>> = MutableLiveData()
    val rvListModelForEdit: MutableLiveData<RvListModel> = MutableLiveData()

    val dataRetrievedFromDBLocal: ArrayList<RvListModel> = ArrayList()
    @SuppressLint("StaticFieldLeak")
    private lateinit var uID: String
    private var TAG: String = "MyTag"

    //  clearing all the values from the UI
    fun clearText(etName: EditText, etYear: EditText, etNumber: EditText) {
        etName.setText("")
        etYear.setText("")
        etNumber.setText("")
    }

    //  INSERTING data to firebase
    fun addModelToDB(rvListModel: RvListModel) {
        uID = "${rvListModel.tvName}-${rvListModel.tvYear}-${rvListModel.tvNumber}"
        Dao.add(rvListModel, uID)
    }

    //  Update a value in firebase
    fun updateModelFromDB(rvListModel: RvListModel, parentNode: String) {
        Dao.databaseReference.child(parentNode).apply {
            child("tvName").setValue(rvListModel.tvName)
            child("tvNumber").setValue(rvListModel.tvNumber)
            child("tvYear").setValue(rvListModel.tvYear)
        }
    }

    //  Delete an item in firebase
    fun deleteModelFromDB(rvListModel: RvListModel) {
        Dao.deleteFromDB(rvListModel)
    }

    //  GETTING ALL data from firebase
    fun getModelFromDB() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataRetrievedFromDBLocal.clear()
                for (childSnapshot in snapshot.children) {
                    val tvName = childSnapshot.child("tvName").value.toString()
                    val tvYear = childSnapshot.child("tvYear").value.toString()
                    val tvNumber = childSnapshot.child("tvNumber").value.toString()

                    dataRetrievedFromDBLocal.add(
                        RvListModel(tvName, tvYear, tvNumber)
                    )
                    dataRetrievedFromDB.value = dataRetrievedFromDBLocal
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("MyTag", error.message)
            }
        }

        Dao.databaseReference.addValueEventListener(postListener)
    }
}