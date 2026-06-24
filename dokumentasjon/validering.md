# Validering av geometri

Det kan sendes inn en liste av geometriske objekter, og det gjøres en enkel validering av dataene som sendes inn.

Gyldige geometrityper er gitt [her](../src/main/kotlin/no/mattilsynet/plantevernjournal/api/shared/kodeverk/GeometriTyper.kt). 

Dersom det ikke sendes inn en gyldig type, så kommer det en tilbakemelding av typen:
_Feature[indeks] har en geometritype som ikke støttes: typeSomErSendtInn_

Det ligger eksempler på de ulike geometritypene i våre [testdata](../src/test/http-requests).

### Point:
Point må inneholde ett gyldig koordinat.

### LineString:
LineString må inneholde minst to punkter, og alle punktene må være gyldig koordinat.

### MultiLineString:
MultiLineString må inneholde minst to linjer, og alle linjene må følge samme regler som LineString.

Det sjekkes ikke om linjene er krysser hverandre, er overlappende eller like.

### Polygon:
Polygonet må inneholde minst fire punkter, og alle disse må være gyldig koordinater. Polygon må være lukket, dvs at først og siste koordinat må være like.

Dersom det fx er deler av et skifte som ikke er sprøytet pga en kolle eller noe annet, så skal det sendes inn en liste av polygoner. Det første skal være hovedområdet, og så kommer de usprøytede områdene etterpå. De usprøytede områdene må ligge inni hovedområdet.

Hvis man har flere skifter (polygoner) i samme innsending, så må disse enten sendes inn som en liste av polygoner, eller som et multipolygon med flere polygoner.

Eksempel på skifte med hull:

![Eksempel på hovedområde med hull](/dokumentasjon/bilder/skifte_med_hull.png)

### MultiPolygon:
Et multiPolygon må inneholde minst to polygoner, og ellers følge samme regler som polygon.

Det sjekkes ikke om polygonene er overlappende.

Eksempel på to skifter ved siden av hverandre:

![Eksempel på to skifter ved siden av hverandre](/dokumentasjon/bilder/to_skifter_ved_siden_av_hverandre.png)
