package com.example.products

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var app: myApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val exitButton = findViewById<TextView>(R.id.exitButton)
        val logButton = findViewById<TextView>(R.id.logButton)
        app = applicationContext as myApp
        val onLoginResponce: (login: String, password: String)-> Unit = {login, password ->
            app.username = login
            val json = JSONObject()
            json.put("username", login)
            json.put("password", password)
            HTTP.requestPOST("http://s4a.kolei.ru/login",
                json,
                mapOf(
                    "ContentType" to "application/json"
                )
            ){result, error ->
                if(result!=null)
                    try {
                        val jsonResp = JSONObject(result)
                        if (!jsonResp.has("notice"))
                            throw Exception("Не верный формат ответа, ожидался объект notice")

                        if (jsonResp.getJSONObject("notice").has("answer"))
                            throw Exception(jsonResp.getJSONObject("notice").getString("answer"))
                        if (jsonResp.getJSONObject("notice").has("token")) {
                            app.token = jsonResp.getJSONObject("notice").getString("token")
                            runOnUiThread {
                                Toast.makeText(this, "sucess get token: ${app.token}", Toast.LENGTH_LONG)
                                    .show()
                            }

                        }
                        else
                            throw Exception("Не верный формат ответа, ожидался объект token")
                    }
                    catch (e:Exception){
                                runOnUiThread {
                                    AlertDialog.Builder(this)
                                        .setTitle("Ошибка")
                                        .setMessage(e.message)
                                        .setPositiveButton("OK", null)
                                        .create()
                                        .show()

                                }
                    }
                else
                    runOnUiThread {
                        AlertDialog.Builder(this)
                            .setTitle("Ошибка http-запроса")
                            .setMessage(error)
                            .setPositiveButton("OK",null)
                            .create()
                            .show()
                    }
            }
        }
        exitButton.setOnClickListener {
            HTTP.requestPOST(
                "http://s4a.kolei.ru/logout",
                JSONObject().put("username", app.username),
                mapOf(
                    "Content-Type" to "application/json"
                )
            ){result, error ->
                app.token = ""
                runOnUiThread{
                            if(result!=null){
                                Toast.makeText(this, "Logout success", Toast.LENGTH_LONG).show()
                            }
                    else{
                        AlertDialog.Builder(this)
                            .setTitle("Ошибка http-запроса")
                            .setMessage(error)
                            .setPositiveButton("OK",null)
                            .create()
                            .show()
                            }
                }
            }
        }
        logButton.setOnClickListener {
            LoginDialog(onLoginResponce).show(supportFragmentManager, null)
        }
        LoginDialog(onLoginResponce).show(supportFragmentManager, null)


    }

    fun getProduct(view: View) {
        startActivity(Intent(this, ProductActivity::class.java))

    }
}