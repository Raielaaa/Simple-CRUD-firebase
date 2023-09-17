package com.example.exer3honraralphdaniel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exer3honraralphdaniel.R
import com.example.exer3honraralphdaniel.databinding.RvListItemBinding
import com.example.exer3honraralphdaniel.model.RvListModel
import com.example.exer3honraralphdaniel.ui.MainActivity
import com.example.exer3honraralphdaniel.ui.MainActivityViewModel

class RvListAdapter(
    private val mainActivityViewModel: MainActivityViewModel
): RecyclerView.Adapter<RvListAdapter.RvListViewHolder>() {
    inner class RvListViewHolder(private val _binding: RvListItemBinding): RecyclerView.ViewHolder(_binding.root) {
        fun bind(mainActivityViewModel: MainActivityViewModel, rvListModel: RvListModel, tvEdit: TextView, tvDelete: TextView) {
            _binding.apply {
                tvName.text = rvListModel.tvName
                tvYear.text = rvListModel.tvYear
                tvNumber.text = rvListModel.tvNumber

                tvDelete.setOnClickListener {
                    mainActivityViewModel.deleteModelFromDB(rvListModel)
                }
                tvEdit.setOnClickListener {
                    mainActivityViewModel.rvListModelForEdit.value = rvListModel
                }
            }
        }
    }

    val collections: ArrayList<RvListModel> = ArrayList()

    fun addToList(rvListModel: List<RvListModel>) {
        collections.apply {
            clear()
            for (i in rvListModel) add(i)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvListViewHolder {
        val binding: RvListItemBinding = RvListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RvListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return collections.size
    }

    override fun onBindViewHolder(holder: RvListViewHolder, position: Int) {
        holder.bind(mainActivityViewModel, collections[position], holder.itemView.findViewById(R.id.tvEdit), holder.itemView.findViewById(R.id.tvDelete))
    }


}