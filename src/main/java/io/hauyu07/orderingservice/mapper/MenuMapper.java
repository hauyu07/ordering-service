package io.hauyu07.orderingservice.mapper;

import io.hauyu07.orderingservice.dto.MenuDto;
import io.hauyu07.orderingservice.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    
    @Mapping(target = "restaurant", ignore = true)
    Menu menuDtoToMenu(MenuDto menuDto);

    MenuDto menuToMenuDto(Menu menu);
}