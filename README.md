# Elektronisk journalføring av plantevernmidler

Mattilsynet skal samle inn data om bruk av plantevernmidler i landbruket i Norge. Team Planter er et eget produktteam som driver utviklingen av et system for dette. Vi nås på epost _plantevernjournal@mattilsynet.no_ hvis dere vil kontakte oss.

plantevernjournal-innlesing-api inneholder datamodellene som skal brukes når man sender inn data til Mattilsynet. Dette er en veldig tidlig versjon av disse modellene, som er under utvikling, og det vil bli endringer.

EU-kravet som skal implementeres er beskrevet her: https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32023R0564

Dette er pågående arbeid, så det er fortsatt mye som ikke er avklart. Mer informasjon vil komme senere, som f.eks. identifisering av brukere, hvordan og hvor ofte data skal rapporteres til Mattilsynet.

Input til rest-endepunktene vil ikke versjoneres i starten, og man må anta at det vil komme endringer som gjør at ting brekker.

Mange datafelter er frivillige til å begynne med, men vil bli obligatoriske etter hvert. Det er noen av datafeltene vi er litt usikre på hva som kreves, og det blir endringer utover i utviklingsløpet. Det gjelder særlig det med definisjon av steder uten kart. Det er ikke avgjort hva som skal ha presedens dersom man sender inn kartdata som ikke stemmer overens med areal.

Link til api-dokumentasjon i dev: https://plantevernjournal-innlesing-api.plantevernjournal-dev.mattilsynet.io/swagger-ui/index.html


### Miljøer
Det skal være tre ulike miljøer:

#### dev:
Brukes for testing av innsending av data, uten autentisering. Data som sendes inn er ikke tenkt som reelle data, de regnes kun som test. Dataene vil slettes med ujevne mellomrom. Miljøet er satt opp med testformål, for å se hvordan datamodellene virker og for å komme i gang så vi kan få tilbakemeldinger fra de som kobler seg på.

#### staging:
Brukes for testing av innsending av data, med autentisering

#### produksjon:
Ikke klart enda 


