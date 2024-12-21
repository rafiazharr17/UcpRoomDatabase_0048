package com.rafi.pertemuan9.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rafi.pertemuan9.ui.view.dokter.HomeDokterView
import com.rafi.pertemuan9.ui.view.dokter.InsertDokterView
import com.rafi.pertemuan9.ui.view.jadwal.DetailJadwalView
import com.rafi.pertemuan9.ui.view.jadwal.HomeJadwalView
import com.rafi.pertemuan9.ui.view.jadwal.InsertJadwalView
import com.rafi.pertemuan9.ui.view.jadwal.UpdateJadwalView

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
                navigateLihatJadwal = {
                    navController.navigate(DestinasiHomeJadwal.route)
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
            route = DestinasiHomeJadwal.route
        ) {
            HomeJadwalView(
                onDetailClick = {idJadwal ->
                    navController.navigate("${DestinasiDetailJadwal.route}/$idJadwal")
                    println("PengelolaHalaman: idJadwal = $idJadwal")
                },
                onAddJadwal = {
                    navController.navigate(DestinasiInsertJadwal.route)
                },
                navigateLihatDokter = {
                    navController.navigate(DestinasiHomeDokter.route)
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

        composable (
            DestinasiDetailJadwal.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailJadwal.IDJADWAL) {
                    type = NavType.IntType
                }
            )
        ){
            val idJadwal = it.arguments?.getInt(DestinasiDetailJadwal.IDJADWAL)

            idJadwal?.let { idJadwal ->
                DetailJadwalView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateJadwal.route}/$idJadwal")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiUpdateJadwal.routeWithArg,
            arguments = listOf(
                navArgument (DestinasiUpdateJadwal.IDJADWAL) {
                    type = NavType.IntType
                }
            )
        ) {
            UpdateJadwalView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }
}