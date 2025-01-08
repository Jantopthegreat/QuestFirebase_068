package com.example.firebase_pam.ui.viewmodel

import android.net.http.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase_pam.model.Mahasiswa
import com.example.firebase_pam.repository.MahasiswaRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeMhsUiState{
    data class Success( val mahasiswa: List<Mahasiswa>) : HomeMhsUiState()
    data class Error (val message: Throwable) : HomeMhsUiState()
    object  Loading: HomeMhsUiState()
}


class HomeMhsViewModel(private val mhs: MahasiswaRepository) : ViewModel() {
    var mhsUiState: HomeMhsUiState by mutableStateOf(HomeMhsUiState.Loading)
        private set


    init {
        getMahasiswa()
    }

    fun getMahasiswa() {
        viewModelScope.launch {
        mhs.getMahasiswa()
            .onStart { mhsUiState = HomeMhsUiState.Loading
            }
            .catch {
                mhsUiState = HomeMhsUiState.Error(it)
            }
            .collect {
                mhsUiState = if (it.isEmpty()) {
                    HomeMhsUiState.Error(Exception("Data Kosong"))
                }
                else{
                    HomeMhsUiState.Success(it)
                }
            }
        }

        }
    }
