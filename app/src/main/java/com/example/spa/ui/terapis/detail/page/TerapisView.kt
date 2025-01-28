package com.example.spa.ui.terapis.detail.page

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.R
import com.example.spa.model.Terapis
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.home.page.OnError
import com.example.spa.ui.pasien.home.page.OnLoading
import com.example.spa.ui.terapis.TerapisPenyediaViewModel
import com.example.spa.ui.terapis.detail.viewmodel.TerapisDetailUiState
import com.example.spa.ui.terapis.detail.viewmodel.TerapisDetailViewModel

object DestinasiDetailTerapis: DestinasiNavigasi {
    override val route = "detail_terapis"
    override val titleRes = "Detail Terapis"
    const val idTerapis = "id_terapis"
    val routesWithArg = "$route/{$idTerapis}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTerapisScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TerapisDetailViewModel = viewModel(factory = TerapisPenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailTerapis.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTerapisbyIdTerapis()
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
        },
        containerColor = colorResource(id = R.color.Background)
    ) { innerPadding ->
        DetailStatusTps(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.terapisDetailUiState,
            retryAction = { viewModel.getTerapisbyIdTerapis() },
            onDeleteAction = { idTerapis ->
                viewModel.deleteTer(idTerapis)
                navigateBack()
            }
        )
    }
}

@Composable
fun DetailStatusTps(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: TerapisDetailUiState,
    onDeleteAction: (Int) -> Unit
) {
    when (detailUiState) {
        is TerapisDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is TerapisDetailUiState.Success -> {
            if (detailUiState.terapis.idTerapis == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                Column(modifier = modifier.fillMaxSize()) {
                    ItemDetailTps(
                        terapis = detailUiState.terapis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    FloatingActionButton(
                        onClick = { onDeleteAction(detailUiState.terapis.idTerapis) },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus Terapis"
                        )
                    }
                }
            }
        }
        is TerapisDetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailTps(
    modifier: Modifier = Modifier,
    terapis: Terapis
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
            ComponentDetailTps(judul = "Id Terapis", isinya = terapis.idTerapis.toString())
            Spacer(modifier =Modifier.padding(5.dp))
            ComponentDetailTps(judul = "Nama Terapis", isinya = terapis.namaTerapis)
            Spacer(modifier =Modifier.padding(5.dp))
            ComponentDetailTps(judul = "Spesialis", isinya = terapis.spesialis)
            Spacer(modifier =Modifier.padding(5.dp))
            ComponentDetailTps(judul = "Nomer Izin Praktik", isinya = terapis.noIzinPrak)
        }
    }
}

@Composable
fun ComponentDetailTps(
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