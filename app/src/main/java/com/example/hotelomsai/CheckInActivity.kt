package com.example.hotelomsai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)

        val etFirstName: EditText = findViewById(R.id.etFirstName)
        val etLastName: EditText = findViewById(R.id.etLastName)
        val etRoomNo: EditText = findViewById(R.id.etRoomNumber)

        val btnSave: Button = findViewById(R.id.btnSave)
        val btnUpdate: Button = findViewById(R.id.btnUpdate)
        val btnCheckout: Button = findViewById(R.id.btnCheckout)
        val btnCancel: Button = findViewById(R.id.btnCancel)

        btnSave.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val roomNo = etRoomNo.text.toString()

            val db = DatabaseHelper(this)
            val success = db.insertGuest(firstName, lastName, roomNo)

            if (success) {
                Toast.makeText(this, "Guest saved successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, GuestListActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error saving guest!", Toast.LENGTH_SHORT).show()
            }
        }

        btnUpdate.setOnClickListener {
            Toast.makeText(this, "Guest info updated!", Toast.LENGTH_SHORT).show()
        }

        btnCheckout.setOnClickListener {
            Toast.makeText(this, "Guest checked out!", Toast.LENGTH_SHORT).show()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }
}
