package no.mattilsynet.plantevernjournal.api.shared.kodeverk

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "Bruksområde for bruk av plantevernmidler",
)
enum class Bruksomraade(val beskrivelse: String) {
    GOLFBANE(beskrivelse = "Golfbane"),
    JORDBRUK(beskrivelse = "Jordbruk"),
}
