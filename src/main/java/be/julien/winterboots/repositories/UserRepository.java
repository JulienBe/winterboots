package be.julien.winterboots.repositories;

import be.julien.winterboots.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    List<User> findByName(String name);
    List<User> findByEmail(String email);
}