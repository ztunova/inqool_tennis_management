package com.example.inqool_task.data.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "COURTS")
@NamedQuery(name = Court.FIND_ALL_QUERY, query = "select c from Court c where c.deleted = false")
@NamedQuery(name = Court.FIND_BY_ID_QUERY, query = "select c from Court c where c.id = :id and c.deleted = false")
@NamedQuery(name = Court.FIND_BY_COURT_NUMBER, query = "select c from Court c where c.courtNumber = :courtNumber and c.deleted = false")
public class Court {

    public static final String FIND_ALL_QUERY = "Court.findAll";
    public static final String FIND_BY_ID_QUERY = "Court.findCourtById";
    public static final String FIND_BY_COURT_NUMBER = "Court.findCourtByNumber";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "court_id")
    private Long id;

    @Column(name = "court_number", nullable = false)
    private Long courtNumber;

    @ManyToOne
    @JoinColumn(name="surface_id")
    private CourtSurface surface;

    @OneToMany(mappedBy = "court",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE}
    )
    private Set<Reservation> reservations = new HashSet<>();

    private boolean deleted = false;


    public void setSurface(CourtSurface surface) {
        this.surface = surface;
    }

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
                ", reservations=" + (reservations == null? "null" : reservations.stream().map(r -> " " + r.getId())) +
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
