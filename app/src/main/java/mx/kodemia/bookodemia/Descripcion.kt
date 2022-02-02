package mx.kodemia.bookodemia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import mx.kodemia.bookodemia.dataclasslibro.Book

class Descripcion : AppCompatActivity() {
    private val TAG = Descripcion::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripcion)
        intent.extras?.let {
            val book = it.getSerializable("book") as Book
            Log.d(TAG,book.attributes.title)
        }

    }
}