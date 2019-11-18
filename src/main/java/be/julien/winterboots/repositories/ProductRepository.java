package be.julien.winterboots.repositories;

import be.julien.winterboots.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.id IN (SELECT MAX(q.id) FROM Product q GROUP BY q.name)")
    List<Product> findAllDistinctByName();
}