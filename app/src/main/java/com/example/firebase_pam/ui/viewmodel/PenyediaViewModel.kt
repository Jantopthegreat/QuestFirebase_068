package com.example.firebase_pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.firebase_pam.MahasiswaApp


object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeMhsViewModel(mhsApp().container.mahasiswaRepository) }

    }
}

fun CreationExtras.mhsApp(): MahasiswaApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApp)