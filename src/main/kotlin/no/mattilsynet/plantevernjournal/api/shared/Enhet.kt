package no.mattilsynet.plantevernjournal.api.shared

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "Enheter som blir brukt",
)
enum class Enhet(enhet:String) {
    ANTALL(enhet = "antall"),
    DEKTAR(enhet = "daa"),
    KILO(enhet = "kg"),
    KILO_PER_ANTALL(enhet = "kg/antall"),
    KILO_PER_DEKTAR(enhet = "kg/daa"),
    KILO_PER_KILO(enhet = "kg/kg"),
    KILO_PER_KVADRATMETER(enhet = "kg/m2"),
    KILO_PER_KUBIKKMETER(enhet = "kg/m3"),
    KILO_PER_TONN(enhet = "kg/tonn"),
    KUBIKKMETER(enhet = "m3"),
    KVADRATMETER(enhet = "m2"),
    LITER_PER_ANTALL(enhet = "l/antall"),
    LITER_PER_HEKTAR(enhet = "l/ha"),
    LITER_PER_KILO(enhet = "l/kg"),
    LITER_PER_KVADRATMETER(enhet = "kg/m2"),
    LITER_PER_KUBIKKMETER(enhet = "kg/m3"),
    LITER_PER_TONN(enhet = "l/tonn"),
    TONN(enhet = "tonn"),
}
