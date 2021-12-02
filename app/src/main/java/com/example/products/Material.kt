package com.example.products

data class Material(
    val ID:Int,
    val Title: String,
    val CountInPack: Int,
    val Unit: String,
    val CountInStock: Int,
    val MinCount: Int,
    val Description: String?,
    val Cost: Int,
    val Image:String?,
    val MaterialTypeID: Int
)
