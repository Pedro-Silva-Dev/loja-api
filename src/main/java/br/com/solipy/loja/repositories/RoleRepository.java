package br.com.solipy.loja.repositories;

import br.com.solipy.loja.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM regras WHERE upper(regra) = 'ROLE_USER' LIMIT 1", nativeQuery = true)
    Optional<Role> findByRoleUser();

}
