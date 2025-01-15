package com.example.firebase_pam.model


data class Mahasiswa (
    val nama: String,
    val nim: String,
    val alamat: String,
    val kelas: String,
    val angkatan: String,
    val jeniskelamin: String,
    val judulskripsi: String,
    val dosenpembimbing1: String,
    val dosenpembimbing2: String
){
    constructor():this ("", "", "", "", "", "","","","")
}