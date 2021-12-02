package com.example.products

import android.graphics.Bitmap

data class Product(
    val ID:Int,
    val Title: String,
    val ProductTypeID:Int,
    val ArticleNumber:Int,
    val Description:String?,
    val Image:String,
    val ProductionPersonCount: Int,
    val ProductionWorkshopNumber:Int,
    val MinCostForAgent: Int,
    var bitmap: Bitmap? = null
)

