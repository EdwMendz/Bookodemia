package mx.kodemia.bookodemia.dataclasslibro

import kotlinx.serialization.Serializable

@Serializable
data class Books(
    val data: MutableList<Book>): java.io.Serializable

