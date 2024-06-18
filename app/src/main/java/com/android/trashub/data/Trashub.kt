package com.android.trashub.data

data class Trashub(
    var createdAt: String,
    var id: Int,
    var kategori_sampah: String,
    var jenis_sampah: String,
    val imageUrl: Int,
    val keterangan_sampah: String
)