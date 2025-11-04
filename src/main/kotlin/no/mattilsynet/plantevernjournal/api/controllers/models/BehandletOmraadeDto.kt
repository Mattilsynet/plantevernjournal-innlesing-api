package no.mattilsynet.plantevernjournal.api.controllers.models

import com.fasterxml.jackson.databind.ObjectMapper
import org.geojson.FeatureCollection

fun FeatureCollection.toBehandledeOmraader() =
    map { feature ->
        ObjectMapper().writeValueAsString(feature)
    }

