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
@Table(name = "users_bookings")
@Getter
@Setter
public class UsersBookingsEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_bookings_generator")
    @SequenceGenerator(name = "users_bookings_generator", sequenceName = "seq_users_bookings", allocationSize = 1)
    private Integer id;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "booking_id", nullable = false)
    private Integer bookingId;
}
