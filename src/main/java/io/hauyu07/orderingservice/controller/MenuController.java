package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.MenuDto;
import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.mapper.MenuMapper;
import io.hauyu07.orderingservice.service.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Menu")
@RestController
@RequestMapping
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper menuMapper;

//    @GetMapping
//    public Page<Menu> getAllMenus(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return menuService.getAllMenus(page, size);
//    }

    @GetMapping("/menus/{id}")
    public MenuDto getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        return menuMapper.menuToMenuDto(menu);
    }

    @PostMapping("/restaurants/{restaurantId}/menus")
    public MenuDto createMenu(@PathVariable Long restaurantId, @RequestBody MenuDto menuDto) {
        Menu menu = menuMapper.menuDtoToMenu(menuDto);
        menuService.createMenu(restaurantId, menu);
        return menuDto;
    }

    @PutMapping("/menus/{id}")
    public Menu updateMenu(@PathVariable Long id, @RequestBody Menu updatedMenu) {
        return menuService.updateMenu(id, updatedMenu);
    }

//    @DeleteMapping("/{id}")
//    public void deleteMenu(@PathVariable Long id) {
//        menuService.deleteMenu(id);
//    }
}
