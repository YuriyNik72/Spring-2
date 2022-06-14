package ru.geekbrains.rest.entites;

import lombok.Data;
import ru.geekbrains.entites.Product;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products_images")
@Data
public class ProductImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "path")
    private String path;
}
