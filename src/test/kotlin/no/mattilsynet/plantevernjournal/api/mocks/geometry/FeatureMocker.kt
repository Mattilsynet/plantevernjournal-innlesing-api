package no.mattilsynet.plantevernjournal.api.mocks.geometry

import org.wololo.geojson.Feature
import org.wololo.geojson.Geometry

object FeatureMocker {

    fun createFeatureMock(geometry: Geometry) =
        Feature(geometry, null)
}
