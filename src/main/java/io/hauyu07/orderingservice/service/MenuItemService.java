package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.entity.MenuCategory;
import io.hauyu07.orderingservice.entity.MenuItem;
import io.hauyu07.orderingservice.exception.ResourceNotFoundException;
import io.hauyu07.orderingservice.repository.MenuCategoryRepository;
import io.hauyu07.orderingservice.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuCategoryRepository menuCategoryRepository;

    public MenuItem createMenuItem(Long menuCategoryId, MenuItem menuItem) {
        MenuCategory menuCategory = menuCategoryRepository
                .findById(menuCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", "id", menuCategoryId));
        menuItem.setCategory(menuCategory);
        return menuItemRepository.save(menuItem);
    }
}