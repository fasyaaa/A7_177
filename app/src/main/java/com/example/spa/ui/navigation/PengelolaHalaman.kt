package com.example.spa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.spa.ui.home.page.DestinasiHomeInti
import com.example.spa.ui.home.page.HomeIntiScreen
import com.example.spa.ui.jenisTrapi.detail.page.DestinasiDetailJenisTrapi
import com.example.spa.ui.jenisTrapi.detail.page.DetailJenisTrapiScreen
import com.example.spa.ui.jenisTrapi.home.page.HomeJenisTrapiScreen
import com.example.spa.ui.jenisTrapi.home.page.JenisTrapiHome
import com.example.spa.ui.jenisTrapi.insert.page.DestinasiInsertJeTEntry
import com.example.spa.ui.jenisTrapi.insert.page.EntryJeTScreen
import com.example.spa.ui.jenisTrapi.update.page.DestinasiUpdateJeT
import com.example.spa.ui.jenisTrapi.update.page.UpdateJeTScreen
import com.example.spa.ui.pasien.detail.page.DestinasiDetailPasien
import com.example.spa.ui.pasien.detail.page.DetailPasienScreen
import com.example.spa.ui.pasien.home.page.HomePasienScreen
import com.example.spa.ui.pasien.home.page.PasienHome
import com.example.spa.ui.pasien.insert.page.DestinasiInsertPasienEntry
import com.example.spa.ui.pasien.insert.page.EntryPasScreen
import com.example.spa.ui.pasien.update.page.DestinasiUpdatePas
import com.example.spa.ui.pasien.update.page.UpdatePasScreen
import com.example.spa.ui.sesi.detail.page.DestinasiDetailSesi
import com.example.spa.ui.sesi.detail.page.DetailSesiScreen
import com.example.spa.ui.sesi.home.page.HomeSesiScreen
import com.example.spa.ui.sesi.home.page.SesiHome
import com.example.spa.ui.sesi.insert.page.DestinasiInsertSesiEntry
import com.example.spa.ui.sesi.insert.page.EntrySsScreen
import com.example.spa.ui.sesi.update.page.DestinasiUpdateSs
import com.example.spa.ui.sesi.update.page.UpdateSsScreen
import com.example.spa.ui.terapis.detail.page.DestinasiDetailTerapis
import com.example.spa.ui.terapis.detail.page.DetailTerapisScreen
import com.example.spa.ui.terapis.home.page.DestinasiHomeTerapis
import com.example.spa.ui.terapis.home.page.TerapisHomeScreen
import com.example.spa.ui.terapis.insert.page.DestinasiInsertTpsEntry
import com.example.spa.ui.terapis.insert.page.EntryTpsScreen
import com.example.spa.ui.terapis.update.page.DestinasiUpdateTps
import com.example.spa.ui.terapis.update.page.UpdateTpsScreen

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeInti.route,
        modifier = Modifier
    ){
        composable(DestinasiHomeInti.route){
            HomeIntiScreen(
                navigateToPas = {navController.navigate(PasienHome.route)},
                navigateToTps = {navController.navigate(DestinasiHomeTerapis.route)},
                navigateToJeT = {navController.navigate(JenisTrapiHome.route)},
                navigateToSs = {navController.navigate(SesiHome.route)},
            )
        }

//      Pasien Page
        composable(PasienHome.route){
            HomePasienScreen(
                navigateToItemEntryPas = {navController.navigate(DestinasiInsertPasienEntry.route)},
                onDetailPasClick = {
                        idPasien ->
                    navController.navigate("${DestinasiDetailPasien.route}/$idPasien")
                }
            )
        }

//      Insert Pasien Page
        composable(DestinasiInsertPasienEntry.route){
            EntryPasScreen(navigatePasBack = {
                navController.navigate(PasienHome.route) {
                    popUpTo(PasienHome.route) {
                        inclusive = true
                    }
                }
            })
        }

//      Detail Pasien Page
        composable(DestinasiDetailPasien.routesWithArg, arguments = listOf(navArgument(DestinasiDetailPasien.idPasien) {
            type = NavType.IntType }
        )
        ){
            val idPasien = it.arguments?.getInt(DestinasiDetailPasien.idPasien)
            idPasien?.let { idPasien ->
                DetailPasienScreen(
                    navigateToItemPasUpdate = { navController.navigate("${DestinasiUpdatePas.route}/$idPasien") },
                    navigatePasBack = { navController.navigate(PasienHome.route) {
                        popUpTo(PasienHome.route) { inclusive = true }
                    }
                    }
                )
            }
        }

//      Update Pasien Page
        composable(DestinasiUpdatePas.routesWithArg, arguments = listOf(navArgument(DestinasiDetailPasien.idPasien){
            type = NavType.IntType })
        ){
            val idPasien = it.arguments?.getInt(DestinasiUpdatePas.idPasien)
            if (idPasien == null) {
                navController.popBackStack()
            } else {
                UpdatePasScreen(
                    onPasBack = { navController.popBackStack() },
                    onPasNavigate = { navController.popBackStack() }
                )
            }
        }

//      Terapis Page
        composable(DestinasiHomeTerapis.route){
            TerapisHomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiInsertTpsEntry.route)},
                onDetailClick = {
                        idTerapis ->
                    navController.navigate("${DestinasiDetailTerapis.route}/$idTerapis")
                }
            )
        }

