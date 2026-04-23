package bankCards.serviceBankCards.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Account> account;

    @Enumerated(EnumType.STRING)
    private TypeUser type;

    private String firstName;
    private String lastName;
    private String email;
}
