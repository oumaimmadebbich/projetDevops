package tn.esprit.tpfoyer17.ReservationTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Reservation;
import tn.esprit.tpfoyer17.repositories.ReservationRepository;
import tn.esprit.tpfoyer17.services.impementations.ReservationService;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RetrieveTestReservation {
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReservationService  reservationService;

    @Test
    void testRetrieveAllReservation() {
        // Données de test
        Reservation reservation1 = new Reservation("1", LocalDate.of(2024, 10, 30), true, null);
        Reservation reservation2 = new Reservation("2", LocalDate.of(2024, 11, 1), true, null);

        List<Reservation> mockReservations = Arrays.asList(reservation1, reservation2);

        // Simule le comportement de findAll() pour retourner les mockReservations
        when(reservationRepository.findAll()).thenReturn(mockReservations);

        // Appel de la méthode à tester
        List<Reservation> reservations = reservationService.retrieveAllReservation();

        // Vérifications
        assertEquals(2, reservations.size());  // Vérifie le nombre de réservations
        assertEquals("1", reservations.get(0).getIdReservation());  // Vérifie l'identifiant
        assertEquals("2", reservations.get(1).getIdReservation());

        // Vérifie que findAll a été appelé exactement une fois
        verify(reservationRepository, times(1)).findAll();
    }



}
