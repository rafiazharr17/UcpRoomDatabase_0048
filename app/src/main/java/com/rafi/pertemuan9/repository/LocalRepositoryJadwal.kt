package com.rafi.pertemuan9.repository

import com.rafi.pertemuan9.data.dao.JadwalDao
import com.rafi.pertemuan9.data.entity.Dokter
import com.rafi.pertemuan9.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

class LocalRepositoryJadwal(
    private val jadwalDao: JadwalDao
) : RepositoryJadwal {
    override suspend fun insertJadwal(jadwal: Jadwal) {
        jadwalDao.insertJadwal(jadwal)
    }

    override suspend fun deleteJadwal(jadwal: Jadwal) {
        jadwalDao.deletejadwal(jadwal)
    }

    override suspend fun updateJadwal(jadwal: Jadwal) {
        jadwalDao.updatejadwal(jadwal)
    }

    override fun getAllJadwal(): Flow<List<Jadwal>> {
        return jadwalDao.getAlljadwal()
    }

    override fun getJadwal(idJadwal: Int): Flow<Jadwal> {
        return jadwalDao.getJadwal(idJadwal)
    }

    override fun getAllNamaDokter(): Flow<List<Dokter>> {
        return jadwalDao.getAllNamaDokter()
    }
}