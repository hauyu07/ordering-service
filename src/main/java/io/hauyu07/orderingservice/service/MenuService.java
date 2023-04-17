package io.hauyu07.orderingservice.service;

import io.hauyu07.orderingservice.dto.MenuCategoryCreationDto;
import io.hauyu07.orderingservice.dto.MenuCreationDto;
import io.hauyu07.orderingservice.dto.MenuDto;
import io.hauyu07.orderingservice.dto.MenuItemCreationDto;
import io.hauyu07.orderingservice.entity.*;
import io.hauyu07.orderingservice.exception.ResourceNotFoundException;
import io.hauyu07.orderingservice.mapper.MenuMapper;
import io.hauyu07.orderingservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private CustomerRepository customerRepository;

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

    public MenuDto getActiveMenuForRestaurant(String userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Restaurant restaurant = user.getRestaurant();
        Menu menu = menuRepository.findByRestaurantAndIsActive(restaurant, true);
        return menuMapper.menuToMenuDto(menu);
    }

    public MenuDto getActiveMenuForCustomer(UUID customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        Menu menu = menuRepository.findByRestaurantAndIsActive(customer.getRestaurant(), true);
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
    public void updateMenu(Long id, MenuCreationDto menuUpdateDto) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", id));

        List<MenuCategory> currentMenuCategories = menu.getCategories();
        List<MenuItem> currentMenuItems = new ArrayList<>();
        for (MenuCategory menuCategory : currentMenuCategories) {
            currentMenuItems.addAll(menuCategory.getItems());
        }

        List<MenuCategoryCreationDto> menuCategoryCreationDtos = menuUpdateDto.getCategories();
        List<MenuCategory> newMenuCategories = new ArrayList<>();
        List<MenuItem> newMenuItems = new ArrayList<>();

        for (MenuCategoryCreationDto menuCategoryCreationDto : menuCategoryCreationDtos) {
            MenuCategory menuCategory = menuMapper.menuCategoryCreationDtoToMenuCategory(menuCategoryCreationDto);
            menuCategory.setMenu(menu);
            newMenuCategories.add(menuCategory);
            List<MenuItemCreationDto> menuItemCreationDtos = menuCategoryCreationDto.getItems();
            for (MenuItemCreationDto menuItemCreationDto : menuItemCreationDtos) {
                MenuItem menuItem = menuMapper.menuItemCreationDtoToMenuItem(menuItemCreationDto);
                menuItem.setCategory(menuCategory);
                newMenuItems.add(menuItem);
            }
        }

        menuItemRepository.deleteAll(currentMenuItems);
        menuCategoryRepository.deleteAll(currentMenuCategories);
        menuCategoryRepository.saveAll(newMenuCategories);
        menuItemRepository.saveAll(newMenuItems);

        menu.setName(menuUpdateDto.getName());
        menu.setDescription(menuUpdateDto.getDescription());
        menu.setActive(menuUpdateDto.getActive());
        menuRepository.save(menu);
    }

    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", id));
        menuRepository.delete(menu);
    }
}
