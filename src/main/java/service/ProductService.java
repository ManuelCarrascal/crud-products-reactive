package service;

import model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService
{
    Flux<Product> getProducts();
    Flux<Product> getProductsByCategory(String category);
    Mono<Product> getProductById(Long id);
    Mono<Void> highEndProduct(Product product);
    Mono<Product> deleteProduct(Long id);
    Mono<Product> updateProduct(Long id, Double unitPrice);
}
