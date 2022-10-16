package br.com.vr.miniautorizador.shared.persistence;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")

@Entity
@Table(name = "cartao")
public class Cartao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "saldo", columnDefinition = "Decimal(10,2) default '500.00'")
    private BigDecimal saldo = new BigDecimal("500.00");

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Long version;

}
