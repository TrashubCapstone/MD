package com.android.trashub

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.trashub.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Update title with user name if passed from HomepageActivity
        val userName = intent.getStringExtra("user_name")
        val titleTextView: TextView = findViewById(R.id.title2)
        if (!userName.isNullOrEmpty()) {
            titleTextView.text = "$userName"
        }

        // Update image if passed from NotificationsFragment
        val imageView: ImageView = findViewById(R.id.image)
        val imageResId = intent.getIntExtra("profile_image", -1)
        if (imageResId != -1) {
            imageView.setImageResource(imageResId)
        }
    }
}