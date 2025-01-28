package com.example.spa.ui.jenisTrapi.insert.page

import CoustumeTopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.R
import com.example.spa.ui.jenisTrapi.JenisTrapiPenyediaViewModel
import com.example.spa.ui.jenisTrapi.insert.viewmodel.FormErrorStateJeT
import com.example.spa.ui.jenisTrapi.insert.viewmodel.JenisTrapiInsertUiEvent
import com.example.spa.ui.jenisTrapi.insert.viewmodel.JenisTrapiInsertUiState
import com.example.spa.ui.jenisTrapi.insert.viewmodel.JenisTrapiInsertViewModel
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.insert.viewmodel.FormErrorStatePas
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
        },
        containerColor = colorResource(id = R.color.Background)
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
            errorState = insertUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .size(height = 48.dp, width = 120.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.ButtonBorder))
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
    errorState: FormErrorStateJeT = FormErrorStateJeT(),
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.namaJenisTrapi,
            onValueChange = { onValueChange(insertUiEvent.copy(namaJenisTrapi = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nama Terapi", modifier = Modifier, color = colorResource(id = R.color.black)) },
            placeholder = {Text("Masukkan Nama Terapi")},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState.hasError(errorState.namaJenisTrapi)) Color.Red else colorResource(id = R.color.black),
                unfocusedBorderColor = if (errorState.hasError(errorState.namaJenisTrapi)) Color.Red else colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = errorState.hasError(errorState.namaJenisTrapi),
            singleLine = true,
            shape = RoundedCornerShape((50.dp))
        )
        Text(
            text = errorState.namaJenisTrapi ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsiJenisTrapi,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiJenisTrapi = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Deskripsi", modifier = Modifier, color = colorResource(id = R.color.black)) },
            placeholder = {Text("Masukkan Deskripsi")},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState.hasError(errorState.deskripsiJenisTrapi)) Color.Red else colorResource(id = R.color.black),
                unfocusedBorderColor = if (errorState.hasError(errorState.deskripsiJenisTrapi)) Color.Red else colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = errorState.hasError(errorState.deskripsiJenisTrapi),
            singleLine = true,
            shape = RoundedCornerShape((50.dp))
        )
        Text(
            text = errorState.deskripsiJenisTrapi ?: "",
            color = Color.Red
        )
    }
}