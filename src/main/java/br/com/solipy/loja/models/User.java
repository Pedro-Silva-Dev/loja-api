package br.com.solipy.loja.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "usuarios")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="senha")
    private String password;

    @Column(name="telefone")
    private String phone;

    @Column(name="conta_nao_expirada")
    private Boolean accountNonExpired;

    @Column(name="conta_nao_bloqueada")
    private Boolean accountNonLocked;

    @Column(name="credenciais_nao_expiradas")
    private Boolean credentialsNonExpired;

    @Column(name="ativo")
    private Boolean active;

    @Column(name="token")
    private String token;

    @CreatedDate
    @Column(name = "dhc")
    private LocalDateTime dhc;

    @LastModifiedDate
    @Column(name = "dhu")
    private LocalDateTime dhu;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_regras",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "regra_id"))
    private List<Role> roles = new ArrayList<Role>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @PrePersist
    public void createUserToken() {
        this.token = UUID.randomUUID().toString();
    }

}