//      Insert Terapis
        composable(DestinasiInsertTpsEntry.route){
            EntryTpsScreen(navigateBack = {
                navController.navigate(DestinasiHomeTerapis.route) {
                    popUpTo(DestinasiHomeTerapis.route) {
                        inclusive = true
                    }
                }
            })
        }

//      Detail Terapis
        composable(DestinasiDetailTerapis.routesWithArg, arguments = listOf(navArgument(DestinasiDetailTerapis.idTerapis) {
            type = NavType.IntType }
        )
        ){
            val idTerapis = it.arguments?.getInt(DestinasiDetailTerapis.idTerapis)
            idTerapis?.let { idTerapis ->
                DetailTerapisScreen(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateTps.route}/$idTerapis") },
                    navigateBack = { navController.navigate(DestinasiHomeTerapis.route) {
                        popUpTo(DestinasiHomeTerapis.route) { inclusive = true }
                    }
                    }
                )
            }
        }

//      Update Terapis
        composable(DestinasiUpdateTps.routesWithArg, arguments = listOf(navArgument(DestinasiDetailTerapis.idTerapis){
            type = NavType.IntType })
        ){
            val idTerapis = it.arguments?.getInt(DestinasiUpdateTps.idTerapis)
            if (idTerapis == null) {
                navController.popBackStack()
            } else {
                UpdateTpsScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

//      Jenis Terapi Page
        composable(JenisTrapiHome.route){
            HomeJenisTrapiScreen(
                navigateToItemEntry = {navController.navigate(DestinasiInsertJeTEntry.route)},
                onDetailClick = {
                        idJenisTrapi ->
                    navController.navigate("${DestinasiDetailJenisTrapi.route}/$idJenisTrapi")
                }
            )
        }

//      Insert Jenis Terapi
        composable(DestinasiInsertJeTEntry.route){
            EntryJeTScreen(navigateBack = {
                navController.navigate(JenisTrapiHome.route) {
                    popUpTo(JenisTrapiHome.route) {
                        inclusive = true
                    }
                }
            })
        }

//      Detail Jenis Terapi
        composable(DestinasiDetailJenisTrapi.routesWithArg, arguments = listOf(navArgument(DestinasiDetailJenisTrapi.idJenisTrapi) {
            type = NavType.IntType }
        )
        ){
            val idJenisTrapi = it.arguments?.getInt(DestinasiDetailJenisTrapi.idJenisTrapi)
            idJenisTrapi?.let { idJenisTrapi ->
                DetailJenisTrapiScreen(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateJeT.route}/$idJenisTrapi") },
                    navigateBack = { navController.navigate(JenisTrapiHome.route) {
                        popUpTo(DestinasiHomeTerapis.route) { inclusive = true }
                    }
                    }
                )
            }
        }

//      Update Jenis Terapi
        composable(DestinasiUpdateJeT.routesWithArg, arguments = listOf(navArgument(DestinasiDetailJenisTrapi.idJenisTrapi){
            type = NavType.IntType })
        ){
            val idJenisTrapi = it.arguments?.getInt(DestinasiUpdateJeT.idJenisTrapi)
            if (idJenisTrapi == null) {
                navController.popBackStack()
            } else {
                UpdateJeTScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

//      Sesi Page
        composable(SesiHome.route){
            HomeSesiScreen(
                navigateToItemEntry = {navController.navigate(DestinasiInsertSesiEntry.route)},
                onDetailClick = {
                        idSesi ->
                    navController.navigate("${DestinasiDetailSesi.route}/$idSesi")
                }
            )
        }

//      Insert Sesi
        composable(DestinasiInsertSesiEntry.route){
            EntrySsScreen(navigateBack = {
                navController.navigate(SesiHome.route) {
                    popUpTo(SesiHome.route) {
                        inclusive = true
                    }
                }
            })
        }

//      Detail Sesi
        composable(DestinasiDetailSesi.routesWithArg, arguments = listOf(navArgument(DestinasiDetailSesi.idSesi) {
            type = NavType.IntType }
        )
        ){
            val idSesi = it.arguments?.getInt(DestinasiDetailSesi.idSesi)
            idSesi?.let { idSesi ->
                DetailSesiScreen(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateSs.route}/$idSesi") },
                    navigateBack = { navController.navigate(SesiHome.route) {
                        popUpTo(SesiHome.route) { inclusive = true }
                    }
                    }
                )
            }
        }

//      Update Sesi
        composable(DestinasiUpdateSs.routesWithArg, arguments = listOf(navArgument(DestinasiUpdateSs.idSesi){
            type = NavType.IntType })
        ){
            val idSesi = it.arguments?.getInt(DestinasiUpdateSs.idSesi)
            if (idSesi == null) {
                navController.popBackStack()
            } else {
                UpdateSsScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}