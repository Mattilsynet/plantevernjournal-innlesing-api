package no.mattilsynet.plantevernjournal.api.validation

import org.springframework.stereotype.Component
import org.wololo.geojson.Feature
import org.wololo.geojson.FeatureCollection
import org.wololo.geojson.Geometry
import org.wololo.geojson.LineString
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
            is Polygon -> validatePolygon(geometry, index)

            else -> throw IllegalArgumentException(
                "Feature[$index] has unsupported geometry type: ${geometry.javaClass.simpleName}"
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

    private fun validatePolygon(polygon: Polygon, index: Int) {
        val rings = polygon.coordinates
        require(rings != null && rings.isNotEmpty()) {
            "Feature[$index] Polygon må ha minst en ring"
        }

        rings.forEachIndexed { ringIndex, ring ->
            require(ring.size >= 4) {
                "Feature[$index] Polygon ring[$ringIndex] må ha minst 4 punkter"
            }
        }
    }
}