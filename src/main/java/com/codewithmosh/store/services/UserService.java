package com.codewithmosh.store.services;

import com.codewithmosh.store.entities.*;
import com.codewithmosh.store.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;
    private final EntityManager entityManager;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void showEntityStates(){
        var user = User.builder()
                .name("John")
                .email("john@gmail.com")
                .password("password")
                .build();

        if(entityManager.contains(user))
            System.out.println("Persistent");
        else
            System.out.println("Transient / Detached");

        userRepository.save(user);

        if(entityManager.contains(user))
            System.out.println("Persistent");
        else
            System.out.println("Transient / Detached");
    }

    @Transactional
    public void showRelatedEntities(){
        var profile = profileRepository.findById(2L).orElseThrow();
        System.out.println(profile.getUser().getEmail());
    }

    @Transactional
    public void showAddresses(){
        var address = addressRepository.findById(1L).orElseThrow();
    }

    public void persistRelated(){
        var user = User.builder()
                .name("John")
                .email("john@email.com")
                .password("password")
                .build();

        var address = Address.builder()
                .street("123 Main St")
                .city("Main St")
                .zip("12345")
                .state("Main St")
                .build();

        user.addAddress(address);

        userRepository.save(user);
    }

    @Transactional
    public void deleteRelated(){
        var user =userRepository.findById(2L).orElseThrow();
        var address = addressRepository.findById(2L).orElseThrow();
        user.removeAddress(address);
        userRepository.save(user);
    }

    @Transactional
    public void manageProducts(){
       profileRepository.deleteById(1L);
    }

    @Transactional
    public void updateProductPrices(){
        productRepository.updatePriceByCategory(BigDecimal.valueOf(10),(byte) 1);
    }

    @Transactional
    public void fetchProducts(){
        var product = new  Product();
        product.setName("Product");

        var matcher = ExampleMatcher.matching()
                .withIncludeNullValues()
                .withIgnorePaths("name", "description")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        var example = Example.of(product, matcher);

        var products = productRepository.findAll(example);
        products.forEach(System.out::println);
    }

    public void fetchProductsByCriteria(){
        var products = productRepository.findProductByCriteria("prod",BigDecimal.valueOf(1),null);
        products.forEach(System.out::println);
    }

    public void fetchProductsBySpecification(String name, BigDecimal minPrice, BigDecimal maxPrice){
        Specification<Product> spec = Specification.where(null);

        if(name != null) {
            spec = spec.and(ProductSpec.hasName(name));
        }

        if(minPrice != null) {
            spec =spec.and(ProductSpec.hasPriceGreaterThanOrEqualTo(minPrice));
        }

        if(maxPrice != null) {
            spec = spec.and(ProductSpec.hasPriceLessThanOrEqualTo(maxPrice));
        }

        productRepository.findAll(spec).forEach(System.out::println) ;

    }

    public void fetchSortedProducts(){
        var sort = Sort.by("name").and(
                Sort.by("price").descending()
        );

        productRepository.findAll(sort).forEach(System.out::println) ;
    }

    public void fetchPaginatedProducts(int pageNumber, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize);
        Page<Product> page = productRepository.findAll(pageRequest);

        var products = page.getContent();
        products.forEach(System.out::println);

        var totalPages = page.getTotalPages();
        var totalElements = page.getTotalElements();
        System.out.println("Total elements: " + totalElements);
    }

    @Transactional
    public void fetchUsers(){
        var users = userRepository.findAllWithAddress();
        users.forEach(u->{
            System.out.println(u);
            u.getAddresses().forEach(System.out::println);
        });
    }

    @Transactional
    public void usersWithProfileExercise() {
        // User 1
        var user1 = User.builder()
                .name("John")
                .email("john@email.com")
                .password("password1")
                .build();
        user1 = userRepository.save(user1);

        var profile1 = Profile.builder()
                .bio("Bio for John")
                .phoneNumber("12345678")
                .dateOfBirth(LocalDate.parse("14-09-2025", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .loyaltyPoints(5)
                .user(user1)
                .build();
        profileRepository.save(profile1);

        // User 2
        var user2 = User.builder()
                .name("Jane")
                .email("jane@email.com")
                .password("password2")
                .build();
        user2 = userRepository.save(user2);

        var profile2 = Profile.builder()
                .bio("Bio for Jane")
                .phoneNumber("87654321")
                .dateOfBirth(LocalDate.parse("15-10-2024", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .loyaltyPoints(10)
                .user(user2)
                .build();
        profileRepository.save(profile2);

        // User 3
        var user3 = User.builder()
                .name("Bob")
                .email("bob@email.com")
                .password("password3")
                .build();
        user3 = userRepository.save(user3);

        var profile3 = Profile.builder()
                .bio("Bio for Bob")
                .phoneNumber("11223344")
                .dateOfBirth(LocalDate.parse("16-11-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .loyaltyPoints(20)
                .user(user3)
                .build();
        profileRepository.save(profile3);
    }

    @Transactional
    public void fetchProfilesByLoyaltyPoints(){
        var profiles = userRepository.findUsersByProfileLoyaltyPoints(2);
        profiles.forEach(u->
                System.out.println("id: "+ u.getId()+" email: "+u.getEmail()));
    }

}
