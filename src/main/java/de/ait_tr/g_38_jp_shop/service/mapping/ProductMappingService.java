package de.ait_tr.g_38_jp_shop.service.mapping;

import de.ait_tr.g_38_jp_shop.domain.dto.ProductDto;
import de.ait_tr.g_38_jp_shop.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMappingService {

    @Mapping(target = "productId", source = "id")
    public ProductDto mapEntityToDto(Product entity);

    @Mapping(target = "id", source = "productId")
    public Product mapDtoToEntity(ProductDto dto);
}
