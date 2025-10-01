package no.mattilsynet.plantevernjournal_api.domain

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "Enheter som blir brukt"
)
enum class Enhet(enhet:String) {
    Antall(enhet = "antall"),
    Hektar(enhet = "ha"),
    Kilo(enhet = "kg"),
    KiloPerAntall(enhet = "kg/antall"),
    KiloPerHektar(enhet = "kg/ha"),
    KiloPerKilo(enhet = "kg/kg"),
    KiloPerKvadratmeter(enhet = "kg/m2"),
    KiloPerKubikkmeter(enhet = "kg/m3"),
    KiloPerTonn(enhet = "kg/tonn"),
    Kubikkmeter(enhet = "m3"),
    Kvadratmeter(enhet = "m2"),
    LiterPerAntall(enhet = "l/antall"),
    LiterPerKvadratmeter(enhet = "kg/m2"),
    LiterPerKubikkmeter(enhet = "kg/m3"),
    LiterPerHektar(enhet = "l/ha"),
    LiterPerKilo(enhet = "l/kg"),
    LiterPerTonn(enhet = "l/tonn"),
    Tonn(enhet = "tonn"),
}
