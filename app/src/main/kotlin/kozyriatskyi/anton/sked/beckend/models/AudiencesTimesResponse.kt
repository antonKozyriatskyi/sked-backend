package kozyriatskyi.anton.sked.beckend.models

import kotlinx.serialization.Serializable

@Serializable
data class AudiencesTimesResponse(
    val from: List<Item>,
    val to: List<Item>
)

