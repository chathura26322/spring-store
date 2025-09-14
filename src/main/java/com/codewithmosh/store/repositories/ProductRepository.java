package com.codewithmosh.store.repositories;

import com.codewithmosh.store.dtos.ProductSummary;
import com.codewithmosh.store.dtos.ProductSummaryDTO;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> ,ProductCriteriaRepository {
    //String
    List<Product> findByName(String name);
    List<Product> findByNameLike(String name);
    List<Product> findByNameNotLike(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByNameStartingWith(String name);
    List<Product> findByNameEndingWith(String name);
    List<Product> findByNameIsEndingWithIgnoreCase(String name);

    //Numbers
    List<Product> findByPrice(BigDecimal price);
    List<Product> findByPriceGreaterThan(BigDecimal price);
    List<Product> findByPriceLessThan(BigDecimal price);
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);
    List<Product> findByPriceLessThanEqual(BigDecimal price);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    //null
    List<Product> findByDescriptionNull();
//    List<Product> findByDescriptionNotNull(String description);

    //Multiple conditions
    List<Product> findByDescriptionNullAndNameNull();

    //sort
    List<Product> findByNameOrderByPrice(String name);

    //Limit
    List<Product> findTop5ByNameOrderByPrice(String name);
    List<Product> findTop5ByNameLikeOrderByPriceDesc(String name);

    //sql or jpql
    @Procedure("findProductsByPrice")
    List<Product> findProducts( BigDecimal min, BigDecimal max);


    @Query("select count(*) from Product p where p.price between :min and :max order by p.name")
    long countProduct(@Param("min")  BigDecimal min, @Param("max") BigDecimal max);

    @Modifying
    @Query("update Product p set p.price = :newPrice where p.category.id = :category")
    void updatePriceByCategory(BigDecimal newPrice, Byte categoryId);

    @Query("select new com.codewithmosh.store.dtos.ProductSummaryDTO(p.id, p.name)   from Product p where p.category = :categoryId")
    List<ProductSummaryDTO> findByCategory(@Param("categoryId") Category categoryId);

}
