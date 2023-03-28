package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.MenuDto;
import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.mapper.MenuMapper;
import io.hauyu07.orderingservice.service.MenuCategoryService;
import io.hauyu07.orderingservice.service.MenuItemService;
import io.hauyu07.orderingservice.service.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Menu")
@RestController
@RequestMapping
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

    @GetMapping("/menus/{id}")
    public MenuDto getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        return menuMapper.menuToMenuDto(menu);
    }

//    @PutMapping("/menus/{id}")
//    public Menu updateMenu(@PathVariable Long id, @RequestBody Menu updatedMenu) {
//        return menuService.updateMenu(id, updatedMenu);
//    }

//    @DeleteMapping("/{id}")
//    public void deleteMenu(@PathVariable Long id) {
//        menuService.deleteMenu(id);
//    }
}
