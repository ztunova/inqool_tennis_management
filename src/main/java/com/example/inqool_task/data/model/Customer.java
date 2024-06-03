package com.example.inqool_task.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer",
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
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
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

        if (!(obj instanceof Customer u)) {
            return false;
        }

        return Objects.equals(this.phoneNumber, u.phoneNumber);
    }
}
