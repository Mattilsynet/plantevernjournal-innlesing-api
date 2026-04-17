package no.mattilsynet.plantevernjournal.api.mocks.geometry

import org.wololo.geojson.LineString

object LineStringMocker {

    fun createLineStringMock(
        x1: Double = 0.0,
        x2: Double = 10.0,
        y1: Double = 0.0,
        y2: Double = 20.0,
    ) =
        LineString(
            arrayOf(
                doubleArrayOf(x1, y1),
                doubleArrayOf(x2, y2),
            )
        )
}
