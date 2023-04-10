package io.hauyu07.orderingservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @NotEmpty
    private String name;

    private String description;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MenuCategory> categories;

    public Menu() {
    }

    public Menu(Restaurant restaurant, String name, String description, List<MenuCategory> categories) {
        this.restaurant = restaurant;
        this.name = name;
        this.description = description;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<MenuCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<MenuCategory> categories) {
        this.categories = categories;
    }
}