# Validering av geometri

Det kan sendes inn en liste av geometriske objekter, og det gjøres en enkel validering av dataene som sendes inn.

Gyldige geometrityper er gitt [her](../src/main/kotlin/no/mattilsynet/plantevernjournal/api/shared/kodeverk/GeometriTyper.kt). 

Dersom det ikke sendes inn en gyldig type, så kommer det en tilbakemelding av typen:
_Feature[indeks] har en geometritype som ikke støttes: typeSomErSendtInn_

### Point:
Point må inneholde ett gyldig koordinat.

### LineString:
LineString må inneholde minst to punkter, og alle punktene må være gyldig koordinat.

### MultiLineString:
MultiLineString må inneholde minst to linjer, og alle linjene må følge samme regler som LineString.

Det sjekkes ikke om linjene er krysser hverandre, er overlappende eller like.

### Polygon:
Polygonet må inneholde minst fire punkter, og alle disse må være gyldig koordinater. Polygon må være lukket, dvs at først og siste koordinat må være like.

### MultiPolygon:
Et multiPolygon må inneholde minst to polygoner, og ellers følge samme regler som polygon.

Det sjekkes ikke om polygonene er overlappende.
