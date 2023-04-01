package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.MenuCreationDto;
import io.hauyu07.orderingservice.dto.MenuDto;
import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.mapper.MenuMapper;
import io.hauyu07.orderingservice.service.MenuCategoryService;
import io.hauyu07.orderingservice.service.MenuItemService;
import io.hauyu07.orderingservice.service.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Menu")
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuCategoryService menuCategoryService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuMapper menuMapper;

//    @GetMapping
//    public Page<Menu> getAllMenus(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return menuService.getAllMenus(page, size);
//    }

    @GetMapping("/{id}")
    public MenuDto getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        return menuMapper.menuToMenuDto(menu);
    }

    @PutMapping("/{id}")
    public MenuDto updateMenu(@PathVariable Long id, @RequestBody MenuCreationDto menuCreationDto) {
        Menu menu = menuMapper.menuCreationDtoToMenu(menuCreationDto);
        return menuMapper.menuToMenuDto(menuService.updateMenu(id, menu));
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
    }
}
