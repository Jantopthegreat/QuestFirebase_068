package com.example.firebase_pam.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.firebase_pam.repository.MahasiswaRepository

class InsertMhsViewModel(
    private val mhsRepository: MahasiswaRepository

) : ViewModel() {










    data class MahasiswaEvent(
        val nim: String = "",
        val nama: String = "",
        val gender: String = "",
        val alamat: String = "",
        val kelas: String = "",
        val angkatan: String = ""
    )



}