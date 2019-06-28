package fr.tycabris.service.mapper;

import fr.tycabris.domain.*;
import fr.tycabris.service.dto.EvenementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Evenement} and its DTO {@link EvenementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EvenementMapper extends EntityMapper<EvenementDTO, Evenement> {

    @Mapping(source = "evenement.id", target = "evenementId")
    @Mapping(source = "evenement.nom", target = "evenementNom")
    EvenementDTO toDto(Evenement evenement);

    @Mapping(target = "suivants", ignore = true)
    @Mapping(target = "removeSuivant", ignore = true)
    @Mapping(target = "evenementChevres", ignore = true)
    @Mapping(target = "removeEvenementChevre", ignore = true)
    @Mapping(source = "evenementId", target = "evenement")
    Evenement toEntity(EvenementDTO evenementDTO);

    default Evenement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Evenement evenement = new Evenement();
        evenement.setId(id);
        return evenement;
    }
}
