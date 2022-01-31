package mx.kodemia.bookodemia.extra

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.android.material.snackbar.Snackbar
import mx.kodemia.bookodemia.R
import org.json.JSONObject

//fun obtenerPreferences

fun obtenerPreferencias(context: Context):SharedPreferences {
    return  EncryptedSharedPreferences.create(context.getString(
            R.string.name_file_preferences),
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
}

fun inisiarSesion(context: Context, jsonObject:JSONObject){
    val sharedPreferences = obtenerPreferencias(context)
    with(sharedPreferences.edit()){
        putString("token",jsonObject.getString(context.getString(R.string.key_token)))
        apply()
    }
}

fun validarSesion(context: Context):Boolean{
    val sharedPreferences = obtenerPreferencias(context)
    val token = sharedPreferences.getString("token","")
    return !token.isNullOrEmpty()
}
fun eliminarSesion(context: Context){
    val sharedPreferences = obtenerPreferencias(context)
    with(sharedPreferences.edit()){
        clear()
        apply()
    }
}

fun obtenerDeSesion(context: Context, clave: String):String{
    val sharedPreferences = obtenerPreferencias(context)
    return sharedPreferences.getString(clave,"").toString()
}

fun mensajeEmergente(activity: Activity, mensaje: String){
    val snackbar = Snackbar.make(activity.findViewById(android.R.id.content),mensaje,Snackbar.LENGTH_LONG)
    val view = snackbar.view
    val params = snackbar.view.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    view.layoutParams = params
    snackbar.show()
}

fun estaEnLinea(context: Context): Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if(capabilities != null){
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                Log.i("internet", "NetworkCapabilities.TRANSPORT_CELULAR")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                Log.i("internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                Log.i("internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}
