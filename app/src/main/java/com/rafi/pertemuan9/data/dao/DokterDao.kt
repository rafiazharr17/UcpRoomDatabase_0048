package com.rafi.pertemuan9.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rafi.pertemuan9.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

@Dao
interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: Dokter)

    @Query("SELECT * FROM dokter ORDER BY idDokter ASC")
    fun getAllDokter(): Flow<List<Dokter>>
}