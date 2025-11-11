# Elektronisk journalføring av plantevernmidler

Mattilsynet skal samle inn data om bruk av plantevernmidler i landbruket i Norge. Team Planter er et eget utviklingsteam som driver utviklingen av dette. Vi nås på epost _plantevernjournal@mattilsynet.no_ hvis dere vil kontakte oss.

plantevernjournal-innlesing-api inneholder datamodellene som skal brukes når man sender inn data til Mattilsynet. Dette er en veldig tidlig versjon av disse modellene, som er under utvikling, og det vil bli endringer.

EU-kravet som skal implementeres er beskrevet her: https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32023R0564

Dette er pågående arbeid, så det er fortsatt mye som ikke er avklart. Mer informasjon vil komme senere, som f.eks. identifisering av brukere, hvordan og hvor ofte data skal rapporteres til Mattilsynet.

Input til endepunktene vil ikke versjoneres i starten, og man må anta at det vil komme endringer som gjør at ting brekker.

Link til api-dokumentasjon: https://plantevernjournal-innlesing-api.plantevernjournal-dev.mattilsynet.io/swagger-ui/index.html

### Datamodeller
Det er laget tre ulike datamodeller for bruk av plantevernmidler, en for [innendørs bruk](https://github.com/Mattilsynet/plantevernjournal-innlesing-api/blob/master/src/main/kotlin/no/mattilsynet/plantevernjournal/api/controllers/models/InnendoersBrukDto.kt), en for [utendørs bruk](https://github.com/Mattilsynet/plantevernjournal-innlesing-api/blob/master/src/main/kotlin/no/mattilsynet/plantevernjournal/api/controllers/models/UtendoersBrukDto.kt) og en for bruk på [frø eller annet formeringsmateriale](https://github.com/Mattilsynet/plantevernjournal-innlesing-api/blob/master/src/main/kotlin/no/mattilsynet/plantevernjournal/api/controllers/models/FroeEllerFormeringsMatrialeDto.kt). En del av dataene som skal samles inn er felles, men modellene har også noen egne egenskaper som gjør at det er valgt å dele de inn, i stedet for å ha en felles datamodell.

### Restendepunkter
Det er laget tre ulike endepunkter for å sende inn data, ett for utendørsbruk, ett for innendørsbruk og ett for sprøyting på frø og formeringsmateriale. Det er valgt tre ulike endepunkter, sånn at vi kan ha en datamodell for hver type sprøyting.

De ulike endepunktene finnes [her](https://github.com/Mattilsynet/plantevernjournal-innlesing-api/blob/master/src/main/kotlin/no/mattilsynet/plantevernjournal/api/controllers/PlantevernjournalController.kt) eller i [swagger](https://plantevernjournal-innlesing-api.plantevernjournal-dev.mattilsynet.io/swagger-ui/index.html).

### Kartdata
Kartdata er sentralt i bruk av plantevermidler. Vi har valgt å bruke [GeoJSON](https://en.wikipedia.org/wiki/GeoJSON) Nå er det frivillig å sende inn kartdata i feltene for _behandledeOmraader_, men det skal bli obligatorisk på sikt. Det er i dag frivillig, sånn at manglende kartdata ikke skal være et hinder for å teste ut løsningen.  

Gyldige geometrityper er gitt [her](https://github.com/Mattilsynet/plantevernjournal-innlesing-api/blob/master/src/main/kotlin/no/mattilsynet/plantevernjournal/api/shared/GeometriTyper.kt)

I GeoJSON er det mulig å sende inn properties, det bruker vi ikke per i dag.

### BBCH
Det skal angis utviklingsstadium for vekstene som sprøytes. Vi kommer tilbake til akkurat hvordan dette skal angis.

Her er et eksempel på utviklingsstadier for korn (fra Felleskjøpet): https://www.felleskjopet.no/globalassets/planteproduksjon/plantekultur/plantevern/utviklingsstadier-for-korn.pdf, og for potet (fra NLR): https://www.felleskjopet.no/globalassets/planteproduksjon/plantekultur/plantevern/utviklingsstadier-for-korn.pdf

### Eppokoder
_Eppo Global Database_ er en internasjonal database som samler informasjon om planter og plantesykdommer. Denne vedlikeholdes av European and Mediterranean Plant Protection Organization (EPPO). Man kan lese om databasen og hva den inneholder : https://gd.eppo.int/

I Eppodatabasen er det laget et kodeverk for klassifisering av planter, og det skal plantevernjournal-innlesing-api bruke for å angi behandlede vekster. Plantekodeverket kan man finne her: https://gd.eppo.int/taxon/1PLAK

Det finnes flere måter å hente eppokoder for planter på, dokumentasjon til rest-api'et til eppo finnes her: https://data.eppo.int/documentation/rest Det er også mulig å manuelt søke på planter på nettsiden: https://gd.eppo.int/

### Testdata
Det er laget filer med testdata som man kan bruke som utgangspunkt for egen testing, eller for å teste api-endepunktene i swaggerdokumentasjonen. Eksempelfilene ligger her: https://github.com/Mattilsynet/plantevernjournal-innlesing-api/tree/master/src/test/http-requests
Dette er testfiler som vi bruker i utviklingen, så de vil oppdateres forløpende etter som datamodellene og restendepunktene endrer seg.
