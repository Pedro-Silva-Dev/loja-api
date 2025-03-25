package br.com.solipy.loja.repositories;

import br.com.solipy.loja.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM usuarios WHERE email =?1 ORDER BY id LIMIT 1", nativeQuery = true)
    Optional<User> findByEmail(String email);

}
