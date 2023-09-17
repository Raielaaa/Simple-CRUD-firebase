package com.example.exer3honraralphdaniel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.exer3honraralphdaniel.R
import com.example.exer3honraralphdaniel.databinding.ActivityEditBinding
import com.example.exer3honraralphdaniel.databinding.FragmentEditActivityBinding
import com.example.exer3honraralphdaniel.firedb.Dao
import com.example.exer3honraralphdaniel.model.RvListModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.values

class EditActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainActivityViewModel = ViewModelProvider(this@EditActivity)[MainActivityViewModel::class.java]

        val passedName = intent.getStringExtra("etName")
        val passedYear = intent.getStringExtra("etYear")
        val passedNumber = intent.getStringExtra("etNumber")

        binding.apply {
            etName.setText(passedName)
            etYear.setText(passedYear)
            etNumber.setText(passedNumber)

            btnEdit.setOnClickListener {
                if (etName.text.isNotEmpty() || etYear.text.isNotEmpty() || etNumber.text.isNotEmpty()) {
                    mainActivityViewModel.updateModelFromDB(
                        RvListModel(etName.text.toString(), etYear.text.toString(), etNumber.text.toString()),
                        "$passedName-$passedYear-$passedNumber"
                    )
                }
                startActivity(Intent(this@EditActivity, MainActivity::class.java))
            }
        }
    }
}