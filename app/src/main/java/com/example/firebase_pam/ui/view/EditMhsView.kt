package com.example.firebase_pam.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebase_pam.model.Mahasiswa
import com.example.firebase_pam.ui.customwidget.TopAppBar
import com.example.firebase_pam.ui.viewmodel.EditMhsViewModel
import com.example.firebase_pam.ui.viewmodel.FormErrorState
import com.example.firebase_pam.ui.viewmodel.MahasiswaEvent
import com.example.firebase_pam.ui.viewmodel.PenyediaViewModel
import com.example.firebase_pam.ui.viewmodel.UpdateUiState

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EditMhsView(
    mahasiswa: Mahasiswa,
    viewModel: EditMhsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onUpdateSuccess: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val updateUiState by viewModel.updateUiState.collectAsState()
    val mahasiswaEvent = remember {
        mutableStateOf(
            MahasiswaEvent(
                nama = mahasiswa.nama,
                nim = mahasiswa.nim,
                jeniskelamin = mahasiswa.jeniskelamin,
                alamat = mahasiswa.alamat,
                kelas = mahasiswa.kelas,
                angkatan = mahasiswa.angkatan
            )
        )
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(updateUiState) {
        when (updateUiState) {
            is UpdateUiState.Success -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Data berhasil diperbarui")
                }
                delay(700)
                onUpdateSuccess()
            }
            is UpdateUiState.Error -> {
                val errorMessage = (updateUiState as UpdateUiState.Error).message
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
            }
            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            FormMahasiswa(
                mahasiswaEvent = mahasiswaEvent.value,
                onValueChange = { updatedEvent ->
                    mahasiswaEvent.value = updatedEvent  // Mengupdate state lokal mahasiswaEvent
                },
                errorState = FormErrorState(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.updateMahasiswa(
                        mahasiswa.copy(
                            nama = mahasiswaEvent.value.nama,
                            alamat = mahasiswaEvent.value.alamat,
                            jeniskelamin = mahasiswaEvent.value.jeniskelamin,
                            kelas = mahasiswaEvent.value.kelas,
                            angkatan = mahasiswaEvent.value.angkatan
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (updateUiState is UpdateUiState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Updating...")
                } else {
                    Text("Update")
                }
            }
        }
    }
}