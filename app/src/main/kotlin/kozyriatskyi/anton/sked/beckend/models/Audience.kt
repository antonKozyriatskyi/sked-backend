package kozyriatskyi.anton.sked.beckend.models

import kotlinx.serialization.Serializable
import kozyriatskyi.anton.sutparser.ParsedAudience

@Serializable
data class Audience(
    val number: String,
    val isFree: Boolean,
    val note: String,
    val capacity: String
)

fun List<ParsedAudience>.mapToAudiences(): List<Audience> = map {
    Audience(
        number = it.number,
        isFree = it.isFree,
        note = it.note,
        capacity = it.capacity
    )
}