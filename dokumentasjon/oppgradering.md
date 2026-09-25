# Oppgradering av biblioteker

api'et bruker dependency locking. Når biblioteker oppgraderes, så må det genereres ny ```gradle.lockfile```. Det gjøres ved å kjøre kommandoen:

```./gradlew dependencies --write-locks```

PR'er fra dependabot oppdaterer ```gradle.lockfile```, men dersom man oppgraderer og lager egen PR, så må man lage ny versjon av ```gradle.lockfile``` og pushe denne til github.

Informasjon om [Locking Versions](https://docs.gradle.org/current/userguide/dependency_locking.html)