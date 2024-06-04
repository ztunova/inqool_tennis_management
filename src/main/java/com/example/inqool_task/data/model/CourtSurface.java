package com.example.inqool_task.data.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "COURT_SURFACES")
@Where(clause = "deleted = false")
@NamedQuery(name = CourtSurface.FIND_ALL_QUERY, query = "select s from CourtSurface s")
@NamedQuery(name = CourtSurface.FIND_BY_SURFACE, query = "select s from CourtSurface s where s.surface = :surface")
public class CourtSurface {

    public static final String FIND_ALL_QUERY = "CourtSurface.findAll";
    public static final String FIND_BY_SURFACE = "CourtSurface.findBySurface";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "surface_id")
    private Long id;

    @Column(name = "surface", nullable = false)
    private String surface;

    @Column(name = "minute_price")
    private double pricePerMinute;

    @OneToMany(mappedBy = "surface",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE}
    )
    private Set<Court> courts;

    @Column(name = "deleted")
    private boolean deleted = false;


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
