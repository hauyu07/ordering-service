package io.hauyu07.orderingservice.controller;

import io.hauyu07.orderingservice.dto.MenuCreationDto;
import io.hauyu07.orderingservice.dto.MenuDto;
import io.hauyu07.orderingservice.dto.MenuListingDto;
import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.mapper.MenuMapper;
import io.hauyu07.orderingservice.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Tag(name = "Menu", description = "Manage menus from a restaurant user's view")
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper menuMapper;

    @GetMapping
    @Operation(summary = "List menus of the current user's restaurant")
    public ResponseEntity<List<MenuListingDto>> getRestaurantMenus(
            Principal principal
    ) {
        List<Menu> menus = menuService.getMenusByRestaurantUser(principal.getName());
        return ResponseEntity.ok(menuMapper.menuListToMenuListingDtoList(menus));
    }

    @GetMapping("/{id}")
    public MenuDto getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        return menuMapper.menuToMenuDto(menu);
    }

    @GetMapping("/active")
    public ResponseEntity<MenuDto> getActiveMenu(Principal principal) {
        return ResponseEntity.ok(menuService.getActiveMenuForRestaurant(principal.getName()));
    }

    @PostMapping
    @Operation(summary = "Create a menu for the current user's restaurant")
    @ApiResponses(value = @ApiResponse(responseCode = "201"))
    public ResponseEntity<String> createRestaurantMenu(
            Principal principal,
            @Valid @RequestBody MenuCreationDto menuCreationDto
    ) {
        Menu menu = menuMapper.menuCreationDtoToMenu(menuCreationDto);
        menuService.createMenu(principal.getName(), menu);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @PutMapping("/{id}")
    @ApiResponses(value = @ApiResponse(responseCode = "204"))
    public ResponseEntity<String> updateMenu(
            @PathVariable Long id,
            @Valid @RequestBody MenuCreationDto menuCreationDto
    ) {
        menuService.updateMenu(id, menuCreationDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok("Success");
    }
}
