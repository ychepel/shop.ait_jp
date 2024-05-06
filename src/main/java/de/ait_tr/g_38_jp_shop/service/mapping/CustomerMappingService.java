package de.ait_tr.g_38_jp_shop.service.mapping;

import de.ait_tr.g_38_jp_shop.domain.dto.CustomerDto;
import de.ait_tr.g_38_jp_shop.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMappingService {

    public CustomerDto mapEntityToDto(Customer entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "active", constant = "true")
    public Customer mapDtoToEntity(CustomerDto dto);
}
