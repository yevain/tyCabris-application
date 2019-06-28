package fr.tycabris.service.mapper;

import fr.tycabris.domain.*;
import fr.tycabris.service.dto.ParcDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Parc} and its DTO {@link ParcDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParcMapper extends EntityMapper<ParcDTO, Parc> {


    @Mapping(target = "parcChevres", ignore = true)
    @Mapping(target = "removeParcChevre", ignore = true)
    Parc toEntity(ParcDTO parcDTO);

    default Parc fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parc parc = new Parc();
        parc.setId(id);
        return parc;
    }
}
