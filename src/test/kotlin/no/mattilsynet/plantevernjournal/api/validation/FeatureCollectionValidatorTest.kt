package no.mattilsynet.plantevernjournal.api.validation

import no.mattilsynet.plantevernjournal.api.mocks.geometry.FeatureMocker.createFeatureMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.LineStringMocker.createLineStringMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.MultiLineStringMocker.createMultiLineStringMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.MultiPolygonMocker.createMultiPolygonMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.PointMocker.createPointMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.PolygonMocker.createPolygonMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.wololo.geojson.Feature
import org.wololo.geojson.FeatureCollection
import org.wololo.geojson.Geometry
import org.wololo.geojson.LineString
import org.wololo.geojson.MultiLineString
import org.wololo.geojson.MultiPolygon
import org.wololo.geojson.Point

class FeatureCollectionValidatorTest {

    private val validator = FeatureCollectionValidator()

    private lateinit var featureCollectionMock: FeatureCollection

    @Test
    fun `Validering av Point ok`() {
        // Given:
        featureCollectionMock = createFeatureCollectionMock(createPointMock())

        // When & then:
        assertDoesNotThrow {
            validator.validate(featureCollectionMock)
        }
    }

    @Test
    fun `Validering av LineString ok`() {
        // Given:
        featureCollectionMock = createFeatureCollectionMock(createLineStringMock())

        // When & then:
        assertDoesNotThrow {
            validator.validate(featureCollectionMock)
        }
    }

    @Test
    fun `Validering av MultiLineString ok`() {
        // Given:
        featureCollectionMock = createFeatureCollectionMock(createMultiLineStringMock())

        // When & then:
        assertDoesNotThrow {
            validator.validate(featureCollectionMock)
        }
    }

    @Test
    fun `Validering av Polygon ok`() {
        // Given:
        featureCollectionMock = createFeatureCollectionMock(createPolygonMock())

        // When & then:
        assertDoesNotThrow {
            validator.validate(featureCollectionMock)
        }
    }

    @Test
    fun `Validering av MultiPolygon ok`() {
        // Given:
        featureCollectionMock = createFeatureCollectionMock(createMultiPolygonMock())

        // When & then:
        assertDoesNotThrow {
            validator.validate(featureCollectionMock)
        }
    }

    @Test
    fun `Validering av tom featureCollection gir exception`() {
        // Given:
        featureCollectionMock = FeatureCollection(emptyArray())

        // When & then:
        assertThrows(IllegalArgumentException::class.java) {
            validator.validate(featureCollectionMock)
        }.message!!.let { message ->
            assertEquals(
                message,
                "FeatureCollection må inneholde minst en feature"
            )
        }
    }

    @Test
    fun `Validering feiler naar geometry er null`() {
        // Given:
        val feature = Feature(null, null)
        featureCollectionMock = FeatureCollection(arrayOf(feature))

        // When & then:
        assertThrows(IllegalArgumentException::class.java) {
            validator.validate(featureCollectionMock)
        }.message!!.let { message ->
            assertEquals(
                "Feature[0] mangler geometry",
                message
            )
        }
    }

    @Test
    fun `Validering feiler naar Point manger y-koordinat`() {
        // Given:
        val invalidPoint = Point(doubleArrayOf(10.0))
        featureCollectionMock = FeatureCollection(arrayOf(Feature(invalidPoint, null)))

        // When & then:
        assertThrows(IllegalArgumentException::class.java) {
            validator.validate(featureCollectionMock)
        }.message!!.let { message ->
            assertEquals(
                "Feature[0] Point må ha gyldig koordinat",
                message
            )
        }
    }

    @Test
    fun `Validering feiler for LineString med ett punkt`() {
        // Given:
        val line = LineString(
            arrayOf(
                doubleArrayOf(0.0, 0.0)
            )
        )

        // When & then:
        featureCollectionMock = FeatureCollection(arrayOf(Feature(line, null)))

        assertThrows(IllegalArgumentException::class.java) {
            validator.validate(featureCollectionMock)
        }.message!!.let { message ->
            assertEquals(
                "Feature[0] LineString må ha minst to punkter",
                message
            )
        }
    }

    @Test
    fun `Validering feiler for MultiLineString med en Line`() {
        // Given:
        val line = MultiLineString(
            arrayOf(
                arrayOf(
                    doubleArrayOf(2.0, 2.0),
                    doubleArrayOf(1.0, 4.0),
                ),
            )
        )

        // When & then:
        featureCollectionMock = FeatureCollection(arrayOf(Feature(line, null)))

        assertThrows(IllegalArgumentException::class.java) {
            validator.validate(featureCollectionMock)
        }.message!!.let { message ->
            assertEquals(
                "Feature[0] MultiLineString må ha minst to punkter i minst to linjer",
                message
            )
        }
    }

    @Test
    fun `Validering feiler naar Polygon ikke er lukket`() {
        // Given:
        featureCollectionMock = createFeatureCollectionMock(
            createPolygonMock(y4 = 99.0)
        )

        // When & then:
        assertThrows(IllegalArgumentException::class.java) {
            validator.validate(featureCollectionMock)
        }.message!!.let { message ->
            assertEquals(
                "Feature[0] Polygon ring[0] må være lukket",
                message
            )
        }
    }

    @Test
    fun `Validering feiler naar MultiPolygon bare har ett Polygon`() {
        // Given:
        featureCollectionMock = createFeatureCollectionMock(
            MultiPolygon(
                arrayOf(
                    arrayOf(
                        arrayOf(
                            doubleArrayOf(1.0, 0.0),
                            doubleArrayOf(2.0, 2.0),
                            doubleArrayOf(3.0, 3.0),
                            doubleArrayOf(1.0, 0.0),
                        ),
                    ),
                )
            )

        )

        // When & then:
        assertThrows(IllegalArgumentException::class.java) {
            validator.validate(featureCollectionMock)
        }.message!!.let { message ->
            assertEquals(
                "Feature[0] MultiPolygon må ha minst to ringer",
                message
            )
        }
    }

    @Test
    fun `Validering feiler ved ugyldig geometry type`() {
        // Given:
        val geometry = object : Geometry() {} // fake unsupported type
        featureCollectionMock = createFeatureCollectionMock(geometry)

        // When & then:
        assertThrows(IllegalArgumentException::class.java) {
            validator.validate(featureCollectionMock)
        }.message!!.let { message ->
            assertEquals(
                "Feature[0] har en geometritype som ikke støttes: ",
                message
            )
        }
    }

    private fun createFeatureCollectionMock(geometry: Geometry) =
        FeatureCollection(arrayOf(createFeatureMock(geometry)))
}
