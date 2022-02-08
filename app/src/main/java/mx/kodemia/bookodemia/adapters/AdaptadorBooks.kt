package mx.kodemia.bookodemia.adapters

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cardview_book.view.*
import mx.kodemia.bookodemia.Descripcion
import mx.kodemia.bookodemia.R
import mx.kodemia.bookodemia.Registro
import mx.kodemia.bookodemia.dataclasslibro.Book

class AdaptadorBooks(val activity: Activity, val books: MutableList<Book>): RecyclerView.Adapter<AdaptadorBooks.BookHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorBooks.BookHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_book,parent,false)
        return BookHolder(view)
    }

    override fun onBindViewHolder(holder: AdaptadorBooks.BookHolder, position: Int) {
        val book = books.get(position)
        with(holder){
            btn_book_see.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("book",book)
                val intent = Intent(activity,Descripcion::class.java)
                intent.putExtras(bundle)
                activity.startActivity(intent)
            }
            tv_titulo_book.text = book.attributes.title
            tv_autor_book.text = book.attributes.content
            tv_categoria_book.text = book.type
        }
    }


    override fun getItemCount(): Int {
        return books.size
    }
    class BookHolder(view: View):RecyclerView.ViewHolder(view) {
        val tv_titulo_book: TextView = view.findViewById(R.id.tv_titulo_book)
        val tv_autor_book : TextView = view.findViewById(R.id.tv_autor_book)
        val tv_categoria_book : TextView = view.findViewById(R.id.tv_categoria_book)
        val btn_book_see : Button = view.findViewById(R.id.btn_book_see)
    }
}