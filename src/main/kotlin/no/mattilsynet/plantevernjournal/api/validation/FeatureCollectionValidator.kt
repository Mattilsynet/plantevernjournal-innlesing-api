package no.mattilsynet.plantevernjournal.api.validation

import org.springframework.stereotype.Component
import org.wololo.geojson.Feature
import org.wololo.geojson.FeatureCollection
import org.wololo.geojson.Geometry
import org.wololo.geojson.LineString
import org.wololo.geojson.MultiLineString
import org.wololo.geojson.MultiPolygon
import org.wololo.geojson.Point
import org.wololo.geojson.Polygon

@Component
class FeatureCollectionValidator {

    fun validate(fc: FeatureCollection) {
        require(!fc.features.isNullOrEmpty()) {
            "FeatureCollection må inneholde minst en feature"
        }

        fc.features.forEachIndexed { index, feature ->
            validateFeature(feature, index)
        }
    }

    private fun validateFeature(feature: Feature, index: Int) {
        val geometry = feature.geometry
            ?: throw IllegalArgumentException("Feature[$index] mangler geometry")

        validateGeometry(geometry, index)
    }

    private fun validateGeometry(geometry: Geometry, index: Int) {
        when (geometry) {
            is Point -> validatePoint(geometry, index)
            is LineString -> validateLineString(geometry, index)
            is MultiLineString -> validateMultiLineString(geometry, index)
            is Polygon -> validatePolygon(geometry, index)
            is MultiPolygon -> validateMultiPolygon(geometry, index)

            else -> throw IllegalArgumentException(
                "Feature[$index] har en geometritype som ikke støttes: ${geometry.javaClass.simpleName}"
            )
        }
    }

    // --- Geometry validators ---

    private fun validatePoint(point: Point, index: Int) {
        point.coordinates
            ?: throw IllegalArgumentException("Feature[$index] Point mangler koordinater")

    }

    private fun validateLineString(line: LineString, index: Int) {
        val koordinater = line.coordinates
        require(koordinater != null && koordinater.size >= 2) {
            "Feature[$index] LineString må ha minst to punkter"
        }
    }

    private fun validateMultiLineString(line: MultiLineString, index: Int) {
        val koordinater = line.coordinates
        require(koordinater != null &&
                koordinater.size >= 2 &&
                koordinater.all { it.size >= 2 }) {
            "Feature[$index] MultiLineString må ha minst to punkter i minst to linjer"
        }
    }

    private fun validatePolygon(polygon: Polygon, index: Int) {
        val rings = polygon.coordinates
        require(rings != null && rings.isNotEmpty()) {
            "Feature[$index] Polygon må ha minst en ring"
        }

        rings.forEachIndexed { ringIndex, ring ->
            sjekkPolygonverdier(ring, index, ringIndex)
        }
    }

    private fun sjekkPolygonverdier(ring: Array<out DoubleArray>, index: Int, ringIndex: Int) {
        require(ring.size >= 4) {
            "Feature[$index] Polygon ring[$ringIndex] må ha minst 4 punkter"
        }
        require(ring.first().contentEquals(ring.last())) {
            "Feature[$index] Polygon ring[$ringIndex] må være lukket"
        }
    }

    private fun validateMultiPolygon(polygon: MultiPolygon, index: Int) {
        val rings = polygon.coordinates
        require(rings != null && rings.size >= 2) {
            "Feature[$index] MultiPolygon må ha minst to ringer"
        }

        rings.forEachIndexed { ringIndex, ring ->
            ring.forEachIndexed { index, arrays ->
                sjekkPolygonverdier(arrays, index, ringIndex)
            }
        }
    }
}
