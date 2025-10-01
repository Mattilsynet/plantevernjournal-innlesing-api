package no.mattilsynet.plantevernjournal_api.domain

data class BehandledeVekster(
    val eppoKode: String,
    val partiNummer: Int,
    val bbchFase: String,
) {
}