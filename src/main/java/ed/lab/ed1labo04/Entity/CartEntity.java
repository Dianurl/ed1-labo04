package ed.lab.ed1labo04.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalPrice;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItems;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemEntity> items) {
        this.cartItems = items;
    }

    public void addItem(CartItemEntity cartItemEntity) {
        this.cartItems.add(cartItemEntity);
    }
}