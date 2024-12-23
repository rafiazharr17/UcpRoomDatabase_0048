package com.rafi.pertemuan9.repository

import com.rafi.pertemuan9.data.entity.Dokter
import com.rafi.pertemuan9.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryJadwal {
    suspend fun insertJadwal(jadwal: Jadwal)

    suspend fun deleteJadwal(jadwal: Jadwal)

    suspend fun updateJadwal(jadwal: Jadwal)

    fun getAllJadwal(): Flow<List<Jadwal>>

    fun getJadwal(idJadwal: Int): Flow<Jadwal>
}