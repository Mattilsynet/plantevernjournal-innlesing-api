package no.mattilsynet.plantevernjournal.api.shared.kodeverk

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "Gyldige geometrityper",
)
enum class GeometriTyper(val beskrivelse: String) {
    LineString(beskrivelse = "LineString"),
    MultiLineString(beskrivelse = "MultiLineString"),
    MultiPoint(beskrivelse = "MultiPoint"),
    MultiPolygon(beskrivelse = "MultiPolygon"),
    Point(beskrivelse = "Point"),
    Polygon(beskrivelse = "Polygon"),
}
