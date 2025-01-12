package com.example.firebase_pam.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.firebase_pam.repository.MahasiswaRepository

class InsertMhsViewModel(
    private val mhsRepository: MahasiswaRepository

) : ViewModel() {








    data class FormErrorState(
        val nim: String? = null,
        val nama: String? = null,
        val gender: String? = null,
        val alamat: String? = null,
        val kelas: String? = null,
        val angkatan: String? = null
    ) {
        fun isValid(): Boolean {
            return nim == null && nama == null && gender == null &&
                    alamat == null && kelas == null && angkatan == null
        }
    }

    data class MahasiswaEvent(
        val nim: String = "",
        val nama: String = "",
        val gender: String = "",
        val alamat: String = "",
        val kelas: String = "",
        val angkatan: String = ""
    )



}