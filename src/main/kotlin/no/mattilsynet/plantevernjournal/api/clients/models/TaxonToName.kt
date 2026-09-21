package no.mattilsynet.plantevernjournal.api.clients.models

data class EppoTaxon(
    val eppocode: String,
    val level: Int,
    val prefname: String,
)
