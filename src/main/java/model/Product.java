package model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Product {
    private Long id;
    private String name;
    private String category;
    private Double unitPrice;
    private Integer stock;
}
