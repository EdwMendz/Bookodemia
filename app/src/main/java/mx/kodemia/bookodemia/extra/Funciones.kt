package mx.kodemia.bookodemia.extra

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
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

