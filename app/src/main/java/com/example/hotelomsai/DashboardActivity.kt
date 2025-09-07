package com.example.hotelomsai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val btnSeeEntries: Button = findViewById(R.id.btnSeeEntries)
        val btnAddEntry: Button = findViewById(R.id.btnAddEntry)

        btnSeeEntries.setOnClickListener {
            startActivity(Intent(this, GuestListActivity::class.java))
        }

        btnAddEntry.setOnClickListener {
            startActivity(Intent(this, CheckInActivity::class.java))
        }
    }
}
