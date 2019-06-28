package fr.tycabris.service.mapper;

import fr.tycabris.domain.*;
import fr.tycabris.service.dto.EvenementChevreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvenementChevre} and its DTO {@link EvenementChevreDTO}.
 */
@Mapper(componentModel = "spring", uses = {EvenementMapper.class, ChevreMapper.class})
public interface EvenementChevreMapper extends EntityMapper<EvenementChevreDTO, EvenementChevre> {

    @Mapping(source = "evenement.id", target = "evenementId")
    @Mapping(source = "evenement.nom", target = "evenementNom")
    @Mapping(source = "chevre.id", target = "chevreId")
    @Mapping(source = "chevre.nom", target = "chevreNom")
    EvenementChevreDTO toDto(EvenementChevre evenementChevre);

    @Mapping(source = "evenementId", target = "evenement")
    @Mapping(source = "chevreId", target = "chevre")
    EvenementChevre toEntity(EvenementChevreDTO evenementChevreDTO);

    default EvenementChevre fromId(Long id) {
        if (id == null) {
            return null;
        }
        EvenementChevre evenementChevre = new EvenementChevre();
        evenementChevre.setId(id);
        return evenementChevre;
    }
}
