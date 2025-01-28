package com.example.spa.ui.sesi.home.page

import CoustumeTopAppBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.R
import com.example.spa.model.Sesi
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.home.page.OnError
import com.example.spa.ui.pasien.home.page.OnLoading
import com.example.spa.ui.sesi.SesiPenyediaViewModel
import com.example.spa.ui.sesi.detail.page.ComponentDetailSs
import com.example.spa.ui.sesi.home.viewmodel.SesiHomeUiState
import com.example.spa.ui.sesi.home.viewmodel.SesiHomeViewModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object SesiHome : DestinasiNavigasi {
    override val route = "home_sesi"
    override val titleRes = "Home Sesi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSesiScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    viewModel: SesiHomeViewModel = viewModel(factory = SesiPenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = SesiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getSs()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kontak")
            }
        },
        containerColor = colorResource(id = R.color.Background)
    ){ innerPadding ->
        HomeSesiStatus(
            homeUiState = viewModel.ssUiState,
            retryAction = { viewModel.getSs() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun HomeSesiStatus(
    homeUiState: SesiHomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
) {
    when(homeUiState) {
        is SesiHomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is SesiHomeUiState.Success ->
            if (homeUiState.sesi.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kontak")
                }
            } else {
                SesiLayout(
                    sesi = homeUiState.sesi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idSesi.toString())
                    }
                )
            }

        is SesiHomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun SesiLayout(
    sesi: List<Sesi>,
    modifier: Modifier = Modifier,
    onDetailClick: (Sesi) -> Unit,
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sesi){kontak ->
            SesiCard(
                sesi = kontak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDetailClick(kontak)},
            )
        }
    }
}

@Composable
fun SesiCard(
    sesi: Sesi,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = sesi.namaPasien,
                style = MaterialTheme.typography.titleLarge
            )

            val dateTime = OffsetDateTime.parse(sesi.tanggalSesi) // Parse ISO 8601
            val formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy - MM - dd"))
            val formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

            ComponentDetailSs(
                judul = "Tanggal Sesi",
                isinya = formattedDate,
                textStyle = MaterialTheme.typography.titleLarge
            )
            ComponentDetailSs(
                judul = "Waktu Sesi",
                isinya = formattedTime,
                textStyle = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun ComponentDetailSs(
    judul: String,
    isinya: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Column(modifier = modifier) {
        Text(text = judul, style = MaterialTheme.typography.labelMedium)
        Text(text = isinya, style = textStyle)
    }
}