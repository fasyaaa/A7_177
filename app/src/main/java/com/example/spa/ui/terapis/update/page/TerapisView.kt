package com.example.spa.ui.terapis.update.page

import CoustumeTopAppBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.insert.page.EntryBodyPasien
import com.example.spa.ui.pasien.update.page.DestinasiUpdatePas
import com.example.spa.ui.terapis.TerapisPenyediaViewModel
import com.example.spa.ui.terapis.insert.page.EntryBodyTerapis
import com.example.spa.ui.terapis.update.viewmodel.TerapisUpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateTps: DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Pas"
    const val idPasien = "id_pasien"
    val routesWithArg = "$route/{$idPasien}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTpsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: TerapisUpdateViewModel = viewModel(factory = TerapisPenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiUpdateTps.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBodyTerapis(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.terapisUpdateUiState,
            onTerapisValueChange = viewModel::updateInsertTerState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTer()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            }
        )
    }
}