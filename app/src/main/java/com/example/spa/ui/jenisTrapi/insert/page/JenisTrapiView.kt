package com.example.spa.ui.jenisTrapi.insert.page

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
import com.example.spa.ui.jenisTrapi.JenisTrapiPenyediaViewModel
import com.example.spa.ui.jenisTrapi.insert.viewmodel.JenisTrapiInsertUiEvent
import com.example.spa.ui.jenisTrapi.insert.viewmodel.JenisTrapiInsertUiState
import com.example.spa.ui.jenisTrapi.insert.viewmodel.JenisTrapiInsertViewModel
import com.example.spa.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiInsertJeTEntry: DestinasiNavigasi {
    override val route = "entry_jenisTrapi"
    override val titleRes = "Entry Data Jenis Trapi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryJeTScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JenisTrapiInsertViewModel = viewModel(factory = JenisTrapiPenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiInsertJeTEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBodyJenisTrapi(
            insertUiState = viewModel.jenisTrapisUiState,
            onJenisTrapiValueChange = viewModel::updateInsertJtrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertJeT()
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
fun EntryBodyJenisTrapi(
    insertUiState: JenisTrapiInsertUiState,
    onJenisTrapiValueChange: (JenisTrapiInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputJenisTrapi(
            insertUiEvent = insertUiState.jenisTrapiInsertUiEvent,
            onValueChange = onJenisTrapiValueChange,
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
fun FormInputJenisTrapi(
    insertUiEvent: JenisTrapiInsertUiEvent,
    onValueChange: (JenisTrapiInsertUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.namaJenisTrapi,
            onValueChange = { onValueChange(insertUiEvent.copy(namaJenisTrapi = it)) },
            label = { Text("Nama Terapi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsiJenisTrapi,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiJenisTrapi = it)) },
            label = { Text("Deskripsi") },
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