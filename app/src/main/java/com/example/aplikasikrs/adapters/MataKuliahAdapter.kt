package com.example.aplikasikrs.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasikrs.R
import com.example.aplikasikrs.models.MataKuliah

class MataKuliahAdapter(
    private var mataKuliahList: MutableList<MataKuliah>,
    private val onSksChange: (Int) -> Unit
) : RecyclerView.Adapter<MataKuliahAdapter.ViewHolder>() {

    fun updateData(newList: List<MataKuliah>) {
        mataKuliahList.clear()
        mataKuliahList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matkul, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mataKuliah = mataKuliahList[position]
        holder.bind(mataKuliah, onSksChange)
    }

    override fun getItemCount(): Int = mataKuliahList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mataKuliah: MataKuliah, onSksChange: (Int) -> Unit) {
            val checkBox = itemView.findViewById<CheckBox>(R.id.ivCheckbox)
            val textViewNama = itemView.findViewById<TextView>(R.id.tvNamaMatkul)
            val textViewSksValue = itemView.findViewById<TextView>(R.id.tvSksValue)
            val textViewKelasValue = itemView.findViewById<TextView>(R.id.tvKelasValue)

            // Set MataKuliah name
            textViewNama.text = mataKuliah.nama

            // Set SKS value
            textViewSksValue.text = mataKuliah.sks.toString()

            // Set Kelas value
            textViewKelasValue.text = mataKuliah.kelas // Menampilkan kelas sesuai dengan nilai 'kelas'

            // Handle checkbox checked state
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                onSksChange(if (isChecked) mataKuliah.sks else -mataKuliah.sks)
            }
        }
    }
}



