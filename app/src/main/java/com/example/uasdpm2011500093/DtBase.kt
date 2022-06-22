package com.example.uasdpm2011500093

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DtBase(context: Context): SQLiteOpenHelper(context, "campuss", null, 1) {
    var Nidn = ""
    var NmDosen = ""
    var jabatan = ""
    var GolPangkat = ""
    var pendidikan = ""
    var keahlian = ""
    var ProgramStudi = ""

    private val tabel = "lecturer"
    private var sql = ""

    override fun onCreate(db: SQLiteDatabase?) {
        sql = """create table $tabel(
            NIDN char(10) primary key, 
            nama_dosen varchar(50) not null, 
            Jabatan varchar(15) not null, 
            golongan_pangkat varchar(30) not null, 
            Pendidikan char(2) not null, 
            Keahlian varchar(30) not null, 
            Program_studi varchar(50) not null
            )
        """.trimIndent()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        sql = "drop table if exists $tabel"
        db?.execSQL(sql)
    }

    fun simpan(): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("NIDN", Nidn)
            put("nama_dosen", NmDosen)
            put("Jabatan", jabatan)
            put("golongan_pangkat", GolPangkat)
            put("Pendidikan", pendidikan)
            put("Keahlian", keahlian)
            put("Program_studi", ProgramStudi)
        }
        val cmd = db.insert(tabel, null, cv)
        db.close()
        return cmd != -1L
    }

    fun ubah(kode: String): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("nama_dosen", NmDosen)
            put("Jabatan", jabatan)
            put("golongan_pangkat", GolPangkat)
            put("Pendidikan", pendidikan)
            put("Keahlian", keahlian)
            put("Program_studi", ProgramStudi)
        }
        val cmd = db.update(tabel, cv, "NIDN = ?", arrayOf(kode))
        db.close()
        return cmd != -1
    }

    fun hapus(kode: String): Boolean {
        val db = writableDatabase
        val cmd = db.delete(tabel, "NIDN = ?", arrayOf(kode))
        return cmd != -1
    }

    fun tampil(): Cursor {
        val db = writableDatabase
        val reader = db.rawQuery("select * from $tabel", null)
        return reader
    }

}