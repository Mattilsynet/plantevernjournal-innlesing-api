package no.mattilsynet.plantevernjournal.api.controllers.models

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.wololo.geojson.FeatureCollection

fun FeatureCollection.toBehandledeOmraader() : List<JsonElement> =
    features.map { feature ->
        Json.parseToJsonElement(jacksonObjectMapper().writeValueAsString(feature))
    }
