package no.mattilsynet.plantevernjournal.api.controllers.models

import org.wololo.geojson.FeatureCollection

fun FeatureCollection.toBehandledeOmraader() : List<String> =
    features.map { it.toString() }

