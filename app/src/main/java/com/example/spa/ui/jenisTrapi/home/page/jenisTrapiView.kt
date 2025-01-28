package com.example.spa.ui.jenisTrapi.home.page

import CoustumeTopAppBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.R
import com.example.spa.model.JenisTrapi
import com.example.spa.ui.jenisTrapi.JenisTrapiPenyediaViewModel
import com.example.spa.ui.jenisTrapi.home.viewmodel.JenisTrapiHomeUiState
import com.example.spa.ui.jenisTrapi.home.viewmodel.JenisTrapiHomeViewModel
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.home.page.OnError
import com.example.spa.ui.pasien.home.page.OnLoading

object JenisTrapiHome : DestinasiNavigasi {
    override val route = "homeJT"
    override val titleRes = "Home Jenis Terapi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeJenisTrapiScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    viewModel: JenisTrapiHomeViewModel = viewModel(factory = JenisTrapiPenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = JenisTrapiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getJtr()
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
        HomeJenisTrapiStatus(
            homeUiState = viewModel.jtrUiState,
            retryAction = { viewModel.getJtr() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun HomeJenisTrapiStatus(
    homeUiState: JenisTrapiHomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
) {
    when (homeUiState) {
        is JenisTrapiHomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is JenisTrapiHomeUiState.Success ->
            if (homeUiState.jenisTrapi.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kontak")
                }
            } else {
                JenisTrapiLayout(
                    jenisTrapi = homeUiState.jenisTrapi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idJenisTrapi.toString())
                    }
                )
            }

        is JenisTrapiHomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun JenisTrapiLayout(
    jenisTrapi: List<JenisTrapi>,
    modifier: Modifier = Modifier,
    onDetailClick: (JenisTrapi) -> Unit,
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(jenisTrapi){kontak ->
            JenisTrapiCard(
                jenisTrapi = kontak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDetailClick(kontak)},
            )
        }
    }
}

@Composable
fun JenisTrapiCard(
    jenisTrapi: JenisTrapi,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = jenisTrapi.namaJenisTrapi,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
            }
            Text(
                text = jenisTrapi.deskripsiJenisTrapi,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
