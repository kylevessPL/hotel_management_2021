package pl.piasta.hotel.infrastructure.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payment_forms")
@Getter
@Setter
public class PaymentFormsEntity {

    @Id @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", nullable = false, length = 10)
    private String name;

}