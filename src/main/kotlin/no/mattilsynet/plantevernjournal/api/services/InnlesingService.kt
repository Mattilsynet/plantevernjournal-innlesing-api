package no.mattilsynet.plantevernjournal.api.services

import no.mattilsynet.plantevernjournal.api.controllers.models.BehandletVekstDto
import no.mattilsynet.plantevernjournal.api.controllers.models.FroeEllerFormeringsMatrialeDto
import no.mattilsynet.plantevernjournal.api.controllers.models.InnendoersBrukDto
import no.mattilsynet.plantevernjournal.api.controllers.models.UtendoersBrukDto
import no.mattilsynet.plantevernjournal.api.domain.SlettInnsending
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class InnlesingService(
    private val eppoService: EppoService,
    private val natsService: NatsService,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun postFroeEllerFormeringsMateriale(
        froeEllerFormeringsMatrialeDto: FroeEllerFormeringsMatrialeDto,
        innsender: String?,
        paaVegneAv: String?,
    ) =
        froeEllerFormeringsMatrialeDto.behandledeVekster.getEppoKoderOgNavn()
            .let { eppoKoderOgNavn ->
                logger.info("postFroeEllerFormeringsMateriale for innsender orgnr: $innsender")
                froeEllerFormeringsMatrialeDto.toFroeEllerFormeringsMatriale(
                    eppoKoderOgNavn = eppoKoderOgNavn,
                    innsender = innsender,
                    paaVegneAv = paaVegneAv,
                ).let { froeEllerFormeringsMatriale ->
                    natsService.publishJournalForFroeEllerFormeringsmateriale(
                        froeEllerFormeringsMatriale = froeEllerFormeringsMatriale,
                    )

                    logger.info(
                        "postFroeEllerFormeringsMateriale ferdig for innsender orgnr: $innsender" +
                                " og ${froeEllerFormeringsMatriale.id}"
                    )

                    froeEllerFormeringsMatriale.toFroeEllerFormeringsMatrialeResponsDto(
                        behandledeOmraader = froeEllerFormeringsMatrialeDto.behandlingssted,
                        behandledeVekster = froeEllerFormeringsMatrialeDto.behandledeVekster,
                        plantevernmiddel = froeEllerFormeringsMatrialeDto.plantevernmiddel,
                    )
                }
            }

    suspend fun postInnendoersBruk(
        innendoersBrukDto: InnendoersBrukDto,
        innsender: String?,
        paaVegneAv: String?,
    ) =
        innendoersBrukDto.behandledeVekster.getEppoKoderOgNavn()
            .let { eppoKoderOgNavn ->
                logger.info("postInnendoersBruk for innsender orgnr: $innsender")
                innendoersBrukDto.toInnendoersBruk(
                    eppoKoderOgNavn = eppoKoderOgNavn,
                    innsender = innsender,
                    paaVegneAv = paaVegneAv,
                ).let { innendoersBruk ->
                    natsService.publishJournalForInnendoersBruk(
                        innendoersBruk = innendoersBruk,
                    )

                    logger.info(
                        "postInnendoersBruk ferdig for innsender orgnr: $innsender" +
                                " og ${innendoersBruk.id}"
                    )

                    innendoersBruk.toInnendoersBrukResponsDto(
                        behandledeOmraader = innendoersBrukDto.behandlingssted,
                        behandledeVekster = innendoersBrukDto.behandledeVekster,
                        plantevernmiddel = innendoersBrukDto.plantevernmiddel,
                    )
                }
            }

    suspend fun postUtendoersBruk(
        innsender: String?,
        paaVegneAv: String?,
        utendoersBrukDto: UtendoersBrukDto,
    ) =
        utendoersBrukDto.behandledeVekster.getEppoKoderOgNavn()
            .let { eppoKoderOgNavn ->
                logger.info("postUtendoersBruk for innsender orgnr: $innsender")
                utendoersBrukDto.toUtendoersBruk(
                    eppoKoderOgNavn = eppoKoderOgNavn,
                    innsender = innsender,
                    paaVegneAv = paaVegneAv,
                ).let { utendoersBruk ->
                    natsService.publishJournalForUtendoersBruk(
                        utendoersBruk = utendoersBruk,
                    )

                    logger.info(
                        "postUtendoersBruk ferdig for innsender orgnr: $innsender" +
                                " og ${utendoersBruk.id}"
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
        logger.info("deleteUtendoersBruk for innsender: $innsender og id $id")
        natsService.publishSlettJournalForUtendoersBruk(
            SlettInnsending(id = id, innsender = innsender, paaVegneAv = paaVegneAv),
        )
    }

    fun deleteInnendoersBruk(
        id: UUID,
        innsender: String?,
        paaVegneAv: String?,
    ) {
        logger.info("deleteInnendoersBruk for innsender: $innsender og id $id")
        natsService.publishSlettJournalForInnendoersBruk(
            SlettInnsending(id = id, innsender = innsender, paaVegneAv = paaVegneAv)
        )
    }

    fun deleteFroeEllerFormeringsMatriale(
        id: UUID,
        innsender: String?,
        paaVegneAv: String?,
    ) {
        logger.info("deleteFroeEllerFormeringsMatriale for innsender: $innsender og id $id")
        natsService.publishSlettJournalForFroeEllerFormeringsmateriale(
            SlettInnsending(id = id, innsender = innsender, paaVegneAv = paaVegneAv)
        )
    }

    private suspend fun List<BehandletVekstDto>.getEppoKoderOgNavn() =
        map { it.eppoKode to eppoService.getNavnFraEppoKode(eppoKode = it.eppoKode) }
            .also { eppoKoderOgNavn ->
                eppoKoderOgNavn.filter { it.second == null }
                    .takeIf { it.isNotEmpty() }
                    ?.let { eppoKoder ->
                        throw NoSuchElementException(
                            eppoKoder.joinToString(", ") { it.first } + " finnes ikke i eppodatabasen."
                        )
                    }
            }.mapNotNull { (first, second) ->
                second?.let { first to it }
            }
}
