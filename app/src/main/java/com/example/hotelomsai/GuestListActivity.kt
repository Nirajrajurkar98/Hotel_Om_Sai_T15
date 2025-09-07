package com.example.hotelomsai

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GuestListActivity : AppCompatActivity() {

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var guestData: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_list)

        val listView: ListView = findViewById(R.id.listViewGuests)
        val tvTotalGuests: TextView = findViewById(R.id.tvTotalGuests)
        val searchView: SearchView = findViewById(R.id.searchView)

        val db = DatabaseHelper(this)
        val logs = db.getAllGuests()

        guestData = logs.mapIndexed { index, guest ->
            "${index + 1}) ${guest.firstName} ${guest.lastName} - Room: ${guest.roomNo} (Date: ${guest.date})"
        }.toMutableList()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, guestData)
        listView.adapter = adapter

        tvTotalGuests.text = "Total Guests: ${logs.size}"

        // Make search view always open
        searchView.isIconified = false
        searchView.clearFocus()

        // Enable searching
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }
}
