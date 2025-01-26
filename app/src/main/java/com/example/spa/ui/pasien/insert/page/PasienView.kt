package com.example.spa.ui.pasien.insert.page

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
import com.example.spa.ui.pasien.PasienPenyediaViewModel
import com.example.spa.ui.pasien.insert.viewmodel.PasInsertUiEvent
import com.example.spa.ui.pasien.insert.viewmodel.PasInsertUiState
import com.example.spa.ui.pasien.insert.viewmodel.PasienInsertViewModel
import kotlinx.coroutines.launch

object DestinasiInsertEntry: DestinasiNavigasi{
    override val route = "item_entry"
    override val titleRes = "Entry Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPasScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PasienInsertViewModel = viewModel(factory = PasienPenyediaViewModel.Factory)
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
        EntryBodyPasien(
            insertUiState = viewModel.pasUiState,
            onPasienValueChange = viewModel::updateInsertPasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPas()
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
fun EntryBodyPasien(
    insertUiState: PasInsertUiState,
    onPasienValueChange: (PasInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputPasien(
            insertUiEvent = insertUiState.pasInsertUiEvent,
            onValueChange = onPasienValueChange,
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
fun FormInputPasien(
    insertUiEvent: PasInsertUiEvent,
    onValueChange: (PasInsertUiEvent) -> Unit,
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
            value = insertUiEvent.namaPasien,
            onValueChange = { onValueChange(insertUiEvent.copy(namaPasien = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.alamat,
            onValueChange = { onValueChange(insertUiEvent.copy(alamat = it)) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.noTelp,
            onValueChange = { onValueChange(insertUiEvent.copy(noTelp = it)) },
            label = { Text("Nomer Telephone") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tglLahir,
            onValueChange = { onValueChange(insertUiEvent.copy(tglLahir = it)) },
            label = { Text("Tanggal Lahir") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.riwayatMedis,
            onValueChange = { onValueChange(insertUiEvent.copy(riwayatMedis = it)) },
            label = { Text("Riwayat Medis") },
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