package com.example.products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.lang.Exception

class ProductActivity : AppCompatActivity() {
    private var ProductList = ArrayList<Product>()
    private lateinit var app: myApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        app = applicationContext as myApp
        val productRecyclerView = findViewById<RecyclerView>(R.id.ProductView)
        if(app.token.isNotEmpty()){
            HTTP.requestGET("http://s4a.kolei.ru/Product",
                mapOf("token" to app.token)
            ){result, error ->
               runOnUiThread {
                   if(result!=null)
                   {

                       try{
                           ProductList.clear()
                           val json = JSONObject(result)
                           if (!json.has("notice"))
                               throw Exception("Не верный формат ответа ожидался объект notice")
                           if(json.getJSONObject("notice").has("answer"))
                               throw Exception(json.getJSONObject("notice").getString("answer"))
                           if(json.getJSONObject("notice").has("data"))
                           {
                               val data = json.getJSONObject("notice").getJSONArray("data")
                               for(i in 0 until data.length())

                               {
                                   val item = data.getJSONObject(i)
                                        ProductList.add(
                                            Product(
                                                item.getInt("ID"),
                                                item.getString("Title"),
                                                item.getInt("ProductTypeID"),
                                                item.getInt("ArticleNumber"),
                                                item.getString("Description"),
                                                item.getString("Image"),
                                                item.getInt("ProductionPersonCount"),
                                                item.getInt("ProductionWorkshopNumber"),
                                                item.getInt("MinCostForAgent")
                                            )
                                        )
                               }
                               runOnUiThread {
                                   productRecyclerView.adapter?.notifyDataSetChanged()
                               }
                           }
                           else{
                               throw Exception("Не верный формат, ожидался объект data")
                           }

                       }
                       catch (e: Exception){
                           AlertDialog.Builder(this)
                               .setTitle("Ошибка")
                               .setMessage(e.message)
                               .setPositiveButton("OK", null)
                               .show()
                       }
                   }

               }

            }


        }
        else
            Toast.makeText(this,"токен не найден, нужно залогониться", Toast.LENGTH_LONG)
                .show()
        productRecyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        val productAdapter= ProductAdapter(ProductList, this)
            productAdapter.setItemClickListener {

            }
        productRecyclerView.adapter = productAdapter
    }
}