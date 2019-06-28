package fr.tycabris.service.mapper;

import fr.tycabris.domain.*;
import fr.tycabris.service.dto.TailleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Taille} and its DTO {@link TailleDTO}.
 */
@Mapper(componentModel = "spring", uses = {ChevreMapper.class})
public interface TailleMapper extends EntityMapper<TailleDTO, Taille> {

    @Mapping(source = "chevre.id", target = "chevreId")
    @Mapping(source = "chevre.nom", target = "chevreNom")
    TailleDTO toDto(Taille taille);

    @Mapping(source = "chevreId", target = "chevre")
    Taille toEntity(TailleDTO tailleDTO);

    default Taille fromId(Long id) {
        if (id == null) {
            return null;
        }
        Taille taille = new Taille();
        taille.setId(id);
        return taille;
    }
}
