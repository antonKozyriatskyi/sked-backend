package kozyriatskyi.anton.sked.beckend.models

import kotlinx.serialization.Serializable
import kozyriatskyi.anton.sutparser.ParsedItem

/**
 * Created by Anton on 23.02.2017.
 */

/**
 * Represents a single faculty, course, group or student
 */
@Serializable
data class Item(val id: String, val value: String)

fun ParsedItem.toItem(): Item = Item(id, value)

fun List<ParsedItem>.mapToItems(): List<Item> = map { it.toItem() }
