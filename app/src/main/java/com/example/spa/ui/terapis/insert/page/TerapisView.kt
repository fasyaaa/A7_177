package com.example.spa.ui.terapis.insert.page

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
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.insert.viewmodel.FormErrorStatePas
import com.example.spa.ui.terapis.TerapisPenyediaViewModel
import com.example.spa.ui.terapis.insert.viewmodel.FormErrorStateTps
import com.example.spa.ui.terapis.insert.viewmodel.TerapisInsertUiEvent
import com.example.spa.ui.terapis.insert.viewmodel.TerapisInsertUiState
import com.example.spa.ui.terapis.insert.viewmodel.TerapisInsertViewModel
import kotlinx.coroutines.launch

object DestinasiInsertTpsEntry: DestinasiNavigasi {
    override val route = "entry_terapis"
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
                title = DestinasiInsertTpsEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        containerColor = colorResource(id = R.color.Background)
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
                containerColor = colorResource(id = R.color.ButtonBorder)
            )
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
    errorState: FormErrorStateTps = FormErrorStateTps(),
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.idTerapis?.toString() ?: "",
            onValueChange = {
                val newValue = it.toIntOrNull()
                if (newValue != null) {
                    onValueChange(insertUiEvent.copy(idTerapis = newValue))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Id Terapis", color = colorResource(id = R.color.black)) },
            placeholder = { Text("Masukkan Id Terapis") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState.hasError(errorState.idTerapis)) Color.Red else colorResource(id = R.color.black),
                unfocusedBorderColor = if (errorState.hasError(errorState.idTerapis)) Color.Red else colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            isError = errorState.hasError(errorState.idTerapis),
            singleLine = true,
            shape = RoundedCornerShape(50.dp)
        )
        Text(
            text = errorState.idTerapis ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertUiEvent.namaTerapis,
            onValueChange = { onValueChange(insertUiEvent.copy(namaTerapis = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nama", modifier = Modifier, color = colorResource(id = R.color.black)) },
            placeholder = {Text("Masukkan Nama Terapis")},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState.hasError(errorState.namaTerapis)) Color.Red else colorResource(id = R.color.black),
                unfocusedBorderColor = if (errorState.hasError(errorState.namaTerapis)) Color.Red else colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = errorState.hasError(errorState.namaTerapis),
            singleLine = true,
            shape = RoundedCornerShape((50.dp))
        )
        Text(
            text = errorState.namaTerapis ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertUiEvent.spesialis,
            onValueChange = { onValueChange(insertUiEvent.copy(spesialis = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Spesialis", modifier = Modifier, color = colorResource(id = R.color.black)) },
            placeholder = {Text("Masukkan Spesialis")},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState.hasError(errorState.spesialis)) Color.Red else colorResource(id = R.color.black),
                unfocusedBorderColor = if (errorState.hasError(errorState.spesialis)) Color.Red else colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = errorState.hasError(errorState.spesialis),
            singleLine = true,
            shape = RoundedCornerShape((50.dp))
        )
        Text(
            text = errorState.spesialis ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertUiEvent.noIzinPrak,
            onValueChange = { onValueChange(insertUiEvent.copy(noIzinPrak = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nomer Izin Praktik", modifier = Modifier, color = colorResource(id = R.color.black)) },
            placeholder = {Text("Masukkan No Izin Praktik")},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState.hasError(errorState.noIzinPrak)) Color.Red else colorResource(id = R.color.black),
                unfocusedBorderColor = if (errorState.hasError(errorState.noIzinPrak)) Color.Red else colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = errorState.hasError(errorState.noIzinPrak),
            singleLine = true,
            shape = RoundedCornerShape((50.dp))
        )
        Text(
            text = errorState.noIzinPrak ?: "",
            color = Color.Red
        )
    }
}