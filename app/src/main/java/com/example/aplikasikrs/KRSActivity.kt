package com.example.aplikasikrs
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasikrs.adapters.MataKuliahAdapter
import com.example.aplikasikrs.models.MataKuliah
import com.google.firebase.database.*
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class KRSActivity : AppCompatActivity() {
    private var totalSKS: Int = 0
    private lateinit var database: DatabaseReference
    private lateinit var mataKuliahAdapter: MataKuliahAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_krsactivity)

        // Tombol kembali
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener { onBackPressed() }

        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val mataKuliahList = mutableListOf<MataKuliah>()

        // Adapter untuk RecyclerView
        mataKuliahAdapter = MataKuliahAdapter(mataKuliahList) { sksChange ->
            updateTotalSKS(totalSKS + sksChange)
        }
        recyclerView.adapter = mataKuliahAdapter

        // Inisialisasi Firebase
        database = FirebaseDatabase.getInstance().getReference("mataKuliah")

        // Ambil data dari Firebase
        fetchMataKuliahData()
    }

    private fun fetchMataKuliahData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mataKuliahList = mutableListOf<MataKuliah>()
                for (data in snapshot.children) {
                    val mataKuliah = data.getValue(MataKuliah::class.java)
                    if (mataKuliah != null) {
                        mataKuliahList.add(mataKuliah)
                    }
                }
                mataKuliahAdapter.updateData(mataKuliahList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun updateTotalSKS(total: Int) {
        totalSKS = total.coerceAtLeast(0)
        val totalSKSTextView: TextView = findViewById(R.id.totalSKSTextView)
        totalSKSTextView.text = "SKS yang diambil: $totalSKS"
    }
}
