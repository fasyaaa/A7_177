package com.example.spa.ui.jenisTrapi.detail.page

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
import com.example.spa.model.JenisTrapi
import com.example.spa.ui.jenisTrapi.JenisTrapiPenyediaViewModel
import com.example.spa.ui.jenisTrapi.detail.viewmodel.JenisTrapiDetailUiState
import com.example.spa.ui.jenisTrapi.detail.viewmodel.JenisTrapiDetailViewModel
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.home.page.OnError
import com.example.spa.ui.pasien.home.page.OnLoading

object DestinasiDetailJenisTrapi: DestinasiNavigasi {
    override val route = "detail_jenisTrapi"
    override val titleRes = "Detail Jenis Terapi"
    const val idJenisTrapi = "id_jenisTrapi"
    val routesWithArg = "$route/{$idJenisTrapi}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailJenisTrapiScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JenisTrapiDetailViewModel = viewModel(factory = JenisTrapiPenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailJenisTrapi.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getJenisTrapibyIdJenisTrapi()
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
        DetailStatusJeT(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.jenisTrapiDetailState,
            retryAction = { viewModel.getJenisTrapibyIdJenisTrapi() },
            onDeleteAction = { idJenisTrapi ->
                viewModel.deleteJeT(idJenisTrapi)
                navigateBack()
            }
        )
    }
}

@Composable
fun DetailStatusJeT(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: JenisTrapiDetailUiState,
    onDeleteAction: (Int) -> Unit
) {
    when (detailUiState) {
        is JenisTrapiDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is JenisTrapiDetailUiState.Success -> {
            if (detailUiState.jenisTrapi.idJenisTrapi == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                Column(modifier = modifier.fillMaxSize()) {
                    ItemDetailJeT(
                        jenisTrapi = detailUiState.jenisTrapi,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    FloatingActionButton(
                        onClick = { onDeleteAction(detailUiState.jenisTrapi.idJenisTrapi) },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus Jenis Terapi"
                        )
                    }
                }
            }
        }
        is JenisTrapiDetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailJeT(
    modifier: Modifier = Modifier,
    jenisTrapi: JenisTrapi
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
            ComponentDetailJeT(judul = "Id Terapi", isinya = jenisTrapi.idJenisTrapi.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailJeT(judul = "Nama Terapi", isinya = jenisTrapi.namaJenisTrapi)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailJeT(judul = "Deskripsi", isinya = jenisTrapi.deskripsiJenisTrapi)
        }
    }
}

@Composable
fun ComponentDetailJeT(
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