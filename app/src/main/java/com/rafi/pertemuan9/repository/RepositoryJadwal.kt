package com.rafi.pertemuan9.repository

import com.rafi.pertemuan9.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

interface RepositoryJadwal {
    suspend fun insertJadwal(dokter: Dokter)

    suspend fun deleteJadwal(dokter: Dokter)

    suspend fun updateJadwal(dokter: Dokter)

    fun getAllJadwal(): Flow<List<Dokter>>

    fun getJadwal(idJadwal: String): Flow<Dokter>
}