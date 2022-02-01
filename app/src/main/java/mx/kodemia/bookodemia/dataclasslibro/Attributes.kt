package mx.kodemia.bookodemia.dataclasslibro

import kotlinx.serialization.SerialName

data class Attributes(
    val title: String,
    val slug: String,
    val content: String,
    @SerialName("created-at")
    val createdAt: String,
    @SerialName("updated-at")
    val updatedAt: String

)
