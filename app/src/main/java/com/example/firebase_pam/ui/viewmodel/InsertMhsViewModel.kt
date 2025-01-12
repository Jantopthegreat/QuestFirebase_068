package com.example.firebase_pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase_pam.model.Mahasiswa
import com.example.firebase_pam.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class InsertMhsViewModel (
    private val repomhs: MahasiswaRepository
) : ViewModel() {

    var uiEvent: InsertMhsUiState by mutableStateOf(InsertMhsUiState())
        private set
    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        uiEvent = uiEvent.copy( insertUiEvent = mahasiswaEvent,
        )
    }

    fun validateFields(): Boolean {
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jeniskelamin = if (event.jeniskelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong"
        )
        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertMhs() {
        if (validateFields()) { viewModelScope.launch {
            uiState = FormState.Loading
            try {
                val mahasiswa = uiEvent.insertUiEvent.toMhsModel()
                repomhs.insertMahasiswa(uiEvent.insertUiEvent.toMhsModel())
                uiState = FormState.Success("Data berhasil disimpan")
            } catch (e: Exception) {
                uiState = FormState.Error("Data gagal disimpan")
            }
        }
        } else {
            uiState = FormState.Error("Data tidak valid")
        }
    }
    fun resetForm() {
        uiEvent = InsertMhsUiState()
        uiState = FormState.Idle
    }

    fun resetSnackBarMessage() {
        uiState = FormState.Idle
    }
}

    fun MahasiswaEvent.toMhsModel(): Mahasiswa = Mahasiswa(
        nim = nim,
        nama = nama,
        jeniskelamin = jeniskelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan
    )


    sealed class FormState {
        object Idle : FormState()
        object Loading : FormState()
        data class Success(val message: String) : FormState()
        data class Error(val message: String) : FormState()
    }

    data class FormErrorState(
        val nim: String? = null,
        val nama: String? = null,
        val jeniskelamin: String? = null,
        val alamat: String? = null,
        val kelas: String? = null,
        val angkatan: String? = null
    ) {

        fun isValid(): Boolean {
            return nim == null && nama == null && jeniskelamin == null &&
                    alamat == null && kelas == null && angkatan == null
        }
    }





data class InsertMhsUiState(
    val insertUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
)

    data class MahasiswaEvent(
        val nim: String = "",
        val nama: String = "",
        val jeniskelamin: String = "",
        val alamat: String = "",
        val kelas: String = "",
        val angkatan: String = ""
    )
