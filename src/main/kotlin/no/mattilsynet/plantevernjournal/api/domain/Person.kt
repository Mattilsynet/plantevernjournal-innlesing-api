package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable

@Serializable
data class Person (
    val fornavn: String,

    val etternavn: String,
)