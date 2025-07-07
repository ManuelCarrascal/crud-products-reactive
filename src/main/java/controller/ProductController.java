package controller;

import lombok.AllArgsConstructor;
import model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.ProductService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Flux<Product>> getAllProducts() {
        return new ResponseEntity<>( productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{category}")
    public ResponseEntity<Flux<Product>> getProductsByCategory(@PathVariable String category) {
        return new ResponseEntity<>(productService.getProductsByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Mono<Product>> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Mono<Void>> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.highEndProduct(product), HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public Mono<ResponseEntity<Product>> deleteProductById(@PathVariable Long id) {
        return productService.deleteProduct(id)  //Mono<Product>
                .map(product -> new ResponseEntity<>(product,HttpStatus.OK))  //Mono<ResponseEntity<Product>>
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND))); //Mono<ResponseEntity<Product>>
    }

    @PutMapping("/product/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable Long id, @RequestBody Double unitPrice) {
        return productService.updateProduct(id, unitPrice)
                .map(product -> new ResponseEntity<>(product,HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
}
