package com.example.products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MaterialActivity : AppCompatActivity() {
    private lateinit var app: myApp
    private var  materialList = ArrayList<Material>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        app = applicationContext as myApp
    }
}