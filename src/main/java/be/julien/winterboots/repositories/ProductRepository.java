package be.julien.winterboots.repositories;

import be.julien.winterboots.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    List<ProductEntity> findAll();
    List<ProductEntity> findByName(String name);
}