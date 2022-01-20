package mx.kodemia.bookodemia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main_screen.*
import mx.kodemia.bookodemia.adapters.BookAdapter
import mx.kodemia.bookodemia.models.DatosBooks

class MainScreen : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        this.supportActionBar?.hide()

        val reciclerView = findViewById<RecyclerView>(R.id.recycler_view_books)
        val adapter = BookAdapter()

        reciclerView.layoutManager = LinearLayoutManager(this)
        reciclerView.adapter = adapter

    }


    }
