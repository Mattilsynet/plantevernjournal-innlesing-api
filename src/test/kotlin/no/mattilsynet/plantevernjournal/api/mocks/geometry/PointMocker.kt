package no.mattilsynet.plantevernjournal.api.mocks.geometry

import org.wololo.geojson.Point

object PointMocker {

    fun createPointMock(
        x: Double = 10.0,
        y: Double = 20.0,
    ) =
        Point(doubleArrayOf(x, y))

}
