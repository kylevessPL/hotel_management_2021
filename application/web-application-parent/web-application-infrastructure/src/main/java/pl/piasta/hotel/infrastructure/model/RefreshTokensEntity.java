package pl.piasta.hotel.infrastructure.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshTokensEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_tokens_generator")
    @SequenceGenerator(name = "refresh_tokens_generator", sequenceName = "seq_refresh_tokens", allocationSize = 1)
    private Integer id;
    @Column(name = "token", unique = true, nullable = false, length = 512)
    private String token;
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UsersEntity user;
}
