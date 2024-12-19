package com.rafi.pertemuan9.dependeciesinjection

import android.content.Context
import com.rafi.pertemuan9.data.database.JadwalDokterDatabase
import com.rafi.pertemuan9.repository.LocalRepositoryDokter
import com.rafi.pertemuan9.repository.LocalRepositoryJadwal
import com.rafi.pertemuan9.repository.RepositoryDokter
import com.rafi.pertemuan9.repository.RepositoryJadwal

interface InterfaceContainerApp {
    val repositoryDokter: RepositoryDokter
    val repositoryJadwal: RepositoryJadwal
}

class ContainerApp (private val context: Context) : InterfaceContainerApp {
    override val repositoryDokter: RepositoryDokter by lazy {
        LocalRepositoryDokter(JadwalDokterDatabase.getDatabase(context).dokterDao())
    }

    override val repositoryJadwal: RepositoryJadwal by lazy {
        LocalRepositoryJadwal(JadwalDokterDatabase.getDatabase(context).jadwalDao())
    }
}