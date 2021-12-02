package com.example.products
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.util.ArrayList

class ProductAdapter (
    private val values: ArrayList<Product>,
    private val activity: Activity
): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    // обработчик клика по элементу списка (лямбда выражение), может быть не задан
    private var itemClickListener: ((Product) -> Unit)? = null

    fun setItemClickListener(itemClickListener: (Product) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    // Метод onCreateViewHolder вызывается при создании визуального элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // грузим layout, который содержит вёрстку элемента списка (нарисуйте сами)
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.product_item,
                parent,
                false
            )

        // создаем на его основе ViewHolder
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = values.size

    // заполняет визуальный элемент данными
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title.text = values[position].Title
        holder.article.text = values[position].ArticleNumber.toString()
        holder.price.text = values[position].MinCostForAgent.toString()
        // onIconLoad.invoke(holder.iconImageView, values[position].weatherIcon)

        holder.container.setOnClickListener {
            //кликнули на элемент списка
            itemClickListener?.invoke(values[position])
        }
        val fileName = values[position].Image.split("\\").lastOrNull()
        Log.d("KEILOG", fileName ?: "null")

        if (fileName != null) {
            if (values[position].bitmap == null) {
                HTTP.getImage("http://s4a.kolei.ru/img/${fileName}") { bitmap, error ->
                    if (bitmap != null) {
                        activity.runOnUiThread {
                            try {
                                values[position].bitmap = bitmap
                                holder.image.setImageBitmap(bitmap)
                            } catch (e: Exception) {

                            }
                        }
                    } else
                        Log.d("KEILOG", error)
                }
            } else
                holder.image.setImageBitmap(values[position].bitmap)
        } else
            holder.image.setImageResource(R.drawable.picturs)


    }


    //Реализация класса ViewHolder, хранящего ссылки на виджеты.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var title: TextView = itemView.findViewById(R.id.title)
        var article: TextView = itemView.findViewById(R.id.Article)
        var price: TextView = itemView.findViewById(R.id.price)
        var container: LinearLayout = itemView.findViewById(R.id.container)
    }
}

