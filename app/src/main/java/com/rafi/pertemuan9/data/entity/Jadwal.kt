package com.rafi.pertemuan9.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "jadwal",
    foreignKeys = [
        ForeignKey(
            entity = Dokter::class,
            parentColumns = arrayOf("idDokter"),
            childColumns = arrayOf("dokter"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["idDokter"])]
)
data class Jadwal(
    @PrimaryKey
    val idJadwal: String,
    @ColumnInfo(index = true)
    val dokter: String,
    val namaPasien: String,
    val noHpPasien: String,
    val tanggal: String,
    val status: String,
)