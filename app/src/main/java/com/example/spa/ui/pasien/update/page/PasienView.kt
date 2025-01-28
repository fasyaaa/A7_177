package com.example.spa.ui.pasien.update.page

import CoustumeTopAppBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.R
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.PasienPenyediaViewModel
import com.example.spa.ui.pasien.insert.page.EntryBodyPasien
import com.example.spa.ui.pasien.update.viewmodel.PasienUpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdatePas: DestinasiNavigasi{
    override val route = "update_pasien"
    override val titleRes = "Update Pasien"
    const val idPasien = "id_pasien"
    val routesWithArg = "$route/{$idPasien}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasScreen(
    onPasBack: () -> Unit,
    modifier: Modifier = Modifier,
    onPasNavigate: () -> Unit,
    viewModel: PasienUpdateViewModel = viewModel(factory = PasienPenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiUpdatePas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onPasBack,
            )
        },
        containerColor = colorResource(id = R.color.Background)
    ){padding ->
        EntryBodyPasien(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.pasienUpdateUiState,
            onPasienValueChange = viewModel::updateInsertPasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePas()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onPasNavigate()
                    }
                }
            }
        )
    }
}