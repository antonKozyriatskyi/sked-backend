package kozyriatskyi.anton.sked.beckend.models

import kotlinx.serialization.Serializable
import kozyriatskyi.anton.sutparser.ParsedLesson

@Serializable
data class Lesson(
    val date: String,
    val number: String,
    val type: String,
    val cabinet: String,
    val shortName: String,
    val name: String,
    val addedOnDate: String,
    val addedOnTime: String,
    val who: String,
    val whoShort: String
)

fun ParsedLesson.toLesson(): Lesson = Lesson(
    date,
    number,
    type,
    cabinet,
    shortName,
    name,
    addedOnDate,
    addedOnTime,
    who,
    whoShort
)

fun List<ParsedLesson>.mapToLessons(): List<Lesson> = map(ParsedLesson::toLesson)