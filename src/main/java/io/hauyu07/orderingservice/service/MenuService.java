package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.entity.Menu;
import io.hauyu07.orderingservice.entity.MenuCategory;
import io.hauyu07.orderingservice.entity.MenuItem;
import io.hauyu07.orderingservice.entity.Restaurant;
import io.hauyu07.orderingservice.exception.ResourceNotFoundException;
import io.hauyu07.orderingservice.repository.MenuCategoryRepository;
import io.hauyu07.orderingservice.repository.MenuItemRepository;
import io.hauyu07.orderingservice.repository.MenuRepository;
import io.hauyu07.orderingservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuCategoryRepository menuCategoryRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Page<Menu> getAllMenus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return menuRepository.findAll(pageable);
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", id));
    }

    public Menu createMenu(Long restaurantId, Menu menu) {
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));
        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu updateMenu(Long id, Menu updatedMenu) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", id));

        List<MenuCategory> categoriesToDelete = menu.getCategories();
        List<MenuItem> itemsToDelete = new ArrayList<MenuItem>();
        for (MenuCategory category : categoriesToDelete) {
            itemsToDelete.addAll(category.getItems());
        }
        menuItemRepository.deleteAll(itemsToDelete);
        menuCategoryRepository.deleteAll(categoriesToDelete);

        List<MenuCategory> categoriesToCreate = updatedMenu.getCategories();
        List<MenuItem> itemsToCreate = new ArrayList<MenuItem>();
        for (MenuCategory category : categoriesToCreate) {
            category.setMenu(menu);
            List<MenuItem> items = category
                    .getItems()
                    .stream()
                    .map(item -> {
                        item.setCategory(category);
                        return item;
                    })
                    .toList();
            itemsToCreate.addAll(items);
        }
        List<MenuCategory> createdMenuCategories = menuCategoryRepository.saveAll(categoriesToCreate);
        menuItemRepository.saveAll(itemsToCreate);

        menu.setName(updatedMenu.getName());
        menu.setDescription(updatedMenu.getDescription());
        menu.setCategories(createdMenuCategories);

        return menuRepository.save(menu);
    }

    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", id));
        menuRepository.delete(menu);
    }
}
