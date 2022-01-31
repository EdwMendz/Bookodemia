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
import mx.kodemia.bookodemia.extra.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    //Generamos el TAG
    private val TAG = MainActivity::class.qualifiedName


    override fun onCreate(savedInstanceState: Bundle?) {
        eliminarSesion(applicationContext)
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
            if(validarContrasenia() && validarCorreo()){
                realizarPeticion()
            }
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
        //validacion del correo
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

        registrate.setOnClickListener {
            startActivity(Intent(this, Registro::class.java))
        }
    }

    fun realizarPeticion(){
        val cola = Volley.newRequestQueue(applicationContext)
        val json = JSONObject()
        json.put("email", tiet_email_login.text)
        json.put("password", tiet_password.text)
        json.put("device_name", "User's phone")
        val peticion = JsonObjectRequest(
            Request.Method.POST,
            getString(R.string.url_servidor) + getString(R.string.api_login),
            json,
            { response ->
                //todo esta bien en el logcast -> Log.d(TAG,response.toString())
                val jsonObject = JSONObject(response.toString())
                inisiarSesion(applicationContext, jsonObject)
                if (validarSesion(applicationContext)) {
                    lanzarActivity()
                }
            },
            { error ->
                Log.e(TAG, error.toString())
            })
        if (estaEnLinea(applicationContext)) {
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

    private fun validarContrasenia() :Boolean{
        return if (tiet_email_login.text.toString().isEmpty()){
            til_email.error = getString(R.string.campo_vacio)
            false
        }else{
            true
        }
    }
}

