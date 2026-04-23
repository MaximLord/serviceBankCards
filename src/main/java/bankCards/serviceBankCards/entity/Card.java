package bankCards.serviceBankCards.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String number;
    private String pin;

    @Enumerated(EnumType.STRING)
    private TypeCard type;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
