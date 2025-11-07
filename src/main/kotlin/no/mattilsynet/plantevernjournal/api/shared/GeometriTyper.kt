package no.mattilsynet.plantevernjournal.api.shared

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "Gyldige geometrityper",
)
enum class GeometriTyper(beskrivelse: String) {
    LINESTRING("LineString"),
    MULTILINESTRING("MultiLineString"),
    MULTIPOINT("MultiPoint"),
    MULTIPOLYGON("MultiPolygon"),
    POINT("Point"),
    POLYGON("Polygon"),
}
