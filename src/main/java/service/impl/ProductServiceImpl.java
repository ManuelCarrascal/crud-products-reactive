package service.impl;

import model.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static List<Product> dataProducts= new ArrayList<>(List.of(
            new Product(1L, "Iphone 14", "Electronics", 1500.0, 10),
            new Product(2L, "Iphone 15", "Electronics", 2500.0, 10),
            new Product(3L, "Iphone 16", "Electronics", 3500.0, 10),
            new Product(4L, "Iphone 17", "Electronics", 4500.0, 10)
    ));

    @Override
    public Flux<Product> getProducts() {
        return Flux.fromIterable(dataProducts);
    }

    @Override
    public Flux<Product> getProductsByCategory(String category) {
        return getProducts().filter(product -> product.getCategory().equals(category)); //Flux<Product>
    }

    @Override
    public Mono<Product> getProductById(Long id) {
        return getProducts() //Flux<Product>
                .filter(product ->product.getId().equals(id)) //Flux<Product>
                .next();//Mono<Product>
    }

    @Override
    public Mono<Void> highEndProduct(Product product) {
        return getProductById(product.getId()) //Mono<Product>
                .switchIfEmpty(
                        Mono.just(product)
                                .map(p -> {
                                    dataProducts.add(p);
                                    return p;
                                })) //Mono<Product>
                .then(); //Mono<Void>
    }

    @Override
    public Mono<Product> deleteProduct(Long id) {
        return getProductById(id) //Mono<Product>
                .map(product -> {
                    dataProducts.removeIf(productToDelete -> product.getId().equals(productToDelete.getId()));
                    return product;
                }) ; //Mono<Product>
    }

    @Override
    public Mono<Product> updateProduct(Long id, Double unitPrice) {
        return getProductById(id) //Mono<Product>
                .map(product -> {
                    product.setUnitPrice(unitPrice);
                    return product;
                }); //Mono<Product>
    }
}
