package no.mattilsynet.plantevernjournal.api.services

import kotlinx.serialization.ExperimentalSerializationApi
import no.mattilsynet.plantevernjournal.api.controllers.models.BehandletVekstDto
import no.mattilsynet.plantevernjournal.api.controllers.models.FroeEllerFormeringsMatrialeDto
import no.mattilsynet.plantevernjournal.api.controllers.models.InnendoersBrukDto
import no.mattilsynet.plantevernjournal.api.controllers.models.UtendoersBrukDto
import no.mattilsynet.plantevernjournal.api.domain.SlettInnsending
import org.springframework.stereotype.Service
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
@kotlin.uuid.ExperimentalUuidApi
@Service
class InnlesingService(
    private val eppoService: EppoService,
    private val natsService: NatsService,
) {

    fun postFroeEllerFormeringsMateriale(
        froeEllerFormeringsMatrialeDto: FroeEllerFormeringsMatrialeDto,
        innsender: String?,
        paaVegneAv: String?,
    ) =
        froeEllerFormeringsMatrialeDto.behandledeVekster.validateEppokoder()
            .run {
                froeEllerFormeringsMatrialeDto.toFroeEllerFormeringsMatriale(
                    innsender = innsender,
                    paaVegneAv = paaVegneAv,
                ).let { froeEllerFormeringsMatriale ->
                    natsService.publishJournalForFroeEllerFormeringsmateriale(
                        froeEllerFormeringsMatriale = froeEllerFormeringsMatriale,
                    )

                    froeEllerFormeringsMatriale.toFroeEllerFormeringsMatrialeResponsDto(
                        behandledeOmraader = froeEllerFormeringsMatrialeDto.behandledeOmraader,
                        behandledeVekster = froeEllerFormeringsMatrialeDto.behandledeVekster,
                        plantevernmiddel = froeEllerFormeringsMatrialeDto.plantevernmiddel,
                    )
                }
            }

    fun postInnendoersBruk(
        innendoersBrukDto: InnendoersBrukDto,
        innsender: String?,
        paaVegneAv: String?,
    ) =
        innendoersBrukDto.behandledeVekster.validateEppokoder()
            .run {
                innendoersBrukDto.toInnendoersBruk(
                    innsender = innsender,
                    paaVegneAv = paaVegneAv,
                ).let { innendoersBruk ->
                    natsService.publishJournalForInnendoersBruk(
                        innendoersBruk = innendoersBruk,
                    )

                    innendoersBruk.toInnendoersBrukResponsDto(
                        behandledeOmraader = innendoersBrukDto.behandledeOmraader,
                        behandledeVekster = innendoersBrukDto.behandledeVekster,
                        plantevernmiddel = innendoersBrukDto.plantevernmiddel,
                    )
                }
            }

    fun postUtendoersBruk(
        innsender: String?,
        paaVegneAv: String?,
        utendoersBrukDto: UtendoersBrukDto,
    ) =
        utendoersBrukDto.behandledeVekster.validateEppokoder()
            .run {
                utendoersBrukDto.toUtendoersBruk(
                    innsender = innsender,
                    paaVegneAv = paaVegneAv,
                )
                    .let { utendoersBruk ->
                        natsService.publishJournalForUtendoersBruk(
                            utendoersBruk = utendoersBruk,
                        )

                        utendoersBruk.toUtendoersBrukResponsDto(
                            behandledeOmraader = utendoersBrukDto.behandledeOmraader,
                            behandledeVekster = utendoersBrukDto.behandledeVekster,
                            plantevernmiddel = utendoersBrukDto.plantevernmiddel,
                        )
                    }
            }

    fun deleteUtendoersBruk(
        id: UUID,
        innsender: String?,
        paaVegneAv: String?,
    ) {
        natsService.publishSlettJournalForUtendoersBruk(
            SlettInnsending(id = id, innsender = innsender, paaVegneAv = paaVegneAv),
        )
    }

    fun deleteInnendoersBruk(
        id: UUID,
        innsender: String?,
        paaVegneAv: String?,
    ) {
        natsService.publishSlettJournalForInnendoersBruk(
            SlettInnsending(id = id, innsender = innsender, paaVegneAv = paaVegneAv)
        )
    }

    fun deleteFroeEllerFormeringsMatriale(
        id: UUID,
        innsender: String?,
        paaVegneAv: String?,
    ) {
        natsService.publishSlettJournalForFroeEllerFormeringsmateriale(
            SlettInnsending(id = id, innsender = innsender, paaVegneAv = paaVegneAv)
        )
    }

    private fun List<BehandletVekstDto>.validateEppokoder() =
        true
    /*     map { it.eppoKode }
             .filter { eppoKode ->
                 eppoService.getNavnFraEppoKode(eppoKode = eppoKode) == null
             }.takeIf { it.isNotEmpty() }
             ?.let { eppoKoder ->
                 throw NoSuchElementException(
                     eppoKoder.joinToString(", ") + " finnes ikke i eppodatabasen."
                 )
             }
 */
}
