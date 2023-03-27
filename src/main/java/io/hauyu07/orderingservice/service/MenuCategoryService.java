package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.entity.MenuCategory;
import io.hauyu07.orderingservice.exception.ResourceNotFoundException;
import io.hauyu07.orderingservice.repository.MenuCategoryRepository;
import io.hauyu07.orderingservice.repository.MenuRepository;
import io.hauyu07.orderingservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MenuCategoryService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuCategoryRepository menuCategoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Page<MenuCategory> getAllMenuCategorys(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return menuCategoryRepository.findAll(pageable);
    }

    public MenuCategory getMenuCategoryById(Long id) {
        return menuCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", "id", id));
    }

    public MenuCategory createMenuCategory(Long menuId, MenuCategory menuCategory) {
        Menu menu = menuRepository
                .findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", menuId));
        menuCategory.setMenu(menu);
        return menuCategoryRepository.save(menuCategory);
    }

    public MenuCategory updateMenuCategory(Long id, MenuCategory updatedMenuCategory) {
        MenuCategory menu = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", "id", id));
        menu.setName(updatedMenuCategory.getName());
        return menuCategoryRepository.save(menu);
    }

    public void deleteMenuCategory(Long id) {
        MenuCategory menu = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", "id", id));
        menuCategoryRepository.delete(menu);
    }
}
