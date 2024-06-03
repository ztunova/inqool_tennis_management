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
@Table(name = "COURT_SURFACES")
public class CourtSurface {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "surface_id")
    private Long id;

    @Column(name = "surface", nullable = false, unique = true)
    private String surface;

    @Column(name = "minute_price")
    private double pricePerMinute;

    @OneToMany(mappedBy = "surface",
            fetch = FetchType.EAGER
    )
    private Set<Court> courts;


    public void addCourt(Court court) {
        this.courts.add(court);
    }

    public boolean removeCourt(Court court) {
        return courts.remove(court);
    }

    @Override
    public String toString() {
        return "CourtSurface{" +
                "id=" + id +
                ", surface='" + surface + '\'' +
                ", pricePerMinute=" + pricePerMinute +
                ", courts=" + (courts == null ? "null" : courts.stream().map(c -> " " + c.getId())) +
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

        if (!(obj instanceof CourtSurface c)) {
            return false;
        }

        return Objects.equals(this.surface, c.surface);
    }
}
