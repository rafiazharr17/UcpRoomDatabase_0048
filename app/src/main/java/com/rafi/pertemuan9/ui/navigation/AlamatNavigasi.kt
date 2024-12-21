package com.rafi.pertemuan9.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiInsertDokter : AlamatNavigasi {
    override val route = "insert_dokter"
}

object DestinasiHomeDokter : AlamatNavigasi {
    override val route = "home_dokter"
}

object DestinasiInsertJadwal : AlamatNavigasi {
    override val route = "insert_jadwal"
}

object DestinasiHomeJadwal : AlamatNavigasi {
    override val route = "home_jadwal"
}

object DestinasiDetailJadwal : AlamatNavigasi {
    override val route = "detail"
    const val IDJADWAL = "idJadwal"
    val routeWithArg = "$route/{${IDJADWAL}}"
}

object DestinasiUpdateJadwal : AlamatNavigasi {
    override val route = "update"
    const val IDJADWAL = "idJadwal"
    val routeWithArg = "$route/{$IDJADWAL}"
}