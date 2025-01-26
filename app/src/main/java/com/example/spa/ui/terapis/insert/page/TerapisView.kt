package com.example.spa.ui.terapis.insert.page

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
import com.example.spa.ui.terapis.TerapisPenyediaViewModel
import com.example.spa.ui.terapis.insert.viewmodel.TerapisInsertUiEvent
import com.example.spa.ui.terapis.insert.viewmodel.TerapisInsertUiState
import com.example.spa.ui.terapis.insert.viewmodel.TerapisInsertViewModel
import kotlinx.coroutines.launch

object DestinasiInsertEntry: DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTpsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TerapisInsertViewModel = viewModel(factory = TerapisPenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiInsertEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBodyTerapis(
            insertUiState = viewModel.terapisInsertUiState,
            onTerapisValueChange = viewModel::terapisUpdateInsertState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTer()
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
fun EntryBodyTerapis(
    insertUiState: TerapisInsertUiState,
    onTerapisValueChange: (TerapisInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputTerapis(
            insertUiEvent = insertUiState.terapisInsertUiEvent,
            onValueChange = onTerapisValueChange,
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
fun FormInputTerapis(
    insertUiEvent: TerapisInsertUiEvent,
    onValueChange: (TerapisInsertUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
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
            value = insertUiEvent.namaTerapis,
            onValueChange = { onValueChange(insertUiEvent.copy(namaTerapis = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.spesialis,
            onValueChange = { onValueChange(insertUiEvent.copy(spesialis = it)) },
            label = { Text("Spesialis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.noIzinPrak,
            onValueChange = { onValueChange(insertUiEvent.copy(noIzinPrak = it)) },
            label = { Text("Nomer Izin Praktik") },
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