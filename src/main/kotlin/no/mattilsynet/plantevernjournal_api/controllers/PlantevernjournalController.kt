package no.mattilsynet.plantevernjournal_api.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("plantevernjournal/innlesing/v1")
class PlantevernjournalController {

    @GetMapping
    fun getPlantevernjournal(): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }

    @PostMapping
    fun postPlantevernjournal(): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
}
