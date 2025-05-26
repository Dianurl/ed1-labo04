package ed.lab.ed1labo04.service;

import ed.lab.ed1labo04.Entity.ProductEntity;
import ed.lab.ed1labo04.model.CreateProductRequest;
import ed.lab.ed1labo04.model.UpdateProductRequest;
import org.springframework.stereotype.Service;
import ed.lab.ed1labo04.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity createProduct(CreateProductRequest createProductRequest) {
        if(createProductRequest.getPrice() <= 0)
            throw new IllegalArgumentException("Price must be greater than 0");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(createProductRequest.getName());
        productEntity.setPrice(createProductRequest.getPrice());
        productEntity.setQuantity(0);

        return productRepository.save(productEntity);
    }

    public ProductEntity updateProduct(Long id, UpdateProductRequest updateProductRequest) {
        if(updateProductRequest.getPrice() <= 0)
            throw new IllegalArgumentException("Price must be greater than 0");

        if(updateProductRequest.getQuantity() < 0)
            throw new IllegalArgumentException("Quantity must be greater or equal to 0");

        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        productEntity.setQuantity(updateProductRequest.getQuantity());
        productEntity.setPrice(updateProductRequest.getPrice());

        return productRepository.save(productEntity);
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> getProductById(Long id) {
        return productRepository.findById(id);
    }
}