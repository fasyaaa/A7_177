package com.example.spa.ui.sesi.update.page

import CoustumeTopAppBar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.spa.ui.sesi.SesiPenyediaViewModel
import com.example.spa.ui.sesi.insert.page.EntryBodySesi
import com.example.spa.ui.sesi.insert.viewmodel.SesiInsertViewModel
import com.example.spa.ui.sesi.update.viewmodel.SesiUpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateSs : DestinasiNavigasi {
    override val route = "update_sesi"
    override val titleRes = "Update Sesi"
    const val idSesi = "id_sesi"
    val routesWithArg = "$route/{$idSesi}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateSsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: SesiUpdateViewModel = viewModel(factory = SesiPenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiUpdateSs.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        },
        containerColor = colorResource(id = R.color.Background)
    ) { padding ->
        EntryBodySesi(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            insertUiState = viewModel.sesiUpdateUiState,
            onSesiValueChange = viewModel::updateInsertSsState,
            viewModel = viewModel(factory = SesiPenyediaViewModel.Factory),
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateSs()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}
