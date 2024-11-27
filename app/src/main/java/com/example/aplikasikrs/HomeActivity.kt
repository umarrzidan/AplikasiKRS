package com.example.aplikasikrs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.aplikasikrs.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup BottomNavigationView
        val navView: BottomNavigationView = binding.navView

        // Setup Navigation Controller
        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        // Set menu IDs as top-level destinations
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        // Connect NavigationView with NavController
        navView.setupWithNavController(navController)

        // Tambahkan logika untuk perubahan warna (otomatis dari menu XML)
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // HomeFragment logic, warna berubah otomatis via selector
                    navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.navigation_dashboard -> {
                    // DashboardFragment logic
                    navController.navigate(R.id.navigation_dashboard)
                    true
                }
                R.id.navigation_notifications -> {
                    // NotificationsFragment logic
                    navController.navigate(R.id.navigation_notifications)
                    true
                }
                else -> false
            }
        }
    }
}
