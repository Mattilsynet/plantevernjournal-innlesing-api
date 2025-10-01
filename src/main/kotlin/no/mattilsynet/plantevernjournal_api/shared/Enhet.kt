package no.mattilsynet.plantevernjournal_api.shared

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "Enheter som blir brukt"
)
enum class Enhet(enhet:String) {
    Antall(enhet = "antall"),
    Hektar(enhet = "ha"),
    KiloPerAntall(enhet = "kg/antall"),
    Kilo(enhet = "kg"),
    KiloPerKilo(enhet = "kg/kg"),
    KiloPerHektar(enhet = "kg/ha"),
    KiloPerKvadratmeter(enhet = "kg/m2"),
    KiloPerKubikkmeter(enhet = "kg/m3"),
    KiloPerTonn(enhet = "kg/tonn"),
    LiterPerKvadratmeter(enhet = "kg/m2"),
    LiterPerKubikkmeter(enhet = "kg/m3"),
    LiterPerAntall(enhet = "l/antall"),
    LiterPerHektar(enhet = "l/ha"),
    LiterPerKilo(enhet = "l/kg"),
    LiterPerTonn(enhet = "l/tonn"),
    Tonn(enhet = "tonn"),
}