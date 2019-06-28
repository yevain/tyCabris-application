package fr.tycabris.service.mapper;

import fr.tycabris.domain.*;
import fr.tycabris.service.dto.ParcChevreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParcChevre} and its DTO {@link ParcChevreDTO}.
 */
@Mapper(componentModel = "spring", uses = {ParcMapper.class, ChevreMapper.class})
public interface ParcChevreMapper extends EntityMapper<ParcChevreDTO, ParcChevre> {

    @Mapping(source = "parc.id", target = "parcId")
    @Mapping(source = "parc.nom", target = "parcNom")
    @Mapping(source = "chevre.id", target = "chevreId")
    @Mapping(source = "chevre.nom", target = "chevreNom")
    ParcChevreDTO toDto(ParcChevre parcChevre);

    @Mapping(source = "parcId", target = "parc")
    @Mapping(source = "chevreId", target = "chevre")
    ParcChevre toEntity(ParcChevreDTO parcChevreDTO);

    default ParcChevre fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParcChevre parcChevre = new ParcChevre();
        parcChevre.setId(id);
        return parcChevre;
    }
}
