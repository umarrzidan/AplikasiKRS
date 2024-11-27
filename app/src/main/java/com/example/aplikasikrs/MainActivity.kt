package com.example.aplikasikrs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference // Referensi ke Firebase Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi Views
        val inputNim: EditText = findViewById(R.id.input_nim)
        val inputPassword: EditText = findViewById(R.id.input_password)
        val btnSelanjutnya: Button = findViewById(R.id.btn_selanjutnya)
        val textBelumMemilikiAkun: TextView = findViewById(R.id.text_belum_memiliki_akun)

        // Inisialisasi Firebase Database
        database = FirebaseDatabase.getInstance().getReference("users")

        // Event tombol "SELANJUTNYA"
        btnSelanjutnya.setOnClickListener {
            val nim = inputNim.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            if (nim.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Harap isi NIM dan Password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses login
            loginUser(nim, password)
        }

        // Event klik "Belum memiliki akun?"
        textBelumMemilikiAkun.setOnClickListener {
            // Pindah ke halaman registrasi
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(nim: String, password: String) {
        // Cari pengguna berdasarkan NIM
        database.child(nim).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Dapatkan password dari database
                    val dbPassword = snapshot.child("password").value.toString()

                    if (dbPassword == password) {
                        Toast.makeText(this@MainActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()
                        // Pindah ke halaman berikutnya
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "Password salah!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "NIM tidak ditemukan!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
