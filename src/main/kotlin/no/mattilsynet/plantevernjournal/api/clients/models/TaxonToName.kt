package no.mattilsynet.plantevernjournal.api.clients.models

import com.fasterxml.jackson.annotation.JsonAlias

data class EppoTaxon(
    val fullname: String,
    @JsonAlias("lang_iso")
    val landIso: String,
    val preferred: Boolean,
)
