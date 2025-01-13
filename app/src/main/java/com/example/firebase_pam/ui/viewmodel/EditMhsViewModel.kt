package com.example.firebase_pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase_pam.model.Mahasiswa
import com.example.firebase_pam.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

import kotlinx.coroutines.launch
class EditMhsViewModel(
    private val repository: MahasiswaRepository
) : ViewModel() {

    private val _mahasiswaState = MutableStateFlow<Mahasiswa?>(null)
    val mahasiswaState: StateFlow<Mahasiswa?> = _mahasiswaState

    private val _updateUiState = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
    val updateUiState: StateFlow<UpdateUiState> = _updateUiState



    fun getMahasiswaByNim(nim: String) {
        viewModelScope.launch {
            repository.getMahasiswaByNim(nim)
                .catch { e ->
                    _mahasiswaState.value = null
                }
                .collectLatest { mahasiswa ->
                    _mahasiswaState.value = mahasiswa
                }
        }
    }

    fun updateMahasiswa(mahasiswa: Mahasiswa) {
        viewModelScope.launch {
            _updateUiState.value = UpdateUiState.Loading
            try {
                repository.updateMahasiswa(mahasiswa)
                _updateUiState.value = UpdateUiState.Success
            } catch (e: Exception) {
                _updateUiState.value = UpdateUiState.Error(e.message ?: "Gagal mengupdate data")
            }
        }
    }
}

sealed class UpdateUiState {
    object Idle : UpdateUiState()
    object Loading : UpdateUiState()
    object Success : UpdateUiState()
    data class Error(val message: String) : UpdateUiState()
}

