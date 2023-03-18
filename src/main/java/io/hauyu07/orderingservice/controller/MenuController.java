package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MenuController {

    @Autowired
    private MenuService menuService;

//    @GetMapping
//    public Page<Menu> getAllMenus(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return menuService.getAllMenus(page, size);
//    }

    @GetMapping("/menus/{id}")
    public Menu getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @PostMapping("/restaurants/{restaurantId}/menus")
    public Menu createMenu(@PathVariable Long restaurantId, @RequestBody Menu menu) {
        return menuService.createMenu(restaurantId, menu);
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
