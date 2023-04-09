package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.MenuCreationDto;
import io.hauyu07.orderingservice.dto.MenuDto;
import io.hauyu07.orderingservice.dto.MenuListingDto;
import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.mapper.MenuMapper;
import io.hauyu07.orderingservice.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Menu")
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper menuMapper;

    @GetMapping
    @Operation(summary = "List menus of the current user's restaurant")
    public List<MenuListingDto> getRestaurantMenus(
            Principal principal
    ) {
        List<Menu> menus = menuService.getMenusByRestaurantUser(principal.getName());
        return menuMapper.menuListToMenuListingDtoList(menus);
    }

    @GetMapping("/{id}")
    public MenuDto getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        return menuMapper.menuToMenuDto(menu);
    }

    @PostMapping
    @Operation(summary = "Create a menu for the current user's restaurant")
    public ResponseEntity<String> createRestaurantMenu(
            Principal principal,
            @RequestBody MenuCreationDto menuCreationDto
    ) {
        Menu menu = menuMapper.menuCreationDtoToMenu(menuCreationDto);
        menuService.createMenu(principal.getName(), menu);
        return ResponseEntity.ok("Success");
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
