package mx.kodemia.bookodemia.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Errors(
    val errors:List<Error>
)
