package fr.tycabris.service.mapper;

import fr.tycabris.domain.*;
import fr.tycabris.service.dto.PoidsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Poids} and its DTO {@link PoidsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ChevreMapper.class})
public interface PoidsMapper extends EntityMapper<PoidsDTO, Poids> {

    @Mapping(source = "chevre.id", target = "chevreId")
    @Mapping(source = "chevre.nom", target = "chevreNom")
    PoidsDTO toDto(Poids poids);

    @Mapping(source = "chevreId", target = "chevre")
    Poids toEntity(PoidsDTO poidsDTO);

    default Poids fromId(Long id) {
        if (id == null) {
            return null;
        }
        Poids poids = new Poids();
        poids.setId(id);
        return poids;
    }
}
