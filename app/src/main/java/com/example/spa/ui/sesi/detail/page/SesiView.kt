package com.example.spa.ui.sesi.detail.page

import CoustumeTopAppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.model.Sesi
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.home.page.OnError
import com.example.spa.ui.pasien.home.page.OnLoading
import com.example.spa.ui.sesi.SesiPenyediaViewModel
import com.example.spa.ui.sesi.detail.viewmodel.SesiDetailUiState
import com.example.spa.ui.sesi.detail.viewmodel.SesiDetailViewModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object DestinasiDetailSesi: DestinasiNavigasi {
    override val route = "detail_sesi"
    override val titleRes = "Detail Sesi"
    const val idSesi = "id_sesi"
    val routesWithArg = "$route/{$idSesi}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSesiScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SesiDetailViewModel = viewModel(factory = SesiPenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailSesi.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getSesibyIdSesi()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Kontak"
                )
            }
        }
    ) { innerPadding ->
        DetailStatusSs(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.sesiDetailUiState,
            retryAction = { viewModel.getSesibyIdSesi()},
            onDeleteAction = { idSesi ->
                viewModel.deleteSs(idSesi)
                navigateBack()
            }
        )
    }
}

@Composable
fun DetailStatusSs(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: SesiDetailUiState,
    onDeleteAction: (Int) -> Unit
) {
    when (detailUiState) {
        is SesiDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is SesiDetailUiState.Success -> {
            if (detailUiState.sesi.idSesi == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                Column(modifier = modifier.fillMaxSize()) {
                    ItemDetailSs(
                        sesi = detailUiState.sesi,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    FloatingActionButton(
                        onClick = { onDeleteAction(detailUiState.sesi.idSesi) },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus Pasien"
                        )
                    }
                }
            }
        }
        is SesiDetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailSs(
    modifier: Modifier = Modifier,
    sesi: Sesi
){
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailSs(judul = "Id Sesi", isinya = sesi.idSesi.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailSs(judul = "Id Pasien", isinya = sesi.idPasien.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailSs(judul = "Id Terapis", isinya = sesi.idTerapis.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailSs(judul = "Id Jenis Terapi", isinya = sesi.idJenisTrapi.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailSs(judul = "Catatan Sesi", isinya = sesi.catatanSesi)
            Spacer(modifier = Modifier.padding(5.dp))

            val dateTime = OffsetDateTime.parse(sesi.tanggalSesi) // Parse ISO 8601
            val formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

            ComponentDetailSs(judul = "Tanggal Sesi", isinya = formattedDate)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailSs(judul = "Waktu Sesi", isinya = formattedTime)
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun ComponentDetailSs(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = judul,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}