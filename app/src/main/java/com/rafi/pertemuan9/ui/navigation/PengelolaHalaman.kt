package com.rafi.pertemuan9.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rafi.pertemuan9.ui.view.dokter.HomeDokterView
import com.rafi.pertemuan9.ui.view.dokter.InsertDokterView
import com.rafi.pertemuan9.ui.view.jadwal.InsertJadwalView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeDokter.route
    ) {
        composable(
            route = DestinasiHomeDokter.route
        ) {
            HomeDokterView(
                onDetailClick = {},
                onAddDokter = {
                    navController.navigate(DestinasiInsertDokter.route)
                },
                modifier = modifier
            )
        }

        composable (
            route = DestinasiInsertDokter.route
        ) {
            InsertDokterView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertJadwal.route
        ) {
            InsertJadwalView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}