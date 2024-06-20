package com.android.trashub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomepageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val btnGeser: MaterialButton = findViewById(R.id.btnGeser)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        // Retrieve the user name passed from InputActivity
        val userName = intent.getStringExtra("user_name")

        btnGeser.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("user_name", userName)
            }
            startActivity(intent)
        }
        supportActionBar?.hide()

        // Setup ViewPager with the adapter
        val contentList = listOf(
            SliderAdapter.SliderContent(R.drawable.slider01, "Reduce", getString(R.string.message_homepage1), getString(R.string.message_quotes1)),
            SliderAdapter.SliderContent(R.drawable.slider02, "Reuse", getString(R.string.message_homepage2), getString(R.string.message_quotes2)),
            SliderAdapter.SliderContent(R.drawable.slider03, "Recycle", getString(R.string.message_homepage3), getString(R.string.message_quotes3))
        )
        val adapter = SliderAdapter(contentList)
        viewPager.adapter = adapter

        // Setup TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, _ ->
            tab.setIcon(R.drawable.selector_dot)
        }.attach()
    }
}
