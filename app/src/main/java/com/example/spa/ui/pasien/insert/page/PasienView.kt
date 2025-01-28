package com.example.spa.ui.pasien.insert.page

import CoustumeTopAppBar
import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.R
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.PasienPenyediaViewModel
import com.example.spa.ui.pasien.insert.viewmodel.FormErrorStatePas
import com.example.spa.ui.pasien.insert.viewmodel.PasInsertUiEvent
import com.example.spa.ui.pasien.insert.viewmodel.PasInsertUiState
import com.example.spa.ui.pasien.insert.viewmodel.PasienInsertViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

object DestinasiInsertPasienEntry: DestinasiNavigasi{
    override val route = "entry_pasien"
    override val titleRes = "Entry Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPasScreen(
    navigatePasBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PasienInsertViewModel = viewModel(factory = PasienPenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiInsertPasienEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigatePasBack
            )
        },
        containerColor = colorResource(id = R.color.Background)
    ){innerPadding ->
        EntryBodyPasien(
            insertUiState = viewModel.pasUiState,
            onPasienValueChange = viewModel::updateInsertPasState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFields()) {
                        viewModel.insertPas()
                        navigatePasBack()
                    }
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
        modifier = modifier.padding(12.dp),
    ){
        FormInputPasien(
            insertUiEvent = insertUiState.pasInsertUiEvent,
            onValueChange = onPasienValueChange,
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

@Composable
fun FormInputPasien(
    insertUiEvent: PasInsertUiEvent,
    onValueChange: (PasInsertUiEvent) -> Unit,
    errorState: FormErrorStatePas = FormErrorStatePas(),
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format(
                "%04d-%02d-%02d",
                selectedYear,
                selectedMonth + 1,
                selectedDay
            )
            onValueChange(insertUiEvent.copy(tglLahir = formattedDate))
        },
        year,
        month,
        day
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.idPasien?.toString() ?: "",
            onValueChange = {
                val newValue = it.toIntOrNull()
                onValueChange(insertUiEvent.copy(idPasien = newValue))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Id Pasien", color = colorResource(id = R.color.black)) },
            placeholder = { Text("Masukkan Id Pasien") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState.hasError(errorState.idPasien)) Color.Red else colorResource(id = R.color.black),
                unfocusedBorderColor = if (errorState.hasError(errorState.idPasien)) Color.Red else colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            isError = errorState.hasError(errorState.idPasien),
            singleLine = true,
            shape = RoundedCornerShape(50.dp)
        )

        Text(
            text = errorState.idPasien ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertUiEvent.namaPasien,
            onValueChange = { onValueChange(insertUiEvent.copy(namaPasien = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nama", modifier = Modifier, color = colorResource(id = R.color.black)) },
            placeholder = {Text("Masukkan Nama Pasien")},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState.hasError(errorState.namaPasien)) Color.Red else colorResource(id = R.color.black),
                unfocusedBorderColor = if (errorState.hasError(errorState.namaPasien)) Color.Red else colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = errorState.hasError(errorState.namaPasien),
            singleLine = true,
            shape = RoundedCornerShape((50.dp))
        )
        Text(
            text = errorState.namaPasien ?: "",
            color = Color.Red
        )


        OutlinedTextField(
            value = insertUiEvent.alamat,
            onValueChange = { onValueChange(insertUiEvent.copy(alamat = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Alamat", modifier = Modifier, color = colorResource(id = R.color.black)) },
            placeholder = {Text("Masukkan Alamat Pasien")},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.black),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            shape = RoundedCornerShape((50.dp))
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertUiEvent.noTelp,
            onValueChange = { onValueChange(insertUiEvent.copy(noTelp = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nomer Telephone",modifier = Modifier, color = colorResource(id = R.color.black)) },
            placeholder = {Text("Masukkan Nomer Telephone")},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState.hasError(errorState.noTelp)) Color.Red else colorResource(id = R.color.black),
                unfocusedBorderColor = if (errorState.hasError(errorState.noTelp)) Color.Red else colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = errorState.hasError(errorState.noTelp),
            singleLine = true,
            shape = RoundedCornerShape((50.dp))
        )
        Text(
            text = errorState.noTelp ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertUiEvent.tglLahir,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                datePickerDialog.show()},
            label = { Text("Tanggal Lahir") },
            placeholder = { Text("Masukkan Tanggal Lahir (YYYY-MM-DD)") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState.hasError(errorState.tglLahir)) Color.Red else colorResource(id = R.color.black),
                unfocusedBorderColor = if (errorState.hasError(errorState.tglLahir)) Color.Red else colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(50.dp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Masukkan Tanggal Lahir",
                    modifier = Modifier.clickable {
                        datePickerDialog.show()
                    }
                )
            }
        )
        Text(
            text = errorState.tglLahir ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertUiEvent.riwayatMedis,
            onValueChange = { onValueChange(insertUiEvent.copy(riwayatMedis = it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Riwayat Medis", modifier = Modifier, color = colorResource(id = R.color.black)) },
            placeholder = {Text("Masukkan Riwayat")},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState.hasError(errorState.riwayatMedis)) Color.Red else colorResource(id = R.color.black),
                unfocusedBorderColor = if (errorState.hasError(errorState.riwayatMedis)) Color.Red else colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = errorState.hasError(errorState.riwayatMedis),
            singleLine = true,
            shape = RoundedCornerShape((50.dp))
        )
        Text(
            text = errorState.riwayatMedis ?: "",
            color = Color.Red
        )
    }
}