### Maskinporten
Maskinporten skal brukes til autentisering når man sender inn data. For å starte opp med maskinporten går man via Samarbeidsportalen. På [Selvbetjening av Maskinporten via Samarbeidsportalen](https://docs.digdir.no/docs/Maskinporten/maskinporten_sjolvbetjening_web.html) [Selvbetjening som API-konsument](https://docs.digdir.no/docs/Maskinporten/maskinporten_sjolvbetjening_web.html#selvbetjening-som-api-konsument) finnes det informasjon om framgangsmåte.

For å sende inn data til digital plantevernjournal, så er det scope _Mattilsynet:plantevern.journal.innlesing_

Ta kontakt på _plantevernjournal@mattilsynet.no_ hvis det er noe dere trenger hjelp med.

Dev-miljøet ligger åpent, så her kan man teste innsending av data uten å sette opp maskinporten. Vi har laget et stagingmiljø som krever autentisering, for at man skal kunne teste at oppsettet med maskinporten er riktig.
Link til api-dokumentasjon i staging: https://plantevernjournal-innlesing-api.plantevernjournal-staging.mattilsynet.io/swagger-ui/index.html


### Datamodeller
Det er laget tre ulike datamodeller for bruk av plantevernmidler, en for [innendørs bruk](https://github.com/Mattilsynet/plantevernjournal-innlesing-api/blob/master/src/main/kotlin/no/mattilsynet/plantevernjournal/api/controllers/models/InnendoersBrukDto.kt), en for [utendørs bruk](https://github.com/Mattilsynet/plantevernjournal-innlesing-api/blob/master/src/main/kotlin/no/mattilsynet/plantevernjournal/api/controllers/models/UtendoersBrukDto.kt) og en for bruk på [frø eller annet formeringsmateriale](https://github.com/Mattilsynet/plantevernjournal-innlesing-api/blob/master/src/main/kotlin/no/mattilsynet/plantevernjournal/api/controllers/models/FroeEllerFormeringsMatrialeDto.kt). En del av dataene som skal samles inn er felles, men modellene har også noen egne egenskaper som gjør at det er valgt å dele de inn, i stedet for å ha en felles datamodell.


### Rest-endepunkter
Det er laget tre ulike rest-endepunkter for å sende inn data, ett for utendørsbruk, ett for innendørsbruk og ett for sprøyting på frø og formeringsmateriale. Det er valgt tre ulike rest-endepunkter, sånn at vi kan ha en datamodell for hver type sprøyting.

Det finnes også rest-endepunkter for å hente ut kodeverk.

De ulike rest-endepunktene finnes i [github](https://github.com/Mattilsynet/plantevernjournal-innlesing-api/blob/master/src/main/kotlin/no/mattilsynet/plantevernjournal/api/controllers/PlantevernjournalController.kt) eller i [swagger](https://plantevernjournal-innlesing-api.plantevernjournal-dev.mattilsynet.io/swagger-ui/index.html).


### Kartdata
Kartdata er sentralt i bruk av digital journalføring av plantevermidler. Vi har valgt å bruke [GeoJSON](https://en.wikipedia.org/wiki/GeoJSON). Nå er det frivillig å sende inn kartdata i feltene for _behandledeOmraader_, men det skal bli obligatorisk på sikt. Det er i dag frivillig, sånn at manglende kartdata ikke skal være et hinder for å teste ut løsningen.  

Gyldige geometrityper er gitt [her](https://github.com/Mattilsynet/plantevernjournal-innlesing-api/blob/master/src/main/kotlin/no/mattilsynet/plantevernjournal/api/shared/kodeverk/GeometriTyper.kt).

I GeoJSON er det mulig å sende inn properties, det bruker vi ikke per i dag.


### BBCH/vekststadier
Det skal angis vekststadium for vekstene som sprøytes, og til det skal det brukes BBCH-skala. [Her](https://en.wikipedia.org/wiki/BBCH-scale) kan man lese litt om hva dette betyr. Vi kommer tilbake til akkurat hvordan dette skal angis.

Det vil bli obligatorisk å sende inn vekststadium på sikt.

Her er et eksempel på utviklingsstadier for [korn](https://www.felleskjopet.no/globalassets/planteproduksjon/plantekultur/plantevern/utviklingsstadier-for-korn.pdf) (fra Felleskjøpet), og for [potet](https://potet.no/kunnskap/fagartikler/potet/potet/bbch-skala-for-potet) (fra Fagforum Potet).


### Eppokoder
_Eppo Global Database_ er en internasjonal database som samler informasjon om planter og plantesykdommer. Denne vedlikeholdes av European and Mediterranean Plant Protection Organization (EPPO). Man kan lese om databasen og hva den inneholder: https://gd.eppo.int/

I Eppodatabasen er det laget et kodeverk for klassifisering av planter, og det skal plantevernjournal-innlesing-api bruke for å angi behandlede vekster. Plantekodeverket kan man finne her: https://gd.eppo.int/taxon/1PLAK

Det finnes flere måter å hente eppokoder for planter på, dokumentasjon til rest-api'et til eppo finnes her: https://data.eppo.int/documentation/rest Det er også mulig å manuelt søke på planter på nettsiden: https://gd.eppo.int/

Det valideres om det er gyldige eppokoder som sendes inn, og det returneres feil dersom en eller flere eppokoder ikke finnes. Dette er bare testdata, så inntil videre er det ikke viktig hvilken eppokode som brukes.

Det finnes ikke eppokoder for alle plantearter, så det kan være tilfeller hvor man ikke klarer å angi eksakt hva som er sprøytet på. Da skal den nærmeste eppokoden i hierarkiet sendes inn, og så angis sort i feltet for 'sort'.


### Testdata
Det er laget filer med testdata som man kan bruke som utgangspunkt for egen testing, eller for å teste rest-endepunktene i swaggerdokumentasjonen. Eksempelfilene ligger [her](https://github.com/Mattilsynet/plantevernjournal-innlesing-api/tree/master/src/test/http-requests).
Dette er testfiler som vi bruker i utviklingen, så de vil oppdateres forløpende etter som datamodellene og rest-endepunktene endrer seg.
