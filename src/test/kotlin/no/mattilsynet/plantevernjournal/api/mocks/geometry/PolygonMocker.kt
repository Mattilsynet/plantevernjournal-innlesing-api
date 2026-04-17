package no.mattilsynet.plantevernjournal.api.mocks.geometry

import org.wololo.geojson.Polygon

object PolygonMocker {

    fun createPolygonMock(
        x1: Double = 0.0,
        x2: Double = 10.0,
        x3: Double = 20.0,
        x4: Double = 0.0,
        y1: Double = 0.0,
        y2: Double = 10.0,
        y3: Double = 20.0,
        y4: Double = 0.0,
    ) =
        Polygon(
            arrayOf(
                arrayOf(
                    doubleArrayOf(x1, y1),
                    doubleArrayOf(x2, y2),
                    doubleArrayOf(x3, y3),
                    doubleArrayOf(x4, y4),
                )
            )
        )
}
