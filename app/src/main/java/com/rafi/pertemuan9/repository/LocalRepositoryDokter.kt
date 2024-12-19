package com.rafi.pertemuan9.repository

import com.rafi.pertemuan9.data.dao.DokterDao
import com.rafi.pertemuan9.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDokter(
    private val dokterDao: DokterDao
) : RepositoryDokter {
    override suspend fun insertDokter(dokter: Dokter) {
        dokterDao.insertDokter(dokter)
    }

    override fun getAllDokter(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }
}