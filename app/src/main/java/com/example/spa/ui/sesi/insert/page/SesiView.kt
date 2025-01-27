package com.example.spa.ui.sesi.insert.page

import CoustumeTopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.sesi.SesiPenyediaViewModel
import com.example.spa.ui.sesi.insert.viewmodel.SesiInsertUiEvent
import com.example.spa.ui.sesi.insert.viewmodel.SesiInsertUiState
import com.example.spa.ui.sesi.insert.viewmodel.SesiInsertViewModel
import kotlinx.coroutines.launch

object DestinasiInsertSesiEntry: DestinasiNavigasi{
    override val route = "item_entry"
    override val titleRes = "Entry Sesi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SesiInsertViewModel = viewModel(factory = SesiPenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiInsertSesiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBodySesi(
            insertUiState = viewModel.sesiUiState,
            onSesiValueChange = viewModel::updateInsertSsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertSs()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodySesi(
    insertUiState: SesiInsertUiState,
    onSesiValueChange: (SesiInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputSesi(
            insertUiEvent = insertUiState.sesiInsertUiEvent,
            onValueChange = onSesiValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputSesi(
    insertUiEvent: SesiInsertUiEvent,
    onValueChange: (SesiInsertUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.idPasien.toString(),
            onValueChange = {
                val newValue = it.toIntOrNull()
                if (newValue != null) {
                    onValueChange(insertUiEvent.copy(idPasien = newValue))
                }
            },
            label = { Text("Id Pasien") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.idTerapis.toString(),
            onValueChange = {
                val newValue = it.toIntOrNull()
                if (newValue != null) {
                    onValueChange(insertUiEvent.copy(idTerapis = newValue))
                }
            },
            label = { Text("Id Terapis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.idJenisTrapi.toString(),
            onValueChange = {
                val newValue = it.toIntOrNull()
                if (newValue != null) {
                    onValueChange(insertUiEvent.copy(idJenisTrapi = newValue))
                }
            },
            label = { Text("Id Jenis Terapis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.catatanSesi,
            onValueChange = { onValueChange(insertUiEvent.copy(catatanSesi = it)) },
            label = { Text("Catatan Sesi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tanggalSesi,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalSesi = it)) },
            label = { Text("Tanggal") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if(enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}