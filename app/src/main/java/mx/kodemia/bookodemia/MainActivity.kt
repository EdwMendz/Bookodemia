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
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    //Generamos el TAG
    private val TAG = MainActivity::class.qualifiedName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



            btn_inisiar_sesion.setOnClickListener {
                //Hacemos la peticion
                val cola = Volley.newRequestQueue(applicationContext)
                val json = JSONObject()
                json.put("email", tiet_email.text)
                json.put("password", tiet_password.text)
                json.put("device_name", "User's phone")
                val peticion = JsonObjectRequest(
                    Request.Method.POST,getString(R.string.url_servidor)+getString(R.string.api_login),
                    json,
                    { response ->
                        Log.d(TAG,response.toString())
                    },
                    { error ->
                        Log.e(TAG,error.toString())
                    })
                cola.add(peticion)
            }


        //validacion del email
        tiet_email.addTextChangedListener(object : TextWatcher {
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

        //Uso el botton para ir a la pagina principal

//        button_login.setOnClickListener {
//            startActivity(Intent(this, MainScreen::class.java))
//        }

        registrate.setOnClickListener {
            startActivity(Intent(this, Registro::class.java))
        }
    }
}