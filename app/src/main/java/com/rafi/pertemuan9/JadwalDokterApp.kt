package com.rafi.pertemuan9

import android.app.Application
import com.rafi.pertemuan9.dependeciesinjection.ContainerApp

class JadwalDokterApp : Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate(){
        super.onCreate()
        containerApp = ContainerApp(this)
    }
}