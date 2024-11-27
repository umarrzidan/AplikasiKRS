package com.example.aplikasikrs

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasikrs.adapters.MataKuliahAdapter
import com.example.aplikasikrs.models.MataKuliah

class KRSAndaActivity : AppCompatActivity() {
    private var totalSKS: Int = 0 // Variabel untuk menyimpan total SKS yang dipilih

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_krsanda)

        val ubahPilihanButton: Button = findViewById(R.id.ubah_pilihan_button)
        ubahPilihanButton.setOnClickListener {
            val intent = Intent(this, KRSActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.krs_anda)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        // Tombol Kembali
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Spinner Semester
        val spinnerSemester: Spinner = findViewById(R.id.semester_spinner)
        val semesterArray = resources.getStringArray(R.array.semester_array)
        val adapterSemester = ArrayAdapter(this, R.layout.spinner_item, semesterArray)
        adapterSemester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSemester.adapter = adapterSemester

        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Contoh data mata kuliah untuk RecyclerView
        val mataKuliahList = mutableListOf(
            MataKuliah("Multimedia", 2, "SIREG5A"),
            MataKuliah("Multimedia", 2, "SIREG5B"),
            MataKuliah("Multimedia", 2, "SIREG5C"),
            MataKuliah("Praktek Pemrograman Bergerak", 3, "SIREG5A"),
            MataKuliah("Praktek Pemrograman Bergerak", 3, "SIREG5B"),
            MataKuliah("Praktek Pemrograman Bergerak", 3, "SIREG5C"),
            MataKuliah("Teori Pemrograman Bergerak", 3, "SIREG5A"),
            MataKuliah("Teori Pemrograman Bergerak", 3, "SIREG5B"),
            MataKuliah("Teori Pemrograman Bergerak", 3, "SIREG5C"),
            MataKuliah("Keamanan Jaringan", 2, "SIREG5A"),
            MataKuliah("Keamanan Jaringan", 2, "SIREG5B"),
            MataKuliah("Keamanan Jaringan", 2, "SIREG5C"),
            MataKuliah("Perencanaan Sumber Daya Perusahaan", 2, "SIREG5A"),
            MataKuliah("Perencanaan Sumber Daya Perusahaan", 2, "SIREG5A"),
            MataKuliah("Perencanaan Sumber Daya Perusahaan", 2, "SIREG5A")
        )

        // Buat adapter untuk RecyclerView
        val mataKuliahAdapter = MataKuliahAdapter(mataKuliahList) { sksChange ->
            updateTotalSKS(totalSKS + sksChange)
        }

        // Set adapter ke RecyclerView
        recyclerView.adapter = mataKuliahAdapter

        // Inisialisasi total SKS di UI
        updateTotalSKS(totalSKS)
    }
    private fun updateTotalSKS(total: Int) {
        totalSKS = total.coerceAtLeast(0) // Total SKS tidak boleh negatif
        val totalSKSTextView: TextView = findViewById(R.id.totalSKSTextView)
        totalSKSTextView.text = " $totalSKS"
    }

}
