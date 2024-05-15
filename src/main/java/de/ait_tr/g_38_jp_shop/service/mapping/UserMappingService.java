package de.ait_tr.g_38_jp_shop.service.mapping;

import de.ait_tr.g_38_jp_shop.domain.dto.UserDto;
import de.ait_tr.g_38_jp_shop.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMappingService {

    @Mapping(target = "password", ignore = true)
    public UserDto mapEntityToDto(User entity);

    public User mapDtoToEntity(UserDto dto);
}
