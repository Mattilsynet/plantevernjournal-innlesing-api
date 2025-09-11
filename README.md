# plantevernjournal-api
Elektronisk journalføring av plantevernmidler

Mattilsynet skal samle inn data om bruk av plantevernmidler i landbruket i Norge. plantevernjournal-api inneholder datamodellene som skal brukes når man sender inn data til Mattilsynet. Dette er en veldig tidlig versjon av disse modellene, som er under utvikling, og det vil bli endringer.

Det er laget tre ulike datamodeller for bruk av plantevernmidler, en for innendørs bruk, en for utendørs bruk og en for bruk på frø eller annet formeringsmateriale. En del av dataene som skal samles inn er felles, men modellene har også noen egne egenskaper, som gjør at det er valgt å dele de inn, i stedet for å ha en felles datamodell.

Kartdata er sentralt i bruk av plantevermidler. Det er ikke tatt stilling til formatet på disse dataene, så det vil komme etter hvert.

Behandlede vekster skal angis med en eppokode. Eppokoder er en internasjonal standard som man kan lese mer om her: https://gd.eppo.int/ Det finnes flere måter å hente eppokoder for planter på, dokumentasjon til rest-api'et til eppo finnes her: https://data.eppo.int/documentation/rest Det er også mulig å manuelt søke på planter på nettsiden.

Dette er pågående arbeid, så det er fortsatt mye som ikke er avklart. Mer informasjon vil komme senere, som f.eks. identifisering av brukere, kartdata, hvordan og hvor ofte data skal rapporteres til Mattilsynet.