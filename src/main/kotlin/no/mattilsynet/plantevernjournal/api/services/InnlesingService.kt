package no.mattilsynet.plantevernjournal.api.services

import kotlinx.serialization.ExperimentalSerializationApi
import no.mattilsynet.plantevernjournal.api.controllers.models.BehandletVekstDto
import no.mattilsynet.plantevernjournal.api.controllers.models.FroeEllerFormeringsMatrialeDto
import no.mattilsynet.plantevernjournal.api.controllers.models.InnendoersBrukDto
import no.mattilsynet.plantevernjournal.api.controllers.models.UtendoersBrukDto
import org.springframework.stereotype.Service

@OptIn(ExperimentalSerializationApi::class)
@kotlin.uuid.ExperimentalUuidApi
@Service
class InnlesingService(
    private val eppoService: EppoService,
    private val natsService: NatsService,
) {

    fun froeEllerFormeringsMatriale(froeEllerFormeringsMatrialeDto: FroeEllerFormeringsMatrialeDto) {
        natsService.publishJournalForFroeEllerFormeringsmateriale(
            froeEllerFormeringsMatrialeDto.toFroeEllerFormeringsMatriale(),
        )
        froeEllerFormeringsMatrialeDto.behandledeVekster.validateEppokoder()
    }

    fun innendoersBruk(innendoersBrukDto: InnendoersBrukDto) {
        natsService.publishJournalForInnendoersBruk(
            innendoersBrukDto.toInnendoersBruk(),
        )
        innendoersBrukDto.behandledeVekster.validateEppokoder()
    }

    fun utendoersBruk(utendoersBrukDto: UtendoersBrukDto) {
        natsService.publishJournalForUtendoersBruk(
            utendoersBrukDto.toUtendoersBrukDto(),
        )
        utendoersBrukDto.behandledeVekster.validateEppokoder()
    }

    private fun List<BehandletVekstDto>.validateEppokoder() =
        map { it.eppoKode }
            .filter { eppoKode ->
                eppoService.getNavnFraEppoKode(eppoKode = eppoKode) == null
            }.takeIf { it.isNotEmpty() }
            ?.let { eppoKoder ->
                throw NoSuchElementException(
                    eppoKoder.joinToString(", ") + " finnes ikke i eppodatabasen. " +
                            "Innsendte data er lagret, selv om validering feilet"
                )
            }
}
