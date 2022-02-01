package mx.kodemia.bookodemia.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.kodemia.bookodemia.dataclasslibro.Book

class AdaptadorBooks(val activity: Activity, val books: MutableList<Book>): RecyclerView.Adapter<AdaptadorBooks.BookHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorBooks.BookHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AdaptadorBooks.BookHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return books.size
    }
    class BookHolder(view: View):RecyclerView.ViewHolder(view) {

    }
}