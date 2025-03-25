package br.com.solipy.loja.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "regras")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "regra")
    private String role;

    @Column(name = "descricao")
    private String description;

    @Column(name = "ativo")
    private Boolean active;

    @CreatedDate
    @Column(name = "dhc")
    private LocalDateTime dhc;

    @LastModifiedDate
    @Column(name = "dhu")
    private LocalDateTime dhu;

}
