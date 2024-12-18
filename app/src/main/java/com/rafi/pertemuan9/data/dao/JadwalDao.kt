package com.rafi.pertemuan9.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rafi.pertemuan9.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {
    @Insert
    suspend fun insertJadwal(jadwal: Jadwal)

    @Delete
    suspend fun deletejadwal(jadwal: Jadwal)

    @Update
    suspend fun updatejadwal(jadwal: Jadwal)

    @Query("SELECT * FROM jadwal ORDER BY namaPasien ASC")
    fun getAlljadwal(): Flow<List<Jadwal>>

    @Query("SELECT * FROM jadwal WHERE idJadwal = :idJadwal")
    fun getjadwal(idJadwal : String): Flow<Jadwal>
}