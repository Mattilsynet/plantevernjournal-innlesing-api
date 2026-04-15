package no.mattilsynet.plantevernjournal.api.controllers.models.jwt

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AuthorizationDetail(
    @JsonProperty("systemuser_org")
    val systemUserOrg: SystemUserOrg,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SystemUserOrg(
    @JsonProperty("ID")
    val systemOrgId: String,
)
