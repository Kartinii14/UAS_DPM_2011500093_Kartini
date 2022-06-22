package com.example.uasdpm2011500093

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var adpDataDosen: AdptDosen
    private lateinit var dataDosen: ArrayList<DtaDosen>
    private lateinit var lvDataDosen: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.button_tambah)
        lvDataDosen = findViewById(R.id.lv_data_dosen)
        linTidakAda = findViewById(R.id.lin_tidak_ada)

        dataDosen = ArrayList()
        adpDataDosen = AdptDosen(this@MainActivity, dataDosen)

        lvDataDosen.adapter = adpDataDosen

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, EntryDosen::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) refresh()
    }

    private fun refresh() {
        val db = DtBase(this@MainActivity)
        val data = db.tampil()
        repeat(dataDosen.size) { dataDosen.removeFirst() }
        if (data.count > 0) {
            while (data.moveToNext()) {
                val dataDosen = DtaDosen(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpDataDosen.add(dataDosen)
                adpDataDosen.notifyDataSetChanged()
            }
            lvDataDosen.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE
        } else {
            lvDataDosen.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}