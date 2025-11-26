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
        froeEllerFormeringsMatrialeDto.behandledeVekster.validateEppokoder()
        natsService.publishJournalForFroeEllerFormeringsmateriale(
            froeEllerFormeringsMatrialeDto.toFroeEllerFormeringsMatriale(),
        )
    }

    fun innendoersBruk(innendoersBrukDto: InnendoersBrukDto) {
        innendoersBrukDto.behandledeVekster.validateEppokoder()
        natsService.publishJournalForInnendoersBruk(
            innendoersBrukDto.toInnendoersBruk(),
        )
    }

    fun utendoersBruk(utendoersBrukDto: UtendoersBrukDto) {
        utendoersBrukDto.behandledeVekster.validateEppokoder()
        natsService.publishJournalForUtendoersBruk(
            utendoersBrukDto.toUtendoersBrukDto(),
        )
    }

    private fun List<BehandletVekstDto>.validateEppokoder() =
        map { it.eppoKode }
            .filter { eppoKode ->
                eppoService.getNavnFraEppoKode(eppoKode = eppoKode) == null
            }.takeIf { it.isNotEmpty() }
            ?.let { eppoKoder ->
                throw NoSuchElementException(
                    eppoKoder.joinToString(", ") + " finnes ikke i eppodatabasen."
                )
            }
}
