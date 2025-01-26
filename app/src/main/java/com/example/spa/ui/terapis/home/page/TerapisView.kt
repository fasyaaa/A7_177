package com.example.spa.ui.terapis.home.page

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.model.Terapis
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.pasien.home.page.OnError
import com.example.spa.ui.pasien.home.page.OnLoading
import com.example.spa.ui.terapis.TerapisPenyediaViewModel
import com.example.spa.ui.terapis.home.viewmodel.TerapisHomeUiState
import com.example.spa.ui.terapis.home.viewmodel.TerapisHomeViewModel

object DestinasiHomeTerapis : DestinasiNavigasi{
    override val route =  "home"
    override val titleRes = "Home Tps"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerapisHomeScreen(
    navigateToltemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: TerapisHomeViewModel = viewModel(factory = TerapisPenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiHomeTerapis.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTer()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToltemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kontak")
            }
        },
    ){ innerPadding ->
        HomeStatus(
            homeUiState = viewModel.terUiState,
            retryAction = { viewModel.getTer() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: TerapisHomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
){
    when (homeUiState) {
        is TerapisHomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is TerapisHomeUiState.Success ->
            if (homeUiState.terapis.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kontak")
                }
            } else {
                TpsLayout(
                    terapis = homeUiState.terapis,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idTerapis.toString())
                    }
                )
            }
        is TerapisHomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun TpsLayout(
    terapis: List<Terapis>,
    modifier: Modifier = Modifier,
    onDetailClick: (Terapis) -> Unit,
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(terapis){kontak ->
            TpsCard(
                terapis = kontak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDetailClick(kontak)},
            )
        }
    }
}

@Composable
fun TpsCard(
    terapis: Terapis,
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
                    text = terapis.namaTerapis,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = terapis.idTerapis.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = terapis.spesialis,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = terapis.noIzinPrak,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}