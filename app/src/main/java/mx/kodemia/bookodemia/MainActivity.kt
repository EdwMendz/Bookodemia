package mx.kodemia.bookodemia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mx.kodemia.bookodemia.dataclass.Errors
import mx.kodemia.bookodemia.extra.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    //Generamos el TAG
    private val TAG = MainActivity::class.qualifiedName


    override fun onCreate(savedInstanceState: Bundle?) {
        if (validarSesion(applicationContext)) {
            lanzarActivity()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    fun init() {
        btn_inisiar_sesion.setOnClickListener {
            //Hacemos la peticion
                validate()
                realizarPeticion()

        }
        //validacion del email
        tiet_email_login.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty()) {
                    til_email.setError("El campo es requerido")
                } else {
                    til_email.setErrorEnabled(false)
                    til_email.setError("")

                }
            }

        })
        //validacion del passworrd
        tiet_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty()) {
                    til_password.setError("El campo es requerido")
                } else {
                    til_password.setErrorEnabled(false)
                    til_password.setError("")
                }
            }

        })


        //Uso el botton para ir a la pagina principal

//        button_login.setOnClickListener {
//            startActivity(Intent(this, MainScreen::class.java))
//        }

        registrate_login.setOnClickListener {
            startActivity(Intent(this, Registro::class.java))
        }
    }

    fun realizarPeticion() {
        VolleyLog.DEBUG = true
        if (estaEnLinea(applicationContext)) {
            pb_login.visibility = View.VISIBLE
            registrate_login.visibility = View.GONE
            btn_inisiar_sesion.visibility = View.GONE



            val cola = Volley.newRequestQueue(applicationContext)
            val json = JSONObject()
            json.put("email", tiet_email_login.text.toString())
            json.put("password", tiet_password.text.toString())
            json.put("device_name", "User's phone")
            val peticion = object : JsonObjectRequest(Request.Method.POST,
                getString(R.string.url_servidor) + getString(R.string.api_login),
                json,
                { response ->
                    val jsonObject = JSONObject(response.toString())
                    inisiarSesion(applicationContext, jsonObject)
                    if (validarSesion(applicationContext)) {
                        lanzarActivity()
                    }
                },
                { error ->
                    pb_login.visibility = View.GONE
                    registrate_login.visibility = View.VISIBLE
                    btn_inisiar_sesion.visibility = View.VISIBLE

                    val json = JSONObject(String(error.networkResponse.data, Charsets.UTF_8))
                    val errors = Json.decodeFromString<Errors>(json.toString())
                    for (error in errors.errors) {
                        mensajeEmergente(this, error.detail)
                    }
                    Log.e(TAG, error.networkResponse.toString())
                    Log.e(TAG, error.toString())
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    headers["Content-type"] = "application/json"
                    return headers
                }
            }
            cola.add(peticion)
        } else {
            mensajeEmergente(this, getString(R.string.error_internet))
        }
    }


    fun lanzarActivity() {
        val intent = Intent(this, MainScreen::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }





    private fun validarCorreo(): Boolean {
        return if (tiet_email_login.text.toString().isEmpty()) {
            til_email.error = getString(R.string.campo_vacio)
            false
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(tiet_email_login.text.toString())
                    .matches()
            ) {
                til_email.isErrorEnabled = false
                true
            } else {
                til_email.error = getString(R.string.error_correo)
                false
            }
        }
    }

    private fun validarContrasenia(): Boolean {
        return if (tiet_email_login.text.toString().isEmpty()) {
            til_email.error = getString(R.string.campo_vacio)
            false
        } else {
            til_password.isErrorEnabled = false
            true
        }
    }

    private fun validate(){
        val result = arrayOf(validarCorreo(),validarContrasenia())
        if (false in result)
            return
    }
}

