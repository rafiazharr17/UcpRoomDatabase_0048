package com.rafi.pertemuan9.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jadwal")
data class Jadwal(
    @PrimaryKey(autoGenerate = true)
    val idJadwal: Int = 0,
    val dokter: String,
    val namaPasien: String,
    val noHpPasien: String,
    val tanggal: String,
    val status: String,
)