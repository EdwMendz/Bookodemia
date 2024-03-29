package mx.kodemia.bookodemia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mx.kodemia.bookodemia.dataclass.Errors
import mx.kodemia.bookodemia.extra.estaEnLinea
import mx.kodemia.bookodemia.extra.inisiarSesion
import mx.kodemia.bookodemia.extra.mensajeEmergente
import org.json.JSONObject

class Registro : AppCompatActivity() {

    private val TAG = MainActivity::class.qualifiedName

    override

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        init()
    }

    fun init() {
        btn_registrar.setOnClickListener {
            validate()
            realizarPeticion()

        }

    }

    fun realizarPeticion() {
        if (estaEnLinea(applicationContext)) {
            val json = JSONObject()
            json.put("name", tiet_usuario_registro.text)
            json.put("email", tiet_correo_registro.text)
            json.put("password", tiet_contrasenia_registro.text)
            json.put("password_confirmation", tiet_contrasenia2_registro.text)
            json.put("device_name", "User's phone")

            val cola = Volley.newRequestQueue(applicationContext)
            val peticion = object: JsonObjectRequest(
                Request.Method.POST,
                getString(R.string.url_servidor) + getString(R.string.api_register),
                json,
                { response ->
                    Log.d(TAG, response.toString())
                    val jsonObject = JSONObject(response.toString())
                    inisiarSesion(applicationContext, jsonObject)
                    val intent = Intent(this, MainScreen::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }, { error ->
                    val json = JSONObject(String(error.networkResponse.data, Charsets.UTF_8))
                    val errors = Json.decodeFromString<Errors>(json.toString())
                    for (error in errors.errors){
                        mensajeEmergente(this,error.detail)
                    }
                    Log.e(TAG,error.toString())
                }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
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

    //validar correo
    private fun validarCorreo(): Boolean {
        return if (tiet_correo_registro.text.toString().isEmpty()) {
            til_correo_registro.error = getString(R.string.campo_vacio)
            false
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(tiet_correo_registro.text.toString())
                    .matches()
            ) {
                til_correo_registro.isErrorEnabled = false
                true
            } else {
                til_correo_registro.error = getString(R.string.error_correo)
                false
            }
        }
    }
    //validar usuario
    private fun validarUsuario(): Boolean {
        return if (tiet_usuario_registro.text.toString().isEmpty()) {
            til_usuario_registro.error = getString(R.string.campo_vacio)
            false
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(tiet_usuario_registro.text.toString())
                    .matches()
            ) {
                til_usuario_registro.isErrorEnabled = false
                true
            } else {
                til_usuario_registro.error = getString(R.string.error_usuario)
                false
            }
        }
    }

    //validar contrasenia
    private fun validarContrasenia(): Boolean {
        return if (tiet_contrasenia_registro.text.toString().isEmpty() || tiet_contrasenia2_registro.text.toString().isEmpty()) {
            til_contrasenia_registro.error = getString(R.string.campo_vacio)
            til_contrasenia2_registro.error = getString(R.string.campo_vacio)
            false
        } else {
            true
        }
    }
    private fun contraseniasIguales() : Boolean {
        return if (tiet_contrasenia_registro.text.toString() != tiet_contrasenia2_registro.text.toString()){
            til_contrasenia_registro.error = "contrasenia distinta"
            til_contrasenia2_registro.error = "contrasenia distinta"
            false
        }else{
            true
        }
    }

    private fun validate(){
        val result = arrayOf(validarCorreo(),validarUsuario(),validarContrasenia(),contraseniasIguales())
        if (false in result)
            return
    }


}
