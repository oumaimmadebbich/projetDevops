package tn.esprit.tpfoyer17.ReservationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.Reservation;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.services.impementations.ReservationService;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashSet;

public class TestReservation {

    private TestReservation testReservation;
    @BeforeEach
    void setUp() {
        testReservation = new TestReservation();
    }


    @Test
    void testCapaciteChambreMaximale_SimpleChambre() {
        Chambre chambre = new Chambre();
        chambre.setTypeChambre(TypeChambre.SIMPLE);
        chambre.setReservations(new HashSet<>());

        // Test avec 0 réservations
        assertTrue(ReservationService.capaciteChambreMaximale(chambre));

        // Test avec 1 réservation
        chambre.getReservations().add(new Reservation());
        assertTrue(ReservationService.capaciteChambreMaximale(chambre));

        // Test avec 2 réservations
        chambre.getReservations().add(new Reservation());
        assertFalse(ReservationService.capaciteChambreMaximale(chambre));
    }

}
