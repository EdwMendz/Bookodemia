package mx.kodemia.bookodemia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_registro.*
import org.json.JSONObject

class Registro : AppCompatActivity() {

    private val TAG = MainActivity::class.qualifiedName
    override

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btn_registrar.setOnClickListener {
            val json = JSONObject()
            json.put("name", tiet_usuario_registro.text)
            json.put("email", tiet_correo_registro.text)
            json.put("password", tiet_contrasenia_registro.text)
            json.put("password_confirmation", tiet_contrasenia2_registro.text)
            json.put("device_name", "User's phone")

            val cola = Volley.newRequestQueue(applicationContext)
            val peticion = JsonObjectRequest(
                Request.Method.POST,
                getString(R.string.url_servidor)+getString(R.string.api_register),
                json,
                { response ->
                    Log.d(TAG,response.toString())
                },{ error->
                    Log.e(TAG,error.toString())
                })
        }


    }
}
