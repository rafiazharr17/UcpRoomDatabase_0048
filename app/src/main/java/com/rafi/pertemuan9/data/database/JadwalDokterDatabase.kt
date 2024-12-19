package com.rafi.pertemuan9.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rafi.pertemuan9.data.dao.DokterDao
import com.rafi.pertemuan9.data.dao.JadwalDao
import com.rafi.pertemuan9.data.entity.Dokter
import com.rafi.pertemuan9.data.entity.Jadwal

@Database(entities = [Dokter::class, Jadwal::class], version = 1, exportSchema = false)
abstract class JadwalDokterDatabase : RoomDatabase() {
    abstract fun dokterDao(): DokterDao
    abstract fun jadwalDao(): JadwalDao

    companion object{
        @Volatile
        private var Instance: JadwalDokterDatabase? = null

        fun getDatabase(context: Context): JadwalDokterDatabase {
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    JadwalDokterDatabase::class.java,
                    "JadwalDokterDatabase"
                )
                    .build().also { Instance = it }
            })
        }
    }
}