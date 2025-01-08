package com.example.firebase_pam.model


data class Mahasiswa (
    val nama: String,
    val nim: String,
    val alamat: String,
    val kelas: String,
    val angkatan: String,
    val jeniskelamin: String
){
    constructor():this ("", "", "", "", "", "")
}