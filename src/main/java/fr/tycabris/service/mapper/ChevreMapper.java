package fr.tycabris.service.mapper;

import fr.tycabris.domain.*;
import fr.tycabris.service.dto.ChevreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chevre} and its DTO {@link ChevreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChevreMapper extends EntityMapper<ChevreDTO, Chevre> {

    @Mapping(source = "pere.id", target = "pereId")
    @Mapping(source = "mere.id", target = "mereId")
    ChevreDTO toDto(Chevre chevre);

    @Mapping(source = "pereId", target = "pere")
    @Mapping(source = "mereId", target = "mere")
    @Mapping(target = "poids", ignore = true)
    @Mapping(target = "removePoids", ignore = true)
    @Mapping(target = "tailles", ignore = true)
    @Mapping(target = "removeTaille", ignore = true)
    @Mapping(target = "parcChevres", ignore = true)
    @Mapping(target = "removeParcChevre", ignore = true)
    @Mapping(target = "evenementChevres", ignore = true)
    @Mapping(target = "removeEvenementChevre", ignore = true)
    Chevre toEntity(ChevreDTO chevreDTO);

    default Chevre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chevre chevre = new Chevre();
        chevre.setId(id);
        return chevre;
    }
}
