package no.mattilsynet.plantevernjournal_api.shared

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "Bruksområde for bruk av plantevernmidlers"
)
enum class Bruksomraade(description: String) {
    JORDBRUK(description = "Jordbruk")
}