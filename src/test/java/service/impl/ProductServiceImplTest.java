package service.impl;

import model.Product;
import org.junit.jupiter.api.*;
import reactor.test.StepVerifier;

import java.util.Objects;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl();
    }

    @Test
    @Order(1)
    void getProducts_ShouldReturnAllProducts() {
        StepVerifier.create(productService.getProducts())
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    @Order(2)
    void getProductsByCategory_ShouldReturnElectronicsProducts() {
        StepVerifier.create(productService.getProductsByCategory("Electronics"))
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    @Order(3)
    void getProductsByCategory_ShouldReturnEmptyForNonExistentCategory() {
        StepVerifier.create(productService.getProductsByCategory("NonExistent"))
                .verifyComplete();
    }

    @Test
    @Order(4)
    void getProductById_ShouldReturnProduct() {
        StepVerifier.create(productService.getProductById(1L))
                .expectNextMatches(product -> product.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    @Order(5)
    void getProductById_ShouldReturnEmptyForNonExistentId() {
        StepVerifier.create(productService.getProductById(999L))
                .verifyComplete();
    }

    @Test
    @Order(6)
    void highEndProduct_ShouldAddNewProduct() {
        Product newProduct = new Product(5L, "Iphone 18", "Electronics", 5500.0, 5);
        
        StepVerifier.create(productService.highEndProduct(newProduct))
                .verifyComplete();
        
        StepVerifier.create(productService.getProductById(5L))
                .expectNextMatches(product -> "Iphone 18".equals(product.getName()))
                .verifyComplete();
    }

    @Test
    @Order(7)
    void highEndProduct_ShouldNotAddExistingProduct() {
        int initialCount = Objects.requireNonNull(productService.getProducts().collectList().block()).size();
        Product existingProduct = new Product(1L, "Existing", "Electronics", 1000.0, 5);

        StepVerifier.create(productService.highEndProduct(existingProduct))
                .verifyComplete();

        int finalCount = Objects.requireNonNull(productService.getProducts().collectList().block()).size();
        assert initialCount == finalCount;
    }

    @Test
    @Order(10)
    void deleteProduct_ShouldRemoveProduct() {
        StepVerifier.create(productService.deleteProduct(2L))
                .expectNextMatches(product -> product.getId().equals(2L))
                .verifyComplete();
        
        StepVerifier.create(productService.getProductById(2L))
                .verifyComplete();
    }

    @Test
    @Order(11)
    void deleteProduct_ShouldReturnEmptyForNonExistentId() {
        StepVerifier.create(productService.deleteProduct(999L))
                .verifyComplete();
    }

    @Test
    @Order(8)
    void updateProduct_ShouldUpdatePrice() {
        StepVerifier.create(productService.updateProduct(1L, 2000.0))
                .expectNextMatches(product -> product.getUnitPrice().equals(2000.0))
                .verifyComplete();
    }

    @Test
    @Order(9)
    void updateProduct_ShouldReturnEmptyForNonExistentId() {
        StepVerifier.create(productService.updateProduct(999L, 2000.0))
                .verifyComplete();
    }
}