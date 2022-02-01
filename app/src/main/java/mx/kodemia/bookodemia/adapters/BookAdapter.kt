//package mx.kodemia.bookodemia.adapters
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import mx.kodemia.bookodemia.R
//
//class BookAdapter:RecyclerView.Adapter<BookAdapter.BookHolder>(){
//
//
//
//    inner class BookHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        var itemTitulo: TextView
//        var itemAutor: TextView
//        var itemCategoria: TextView
//
//        init {
//            itemTitulo = itemView.findViewById(R.id.tv_titulo_book)
//            itemAutor = itemView.findViewById(R.id.tv_autor_book)
//            itemCategoria = itemView.findViewById(R.id.tv_categoria_book)
//        }
//    }
//
//    val titles = arrayOf("codelia",
//    "marAzul",
//        "rojo",
//        "Genio",
//        "Maso"
//        )
//
//    val autor = arrayOf("jose",
//        "mar",
//        "edwin",
//        "Kamila",
//        "Karla"
//    )
//
//    val categoria = arrayOf("codelia",
//        "marAzul",
//        "rojo",
//        "Espanto",
//        "Accion"
//    )
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): BookHolder {
//        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cardview_book, viewGroup,false)
//        return BookHolder(v)
//    }
//
//    override fun onBindViewHolder(bookHolder: BookHolder, position: Int) {
//        bookHolder.itemTitulo.text = titles[position]
//        bookHolder.itemAutor.text = autor[position]
//        bookHolder.itemCategoria.text = categoria[position]
//    }
//
//    override fun getItemCount(): Int {
//       return titles.size
//    }
//}
