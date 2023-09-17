package com.example.exer3honraralphdaniel.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.exer3honraralphdaniel.adapter.RvListAdapter
import com.example.exer3honraralphdaniel.databinding.ActivityMainBinding
import com.example.exer3honraralphdaniel.firedb.Dao
import com.example.exer3honraralphdaniel.model.RvListModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var rvListAdapter: RvListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainActivityViewModel = ViewModelProvider(this@MainActivity)[MainActivityViewModel::class.java]
        initButtonFunction()
        initRecyclerView()
        initObservers()
    }

    //  displaying all data from firebase to UI
    @SuppressLint("NotifyDataSetChanged")
    private fun initObservers() {
        mainActivityViewModel.apply {
            dataRetrievedFromDB.observe(this@MainActivity) {
                rvListAdapter.apply {
                    if (it != null) addToList(it)
                    else collections.clear()
                    notifyDataSetChanged()
                }
            }
            rvListModelForEdit.observe(this@MainActivity) {
                val intent: Intent = Intent(this@MainActivity, EditActivity()::class.java)
                intent.putExtra("etName", it.tvName)
                intent.putExtra("etYear", it.tvYear)
                intent.putExtra("etNumber", it.tvNumber)
                startActivity(intent)
            }
        }
    }

    //  initializing recyclerview
    private fun initRecyclerView() {
        rvListAdapter = RvListAdapter(mainActivityViewModel)
        binding.rvMain.adapter = rvListAdapter
        // Initializing the contents of the RecyclerView
        mainActivityViewModel.getModelFromDB()
    }

    //  configuring create button function
    private fun initButtonFunction() {
        binding.apply {
            btnCreate.setOnClickListener {
                val nameFromView = etName.text.toString()
                val yearFromView = etYear.text.toString()
                val numberFromView = etNumber.text.toString()

                if (nameFromView.isNotEmpty() || yearFromView.isNotEmpty() || numberFromView.isNotEmpty()) {
                    mainActivityViewModel.apply {
                        clearText(etName, etYear, etNumber)
                        addModelToDB(
                            RvListModel(nameFromView, yearFromView, numberFromView)
                        )
                        getModelFromDB()
                    }

                    Toast.makeText(this@MainActivity, "Profile inserted", Toast.LENGTH_LONG).show()
                }
                else Toast.makeText(this@MainActivity, "Please complete all the input fields", Toast.LENGTH_LONG).show()
            }
        }
    }
}