# Validering av geometri

Gyldige geometrityper er gitt [her](src/main/kotlin/no/mattilsynet/plantevernjournal/api/shared/kodeverk/GeometriTyper.kt). Det gjøres en enkel validering av dataene som sendes inn.

Dersom det ikke sendes inn en gyldig type, så kommer det en tilbakemelding av typen:
_Feature[indeks] har en geometritype som ikke støttes: typeSomErSendtInn_

### Point:
Ved manglende _coordinats_
_"Feature[indeks] Point mangler koordinater"_

Ved feil format på koordinatet:
_Feature[index] Point må ha gyldig koordinat_

