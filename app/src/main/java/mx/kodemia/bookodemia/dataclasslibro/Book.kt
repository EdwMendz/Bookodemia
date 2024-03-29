package mx.kodemia.bookodemia.dataclasslibro

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val type: String,
    val id: String,
    val attributes: Attributes,
    val relationships: Relationships,
    val links: Links
): java.io.Serializable

