package com.example.firebase_pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase_pam.model.Mahasiswa
import com.example.firebase_pam.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch



sealed class DetailMhsUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailMhsUiState()
    object Error : DetailMhsUiState()
    object Loading : DetailMhsUiState()
}

class DetailMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
) : ViewModel() {

    private val nim: String = checkNotNull(savedStateHandle["nim"])

    private val _detailMhsUiState = MutableStateFlow<DetailMhsUiState>(DetailMhsUiState.Loading)
    val detailMhsUiState: StateFlow<DetailMhsUiState> = _detailMhsUiState

    init {
        getMhsbyNim()
    }

    fun getMhsbyNim() {
        viewModelScope.launch {
            _detailMhsUiState.value = DetailMhsUiState.Loading

            mahasiswaRepository.getMahasiswaByNim(nim)
                .onStart {

                }
                .catch {

                    _detailMhsUiState.value = DetailMhsUiState.Error
                }
                .collect { mahasiswa ->
                    _detailMhsUiState.value = DetailMhsUiState.Success(mahasiswa) // Jika berhasil, update UI
                }
        }
    }
}