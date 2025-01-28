package com.example.spa.ui.jenisTrapi.update.page

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
import com.example.spa.ui.jenisTrapi.JenisTrapiPenyediaViewModel
import com.example.spa.ui.jenisTrapi.insert.page.EntryBodyJenisTrapi
import com.example.spa.ui.jenisTrapi.update.viewmodel.JenisTrapiUpdateViewModel
import com.example.spa.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateJeT: DestinasiNavigasi {
    override val route = "update_jenisTrapi"
    override val titleRes = "Update Jenis Terapi"
    const val idJenisTrapi = "id_jenisTrapi"
    val routesWithArg = "$route/{$idJenisTrapi}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateJeTScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: JenisTrapiUpdateViewModel = viewModel(factory = JenisTrapiPenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiUpdateJeT.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        },
        containerColor = colorResource(id = R.color.Background)
    ){padding ->
        EntryBodyJenisTrapi(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.jenisTrapiUpdateUiState,
            onJenisTrapiValueChange = viewModel::updateInsertJtrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateJtr()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            }
        )
    }
}