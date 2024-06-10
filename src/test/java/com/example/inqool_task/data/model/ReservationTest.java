package com.example.inqool_task.data.model;

import com.example.inqool_task.util.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ReservationTest {

    @Test
    void calculateTotalPrice_singlesMatch_priceSet() {
        Reservation reservation = TestDataFactory.reservationEntity;
        reservation.calculateTotalPrice();
        assertThat(reservation.getTotalPrice()).isEqualTo(60);
    }

    @Test
    void calculateTotalPrice_doublesMatch_priceSet() {
        Reservation reservation = TestDataFactory.reservationEntity;
        reservation.setGameType(GameType.DOUBLES_MATCH);
        reservation.calculateTotalPrice();
        assertThat(reservation.getTotalPrice()).isEqualTo(90);
    }
}
