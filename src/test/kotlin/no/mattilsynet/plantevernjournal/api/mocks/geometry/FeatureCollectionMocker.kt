package no.mattilsynet.plantevernjournal.api.mocks.geometry

import org.wololo.geojson.Feature
import org.wololo.geojson.FeatureCollection

object FeatureCollectionMocker {

    fun createFeatureCollectionMock(feature: Feature) =
        FeatureCollection(arrayOf(feature))

}
