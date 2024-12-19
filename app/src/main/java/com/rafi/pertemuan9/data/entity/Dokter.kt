package com.rafi.pertemuan9.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dokter")
data class Dokter(
    @PrimaryKey(autoGenerate = true)
    val idDokter: Int = 0,
    val namaDokter: String,
    val spesialis: String,
    val klinik: String,
    val nomorHpDokter: String,
    val jamKerja: String,
)
