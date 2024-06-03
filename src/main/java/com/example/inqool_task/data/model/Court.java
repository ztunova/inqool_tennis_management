package com.example.inqool_task.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "court_id")
    private Long id;

    @Column(name = "court_number", nullable = false, unique = true)
    private Long courtNumber;

    @ManyToOne
    @JoinColumn(name="surface_id", nullable = false)
    private CourtSurface surface;

    @OneToMany(mappedBy = "court",
            fetch = FetchType.EAGER
    )
    private Set<Reservation> reservations;


    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public boolean removeReservation(Reservation reservation) {
        return reservations.remove(reservation);
    }

    @Override
    public String toString() {
        return "Court{" +
                "id=" + id +
                ", courtNumber=" + courtNumber +
                ", surface=" + surface +
                ", reservations=" + reservations.stream().map(r -> " " + r.getId()) +
                '}';
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

        if (!(obj instanceof Court c)) {
            return false;
        }

        return Objects.equals(this.courtNumber, c.courtNumber);
    }
}
