package com.codewithmosh.store.services;

import com.codewithmosh.store.entities.Address;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
        var products =productRepository.findProducts(BigDecimal.valueOf(1),BigDecimal.valueOf(15));
        products.forEach(System.out::println);
    }

    @Transactional
    public void fetchUsers(){
        var users = userRepository.findAllWithAddress();
        users.forEach(u->{
            System.out.println(u);
            u.getAddresses().forEach(System.out::println);
        });
    }
}
