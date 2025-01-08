package com.example.firebase_pam.repository

import com.example.firebase_pam.model.Mahasiswa
import kotlinx.coroutines.flow.Flow


interface MahasiswaRepository{

    suspend fun getMahasiswa(): Flow<List<Mahasiswa>>

    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa)

    suspend fun deleteMahasiswa(nim: String)

    suspend fun getMahasiswaByNim(nim: String): Flow<Mahasiswa>

}
