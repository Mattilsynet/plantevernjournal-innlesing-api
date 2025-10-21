# Elektronisk journalføring av plantevernmidler

Mattilsynet skal samle inn data om bruk av plantevernmidler i landbruket i Norge. plantevernjournal-innlesing-api inneholder datamodellene som skal brukes når man sender inn data til Mattilsynet. Dette er en veldig tidlig versjon av disse modellene, som er under utvikling, og det vil bli endringer.

EU-kravet som skal implementeres er beskrevet her: https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32023R0564

Dette er pågående arbeid, så det er fortsatt mye som ikke er avklart. Mer informasjon vil komme senere, som f.eks. identifisering av brukere, kartdata, hvordan og hvor ofte data skal rapporteres til Mattilsynet.

Input til endepunktene vil ikke versjoneres i starten, og man må anta at det vil komme endringer som gjør at ting brekker.

Link til api-dokumentasjon: https://plantevernjournal-innlesing-api.plantevernjournal-dev.mattilsynet.io/swagger-ui/index.html

### Datamodeller
Det er laget tre ulike datamodeller for bruk av plantevernmidler, en for innendørs bruk, en for utendørs bruk og en for bruk på frø eller annet formeringsmateriale. En del av dataene som skal samles inn er felles, men modellene har også noen egne egenskaper som gjør at det er valgt å dele de inn, i stedet for å ha en felles datamodell.

### Kartdata
Kartdata er sentralt i bruk av plantevermidler. Det er ikke tatt stilling til formatet på disse dataene, så det vil komme etter hvert.

### BBCH
Det skal angis utviklingsstadium for vekstene som sprøytes. Vi kommer tilbake til akkurat hvordan dette skal angis.

Her er et eksempel på utviklingsstadier for korn (fra Felleskjøpet): https://www.felleskjopet.no/globalassets/planteproduksjon/plantekultur/plantevern/utviklingsstadier-for-korn.pdf, og for potet (fra NLR): https://www.felleskjopet.no/globalassets/planteproduksjon/plantekultur/plantevern/utviklingsstadier-for-korn.pdf

### Eppokoder
_Eppo Global Database_ er en internasjonal database som samler informasjon om planter og plantesykdommer. Denne vedlikeholdes av European and Mediterranean Plant Protection Organization (EPPO). Man kan lese om databasen og hva den inneholder : https://gd.eppo.int/

I Eppodatabasen er det laget et kodeverk for klassifisering av planter, og det skal plantevernjournal-innlesing-api bruke for å angi behandlede vekster. Plantekodeverket kan man finne her: https://gd.eppo.int/taxon/1PLAK

Det finnes flere måter å hente eppokoder for planter på, dokumentasjon til rest-api'et til eppo finnes her: https://data.eppo.int/documentation/rest Det er også mulig å manuelt søke på planter på nettsiden: https://gd.eppo.int/

