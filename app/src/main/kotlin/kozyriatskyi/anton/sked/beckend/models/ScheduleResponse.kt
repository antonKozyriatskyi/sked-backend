package kozyriatskyi.anton.sked.beckend.models

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleResponse(
    val schedule: List<Lesson>
)