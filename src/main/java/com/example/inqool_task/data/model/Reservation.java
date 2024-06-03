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
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "start", nullable = false)
    private LocalDateTime reservationStart;

    @Column(name = "end", nullable = false)
    private LocalDateTime reservationEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_type")
    private GameType gameType;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="court_id", nullable = false)
    private Court court;

    @Transient
    private double totalPrice;


    public double calculateTotalPrice() {
        long diff = ChronoUnit.MINUTES.between(reservationStart, reservationEnd);
        double rentalPrice = diff * this.court.getSurface().getPricePerMinute();
        return rentalPrice * gameType.getCharge();
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
