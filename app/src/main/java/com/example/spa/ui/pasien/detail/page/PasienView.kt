package com.example.spa.ui.pasien.detail.page

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
import com.example.spa.model.Pasien
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.PasienPenyediaViewModel
import com.example.spa.ui.pasien.detail.viewmodel.PasienDetailUiState
import com.example.spa.ui.pasien.detail.viewmodel.PasienDetailViewModel
import com.example.spa.ui.pasien.home.page.OnError
import com.example.spa.ui.pasien.home.page.OnLoading

object DestinasiDetailPasien: DestinasiNavigasi{
    override val route = "detail_pasien"
    override val titleRes = "Detail Pasien"
    const val idPasien = "id_pasien"
    val routesWithArg = "$route/{$idPasien}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPasienScreen(
    navigatePasBack: () -> Unit,
    navigateToItemPasUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PasienDetailViewModel = viewModel(factory = PasienPenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailPasien.titleRes,
                canNavigateBack = true,
                navigateUp = navigatePasBack,
                onRefresh = {
                    viewModel.getPasienbyIdPasien()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemPasUpdate,
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
        DetailStatusPas(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.pasienDetailState,
            retryAction = { viewModel.getPasienbyIdPasien() },
            onDeleteAction = { idPasien ->
                viewModel.deletePas(idPasien)
                navigatePasBack()
            }
        )
    }
}

@Composable
fun DetailStatusPas(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: PasienDetailUiState,
    onDeleteAction: (Int) -> Unit
) {
    when (detailUiState) {
        is PasienDetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PasienDetailUiState.Success -> {
            if (detailUiState.pasien.idPasien == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                Column(modifier = modifier.fillMaxSize()) {
                    ItemDetailPas(
                        pasien = detailUiState.pasien,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    FloatingActionButton(
                        onClick = { onDeleteAction(detailUiState.pasien.idPasien) },
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
        is PasienDetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPas(
    modifier: Modifier = Modifier,
    pasien: Pasien
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
            ComponentDetailPas(judul = "Id Pasien", isinya = pasien.idPasien.toString())
            Spacer(modifier =Modifier.padding(5.dp))
            ComponentDetailPas(judul = "Nama Pasien", isinya = pasien.namaPasien)
            Spacer(modifier =Modifier.padding(5.dp))
            ComponentDetailPas(judul = "Tanggal Lahir", isinya = pasien.tglLahir)
            Spacer(modifier =Modifier.padding(5.dp))
            ComponentDetailPas(judul = "Alamat", isinya = pasien.alamat)
            Spacer(modifier =Modifier.padding(5.dp))
            ComponentDetailPas(judul = "nomer telephone", isinya = pasien.noTelp)
            Spacer(modifier =Modifier.padding(5.dp))
            ComponentDetailPas(judul = "Riwayat Medis", isinya = pasien.riwayatMedis)
        }
    }
}

@Composable
fun ComponentDetailPas(
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