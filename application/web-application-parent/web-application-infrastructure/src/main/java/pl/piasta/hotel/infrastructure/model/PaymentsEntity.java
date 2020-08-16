package pl.piasta.hotel.infrastructure.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class PaymentsEntity {

    @Id @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "booking_id", nullable = false)
    private int bookingId;
    @Column(name = "payment_date", nullable = false)
    private Timestamp paymentDate;
    @Column(name = "payment_form_id", nullable = false)
    private int paymentFormId;

}