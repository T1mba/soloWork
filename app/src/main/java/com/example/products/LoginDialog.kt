package com.example.products

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import org.json.JSONObject
import kotlin.math.log
//Создаём лямбда-функцию чтобы передавать данные в нужные actiivty
class LoginDialog (private val callback:(login:String,paswword:String)-> Unit) : DialogFragment()
{


    override fun onCreateDialog(savedInstanceState:Bundle?): Dialog {
        return activity?.let {
            //Создаём переменные для работы с внешними элементами

            val builder = AlertDialog.Builder(it)
            val loginLayout  = layoutInflater.inflate(R.layout.acitivty_login, null)
            val loginError = loginLayout.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.loginError)
            val loginText = loginLayout.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.login)
            val PasswordError = loginLayout.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.passwordError)
            val PasswordText  = loginLayout.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.password)
            val loginButton = loginLayout.findViewById<TextView>(R.id.LoginButton)
            val exitButton = loginLayout.findViewById<TextView>(R.id.exitButton)
            //Устанавливаем титульник и инконку для alertdialog
            val myDialog = builder.setView(loginLayout)
                .setTitle("Авторизация")
                .setIcon(R.mipmap.ico)
                .create()
                //делаем обработчик события для кнопки логин
            loginButton.setOnClickListener{
                var hassErrors = false
                if(loginText.text.isNullOrEmpty()){
                    hassErrors = true
                    loginError.error = "Поле должно быть заполнено"
                }
                else
                    loginError.error = ""
                if(PasswordText.text.isNullOrEmpty()){
                    hassErrors = true
                    PasswordError.error = "Поле должно быть заполнено"
                }
                else
                    PasswordError.error = ""
                if(!hassErrors){
                    myDialog.dismiss()
                    callback.invoke(
                        loginText.text.toString(),
                        PasswordText.text.toString()
                    )
                }
            }


            myDialog
        }?: throw IllegalStateException("Activity cannot be null")
    }

}