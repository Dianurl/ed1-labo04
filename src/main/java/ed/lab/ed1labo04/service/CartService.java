package ed.lab.ed1labo04.service;

import ed.lab.ed1labo04.Entity.CartEntity;
import ed.lab.ed1labo04.Entity.CartItemEntity;
import ed.lab.ed1labo04.Entity.ProductEntity;
import ed.lab.ed1labo04.model.CartItemRequest;
import ed.lab.ed1labo04.model.CreateCartRequest;
import ed.lab.ed1labo04.repository.CartRepository;
import ed.lab.ed1labo04.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository; // final vuelve global al objeto.
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public CartEntity createCart(CreateCartRequest request) {
        CartEntity cart = new CartEntity();
        List<CartItemEntity> items = new ArrayList<>();
        double TotalPrice = 0;

        for(CartItemRequest itemReq : request.getCartItems()) {
            Optional<ProductEntity> productOp = productRepository.findById(itemReq.getProductId());

            if(itemReq.getQuantity() <= 0)
                throw new IllegalArgumentException("Quantity must be greater than 0");

            if(productOp.isEmpty()) {
                throw new IllegalArgumentException("Product not found");
            }

            ProductEntity product = productOp.get();

            if(product.getQuantity() < itemReq.getQuantity())
                throw new IllegalArgumentException("Not enough inventory for product: " + product.getName());

            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            CartItemEntity item = new CartItemEntity();
            item.setProductId(product.getId());
            item.setName(product.getName());
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(product.getPrice());

            TotalPrice += product.getPrice() * itemReq.getQuantity();
            items.add(item);
        }

        cart.setCartItems(items);
        cart.setTotalPrice(TotalPrice);

        return cartRepository.save(cart);
    }

    public Optional<CartEntity> getCartById(Long id) {
        return cartRepository.findById(id);
    }
}