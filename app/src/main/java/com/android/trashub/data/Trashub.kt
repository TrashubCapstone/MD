package com.android.trashub.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trashub(
    var createdAt: String = "",
    var id: String = "",
    var kategori_sampah: String = "",
    var jenis_sampah: String = "",
    val imageUrl: String = "",
    val keterangan_sampah: String = "",
    val nama_sampah: String = ""
) : Parcelable