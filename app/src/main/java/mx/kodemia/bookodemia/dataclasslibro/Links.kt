package mx.kodemia.bookodemia.dataclasslibro

import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val self: String,
    val related: String = ""
): java.io.Serializable
