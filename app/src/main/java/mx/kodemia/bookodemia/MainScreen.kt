package mx.kodemia.bookodemia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main_screen.*
import mx.kodemia.bookodemia.adapters.BookAdapter
import mx.kodemia.bookodemia.extra.eliminarSesion
import mx.kodemia.bookodemia.extra.obtenerDeSesion
import mx.kodemia.bookodemia.models.DatosBooks

class MainScreen : AppCompatActivity() {
    private val TAG = MainScreen::class.qualifiedName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        this.supportActionBar?.hide()

        val reciclerView = findViewById<RecyclerView>(R.id.recycler_view_books)
        val adapter = BookAdapter()

        reciclerView.layoutManager = LinearLayoutManager(this)
        reciclerView.adapter = adapter

        init()

    }

    fun init() {
        btn_cerrar_sesion.setOnClickListener {
            eliminarSesion(applicationContext)
            //startActivity(Intent(this,MainActivity::class.java))
            //finish()
            val cola = Volley.newRequestQueue(applicationContext)
            val peticion = object : StringRequest(
                Request.Method.POST,
                getString(R.string.url_servidor) + getString(R.string.api_logout),
                 { response ->
                     Log.d(TAG,"todo salio bien")
                    eliminarSesion(applicationContext)
                 startActivity(Intent(this,MainActivity::class.java))
                     finish()
                }, { error ->

                }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Authorization"] = "Bearer ${obtenerDeSesion(applicationContext, "token")}"
                    return headers
                }
            }
            cola.add(peticion)
        }
    }
}

