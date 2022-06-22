package com.example.uasdpm2011500093

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class EntryDosen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_dosen)

        val modeEdit = intent.hasExtra("NIDN") && intent.hasExtra("nama_dosen") &&
                intent.hasExtra("Jabatan") && intent.hasExtra("golongan_pangkat") &&
                intent.hasExtra("Pendidikan") && intent.hasExtra("Keahlian") &&
                intent.hasExtra("Program_studi")
        title = if (modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etNIDN = findViewById<EditText>(R.id.et_NIDN)
        val etNmDosen = findViewById<EditText>(R.id.et_nm_dosen)
        val spnJabatan = findViewById<Spinner>(R.id.spn_jabatan)
        val spnGolongan = findViewById<Spinner>(R.id.spn_golongan)
        val rdS2 = findViewById<RadioButton>(R.id.rd_S2)
        val rdS3 = findViewById<RadioButton>(R.id.rd_S3)
        val etKeahlian = findViewById<EditText>(R.id.et_keahlian)
        val etStudi = findViewById<EditText>(R.id.et_studi)
        val btnSimpan = findViewById<Button>(R.id.btn_simpan)

        val Jabatan = arrayOf("Tenaga Pengajar", "Asisten Ahli", "Lektor", "Lektor Kepala", "Guru Besar")
        val adpJbtn = ArrayAdapter(
            this@EntryDosen,
            android.R.layout.simple_spinner_dropdown_item,
            Jabatan
        )
        spnJabatan.adapter = adpJbtn

        val Pangkat = arrayOf("III/a - Penata Muda", "III/b - Penata Muda Tingkat I", "III/c - Penata", "III/d - Penata Tingkat I", "IV/a - Pembina", "IV/b - Pembina Tingkat I", "IV/c - Pembina Utama Muda" , "IV/d - Pembina Utama Madya", "IV/e - Pembina Utama")
        val adpGol = ArrayAdapter(
            this@EntryDosen,
            android.R.layout.simple_spinner_dropdown_item,
            Pangkat
        )
        spnGolongan.adapter = adpGol

        if (modeEdit) {
            val NIDN = intent.getStringExtra("NIDN")
            val nama_dosen = intent.getStringExtra("Nama Dosen")
            val PosisiJabatan = intent.getStringExtra("Jabatan")
            val GolonganPangkat = intent.getStringExtra("Golongan Pangkat")
            val Pendidikan = intent.getStringExtra("Pendidikan Terakhir")
            val Keahlian = intent.getStringExtra("Bidang Keahlian")
            val Program_studi = intent.getStringExtra("Program Studi")

            etNIDN.setText(NIDN)
            etNmDosen.setText(nama_dosen)
            spnJabatan.setSelection(Jabatan.indexOf(PosisiJabatan))
            spnGolongan.setSelection(Pangkat.indexOf(GolonganPangkat))
            if (Pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etKeahlian.setText(Keahlian)
            etStudi.setText(Program_studi)
        }
        etNIDN.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if ("${etNIDN.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked)) {
                val db = DtBase(this@EntryDosen)
                db.Nidn = "${etNIDN.text}"
                db.NmDosen = "${etNmDosen.text}"
                db.jabatan = spnJabatan.selectedItem as String
                db.GolPangkat = spnGolongan.selectedItem as String
                db.pendidikan = if (rdS2.isChecked) "S2" else "S3"
                db.keahlian = "${etKeahlian.text}"
                db.ProgramStudi = "${etStudi.text}"
                if (if (!modeEdit) db.simpan() else db.ubah("${etNIDN.text}")){
                    Toast.makeText(
                        this@EntryDosen,
                        "Data dosen berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else
                    Toast.makeText(
                        this@EntryDosen,
                        "Data dosen gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@EntryDosen,
                    "Data dosen belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}