package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.dto.MenuDto;
import io.hauyu07.orderingservice.entity.*;
import io.hauyu07.orderingservice.exception.ResourceNotFoundException;
import io.hauyu07.orderingservice.mapper.MenuMapper;
import io.hauyu07.orderingservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> getMenusByRestaurantUser(String userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return user.getRestaurant().getMenus();
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", id));
    }

    public MenuDto getActiveMenu(String userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Restaurant restaurant = user.getRestaurant();
        Menu menu = menuRepository.findByRestaurantAndIsActive(restaurant, true);
        return menuMapper.menuToMenuDto(menu);
    }

    @Transactional
    public void createMenu(String userId, Menu menu) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Restaurant restaurant = user.getRestaurant();
        menu.setRestaurant(restaurant);
        Menu createdMenu = menuRepository.save(menu);

        List<MenuCategory> menuCategories = menu.getCategories();
        List<MenuItem> menuItemsToCreate = new ArrayList<>();
        for (MenuCategory category : menuCategories) {
            category.setMenu(createdMenu);
            List<MenuItem> menuItems = category.getItems();
            for (MenuItem item : menuItems) {
                item.setCategory(category);
                menuItemsToCreate.add(item);
            }
        }
        menuCategoryRepository.saveAll(menuCategories);
        menuItemRepository.saveAll(menuItemsToCreate);
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
