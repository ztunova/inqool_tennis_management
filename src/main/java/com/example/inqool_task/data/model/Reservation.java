package com.example.inqool_task.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table
@NamedQuery(name = Reservation.FIND_ALL_QUERY, query = "select r from Reservation r where r.deleted = false")
@NamedQuery(name = Reservation.FIND_BY_ID_QUERY, query = "select r from Reservation r where r.id = :id and r.deleted = false")
//@NamedQuery(name = Court.FIND_BY_COURT_NUMBER, query = "select c from Court c where c.courtNumber = :courtNumber and c.deleted = false")
public class Reservation {

    public static final String FIND_ALL_QUERY = "Reservation.findAll";
    public static final String FIND_BY_ID_QUERY = "Reservation.findReservationById";
 //   public static final String FIND_BY_COURT_NUMBER = "Court.findCourtByNumber";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "reservation_id")
    private Long id;

    @Column(name = "reservation_start", nullable = false)
    private LocalDateTime reservationStart;

    @Column(name = "reservation_end", nullable = false)
    private LocalDateTime reservationEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_type")
    private GameType gameType;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="court_id")
    private Court court;

    private boolean deleted = false;

    @Transient
    private double totalPrice;


//    @PostLoad
//    @PostPersist
    public void calculateTotalPrice() {
        long diff = ChronoUnit.MINUTES.between(reservationStart, reservationEnd);
        double rentalPrice = diff * this.court.getSurface().getPricePerMinute();
        this.totalPrice = rentalPrice * gameType.getCharge();
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Reservation r)) {
            return false;
        }

        if (this.reservationStart.isEqual(r.reservationStart) && this.reservationEnd.isEqual(r.reservationEnd)
                && this.gameType == r.gameType && this.customer == r.customer && this.court == r.court) {
            return true;
        }

        return false;
    }
}
