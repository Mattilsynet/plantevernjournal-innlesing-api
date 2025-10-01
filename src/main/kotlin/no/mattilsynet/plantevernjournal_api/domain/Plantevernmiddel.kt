package no.mattilsynet.plantevernjournal_api.domain

data class Plantevernmiddel(
    val autorisasjonsnummer: String,
    val dosering: Dosering
)