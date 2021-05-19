package pl.piasta.hotel.infrastructure.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "discounts")
@Getter
@Setter
public class DiscountsEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discounts_generator")
    @SequenceGenerator(name = "discounts_generator", sequenceName = "seq_discounts", allocationSize = 1)
    private Integer id;
    @Column(name = "code", nullable = false, length = 20)
    private String code;
    @Column(name = "value", nullable = false)
    private Integer value;
}
