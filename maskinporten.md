# Autentisering via Maskinporten med Systembruker

Hvis du er en skifteleverandør, skal du benytte deg av Altinns systembrukerflyt for å sende data til digital plantevernjournal.
Systembrukerflyten betyr (i korte trekk) at en _sluttbruker_ (en organisasjon/bedrift) samtykker til at dere som _sluttbrukersystemleverandør_ sender data på vegne av organisasjonen/bedriften til f.eks. Mattilsynet.

## Oversikt over flyten

Oppsettet er delt i to faser:
- **Engangsoppsett (Steg 0-2)**: Gjøres én gang når du setter opp integrasjonen. Deler av flyten må repeteres mellom Altinn sitt testmiljø og produksjonsmiljø.
- **Per sluttbruker (Steg 3-5)**: Gjøres for hver ny sluttbruker som skal dele data via ditt system, og hver gang du skal hente data.

### Steg 0: Forutsetninger
**⏱️ Engangsoppsett**

For å komme i gang med autentisering via Maskinporten med Systembruker, må du ha følgende forutsetninger:

- Du må ha tilgang til DigDirs [Samarbeidsportal](https://samarbeid.digdir.no/).
- Du må ha registrert en Maskinporten-klient til [Altinns testmiljø](https://sjolvbetjening.test.samarbeid.digdir.no/login). Denne brukes for testing mot Mattilsynets staging-miljø.
- Maskinporten-klienten må ha scopet `mattilsynet:plantevern.journal.innlesing` for å kunne kommunisere med digital plantevernjournal.
- Du må ha registrert deg som sluttbrukersystemleverandør i Altinn via dette [skjemaet](https://forms.office.com/Pages/ResponsePage.aspx?id=D1aOAK8I7EygVrNUR1A5kcdP2Xp78HZOttvolvmHfSJUOFFBMThaOTI1UlVEVU9VM0FaTVZLMzg0Vi4u). Registreringen vil gjelde for Altinns test og produksjonsmiljø.
- En sluttbruker som har samtykket til å dele data via ditt system. Når du tester denne flyten i Altinns testmiljø kan du velge en testbruker som representerer en organisasjon via [Skattetatens tenor test-data](https://www.skatteetaten.no/testdata/).

### Steg 1: Oppsett av Maskinporten-klient med Systembruker
**⏱️ Engangsoppsett**

For å kunne registrere en systembruker må du ha tilgang til følgende scopes for din Maskinporten-klient:
- `altinn:authentication/systemregister.write`
- `altinn:authentication/systemuser.request.read`
- `altinn:authentication/systemuser.request.write`

Hvis du er ferdig med steg 0, vil disse scopene være synlige i Samarbeidsportalen for din Maskinporten-klient.

Scopene gir deg tilgang til følgende:

- `altinn:authentication/systemregister.write` gir deg som sluttbrukersystemleverandør tilgang til å opprette og administrere et system i Altinns systemregister.
- `altinn:authentication/systemuser.request.write` gir deg som sluttbrukersystemleverandør tilgang til å registrere at en sluttbruker samtykker til at data deles via ditt system.
- `altinn:authentication/systemuser.request.read` gir deg som sluttbrukersystemleverandør tilgang til å se om en sluttbruker har samtykket til å dele data via ditt system.

### Steg 2: Registrere Sluttbrukersystem hos Altinn
**⏱️ Engangsoppsett**

Registrering og administrering av ditt sluttbrukersystem hos Altinn skjer via [REST-kall](https://docs.altinn.studio/nb/api/authentication/spec/#/SystemRegister) med et gyldig Maskinporten-token med scope `altinn:authentication/systemregister.write`.

Flyten er dokumentert hos [Altinn - "Metode A: Leverandørstyrt Opprettelse"](https://docs.altinn.studio/nb/authorization/guides/system-vendor/system-user/systemuserrequest/#1-opprette-systembruker-for-eget-system).

Merk at når du oppretter klienten, må du enten basere deg på en rettighet (`rights`) eller tilgangspakke (`accessPackages`) som sluttbrukeren må ha når vedkommende samtykker til å dele data via ditt sluttbrukersystem. Vi har laget en ressurs (`rights`) spesifikt for digital plantevernjournal med id `mat-plantevern-journal` som kan benyttes.

### Steg 3: Registrere samtykke fra sluttbruker inn i Altinns systemregister
**🔄 Per sluttbruker - gjøres én gang per sluttbruker**

Når ditt sluttbrukersystem er registrert og du har lagt inn en rettighet/tilgangspakke, kan du begynne å registrere samtykke fra sluttbruker inn i Altinns systemregister. Dette gjøres med [REST-kall](https://docs.altinn.studio/nb/api/authentication/spec/#/RequestSystemUser) med et Maskinporten-token med scope `altinn:authentication/systemuser.request.write`.

Flyten for å opprette en systembrukerforespørsel er dokumentert hos [Altinn - opprett en standard systembrukerforespørsel](https://docs.altinn.studio/nb/api/authentication/systemuserapi/systemuserrequest/external/#opprett-en-standard-systembrukerforesp%C3%B8rsel).

Merk at rettigheten eller tilgangspakken du baserer deg på her må samsvare med hva du har registrert i det forrige steget når du registrerte ditt sluttbrukersystem.

Før forespørselen om å opprette en systembruker er ferdig, må brukeren bekrefte samtykket. Dette gjøres via en `confirmUrl` som du får tilbake i body fra POST-requesten og som må åpnes av sluttbrukeren i en nettleser.


### Steg 4: Lese status på samtykke fra sluttbruker fra Altinns systemregister
**🔄 Ved hver datautveksling - før du oppretter token**

Før du oppretter et Maskinporten-token, må du verifisere at sluttbrukeren fremdeles har et aktivt samtykke registrert til ditt sluttbrukersystem.
Flyten for å verifisere status er dokumentert hos [Altinn](https://docs.altinn.studio/nb/authorization/guides/system-vendor/system-user/systemuserrequest/#3-verifisering-og-status).


### Steg 5: Utstedelse av systembrukertoken via Maskinporten
**🔄 Ved hver datautveksling - tokenet må fornyes regelmessig**

Når du har fullført alle de nødvendige stegene, kan du begynne å utstede systembrukertoken via Maskinporten.
Flyten er dokumentert hos [Altinn - Bruk av systembruker](https://docs.altinn.studio/nb/authorization/guides/system-vendor/system-user/usetoken/).

Merk at organisasjonsnummer i `systemuser_org` er organisasjonsnummeret til sluttbruker.

Et gyldig systembrukertoken vil inneholde listen `authorization_details` hvor et av innslagene vil være ditt sluttbrukersystem som du registrerte i steg 2.

Husk at systembrukertoken for å kalle på digital plantevernjournal må inneholde scope `mattilsynet:plantevern.journal.innlesing`.

**Viktig**: Systembrukertoken har en begrenset levetid (5 minutter). Du må opprette et nytt token hver gang det utløper.

## Verifisering av flyt mot digital plantevernjournal

Skifteleverandører forventes å levere data til Mattilsynet via denne flyten. Vårt staging-miljø er åpent for alle som har registrert en Maskinporten-klient. Mattilsynet vil verifisere at flyten er satt opp riktig etter at du har sendt inn data. Ta kontakt på _plantevernjournal@mattilsynet.no_ for å få verifisert at flyten er satt opp riktig.

## Trenger du hjelp?

Hvis du har spørsmål eller opplever problemer med denne flyten, ta kontakt på _plantevernjournal@mattilsynet.no_.


