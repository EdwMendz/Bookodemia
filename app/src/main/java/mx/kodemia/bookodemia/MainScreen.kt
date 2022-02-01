package mx.kodemia.bookodemia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mx.kodemia.bookodemia.adapters.AdaptadorBooks
import mx.kodemia.bookodemia.dataclass.Errors
import mx.kodemia.bookodemia.dataclasslibro.Book
import mx.kodemia.bookodemia.dataclasslibro.Books
//import mx.kodemia.bookodemia.adapters.BookAdapter
import mx.kodemia.bookodemia.extra.eliminarSesion
import mx.kodemia.bookodemia.extra.estaEnLinea
import mx.kodemia.bookodemia.extra.mensajeEmergente
import mx.kodemia.bookodemia.extra.obtenerDeSesion
import mx.kodemia.bookodemia.models.DatosBooks
import org.json.JSONObject
import java.nio.charset.Charset

class MainScreen : AppCompatActivity() {
    private val TAG = MainScreen::class.qualifiedName
    private lateinit var rv_books: RecyclerView
    private lateinit var pb_books: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        //this.supportActionBar?.hide()

//        val reciclerView = findViewById<RecyclerView>(R.id.recycler_view_books)
//        val adapter = BookAdapter()

//        reciclerView.layoutManager = LinearLayoutManager(this)
//        reciclerView.adapter = adapter

        init()

    }


    fun init() {
        rv_books = findViewById(R.id.recycler_view_books)
        pb_books = findViewById(R.id.pb_recicler)
        btn_cerrar_sesion.setOnClickListener {
            //eliminarSesion(applicationContext)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            val cola = Volley.newRequestQueue(applicationContext)
            val peticion = object : StringRequest(
                Request.Method.POST,
                getString(R.string.url_servidor) + getString(R.string.api_logout),
                { response ->
                    Log.d(TAG, "todo salio bien")
                    eliminarSesion(applicationContext)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, { error ->

                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] =
                        "Bearer ${obtenerDeSesion(applicationContext, "token")}"
                    return headers
                }
            }
            cola.add(peticion)
        }
        Log.d(TAG, "token: ${obtenerDeSesion(applicationContext, "token")}")
    }

    override fun onResume() {
        super.onResume()
        realizarPeticion()
    }

    fun realizarPeticion(){
        if(estaEnLinea(applicationContext)){
            val cola = Volley.newRequestQueue(applicationContext)
            val peticion =object: JsonObjectRequest(Request.Method.GET,getString(R.string.url_servidor)+getString(R.string.api_libros),null,{
                    response ->
                Log.d(TAG,response.toString())
                val books = Json.decodeFromString<Books>(response.toString())
                val adaptador = AdaptadorBooks(this,books.data)
                rv_books.layoutManager = LinearLayoutManager(this)
                rv_books.adapter = adaptador
                adaptador.notifyDataSetChanged()
                pb_books.visibility = View.GONE
                rv_books.visibility = View.VISIBLE
            },{
                    error ->
                if(error.networkResponse.statusCode == 401){
                    eliminarSesion(applicationContext)
                    val intent = Intent(this,MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                else{
                    val json = JSONObject(String(error.networkResponse.data, Charsets.UTF_8))
                    val errors = Json.decodeFromString<Errors>(json.toString())
                    for (error in errors.errors){
                        mensajeEmergente(this,error.detail)
                    }
                    rv_books.visibility = View.GONE
                    pb_books.visibility = View.VISIBLE
                }
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Authorization"] = "Bearer ${obtenerDeSesion(applicationContext,"token")}"
                    headers["Accept"] = "application/json"
                    headers["Content-type"] = "application/json"
                    return headers
                }
            }
            cola.add(peticion)
        }
        else{
            mensajeEmergente(this,getString(R.string.error_internet))
        }
    }

    }

