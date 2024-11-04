package tn.esprit.tpfoyer17.ReservationTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Reservation;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.repositories.ReservationRepository;
import tn.esprit.tpfoyer17.services.impementations.ReservationService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
 class TestReservation {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Test
    void testRetrieveAllReservation() {
        // Cas normal : le repository retourne une liste non vide
        Reservation reservation1 = new Reservation();
        reservation1.setIdReservation("RES1");
        Reservation reservation2 = new Reservation();
        reservation2.setIdReservation("RES2");

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation1, reservation2));

        List<Reservation> reservations = reservationService.retrieveAllReservation();

        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveAllReservationEmptyList() {
        // Cas vide : le repository retourne une liste vide
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());

        List<Reservation> reservations = reservationService.retrieveAllReservation();

        assertEquals(0, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

   @Test
    void testRetrieveAllReservationThrowsException() {
        // Cas d'erreur : le repository lève une exception
        when(reservationRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> reservationService.retrieveAllReservation());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testUpdateReservationSuccess() {
        Reservation reservation = new Reservation();
        reservation.setIdReservation("RES1");

        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation updatedReservation = reservationService.updateReservation(reservation);

        assertNotNull(updatedReservation);
        assertEquals("RES1", updatedReservation.getIdReservation());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testUpdateReservationThrowsException() {
        Reservation reservation = new Reservation();
        reservation.setIdReservation("RES1");

        when(reservationRepository.save(reservation)).thenThrow(new RuntimeException("Update error"));

        assertThrows(RuntimeException.class, () -> reservationService.updateReservation(reservation));
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testRetrieveReservationSuccess() {
        Reservation reservation = new Reservation();
        reservation.setIdReservation("RES1");

        when(reservationRepository.findById("RES1")).thenReturn(Optional.of(reservation));

        Reservation retrievedReservation = reservationService.retrieveReservation("RES1");

        assertNotNull(retrievedReservation);
        assertEquals("RES1", retrievedReservation.getIdReservation());
        verify(reservationRepository, times(1)).findById("RES1");
    }

    @Test
    void testRetrieveReservationNotFound() {
        when(reservationRepository.findById("RES1")).thenReturn(Optional.empty());

        Reservation retrievedReservation = reservationService.retrieveReservation("RES1");

        assertNull(retrievedReservation);
        verify(reservationRepository, times(1)).findById("RES1");
    }

    @Test
    void testRetrieveReservationThrowsException() {
        when(reservationRepository.findById("RES1")).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> reservationService.retrieveReservation("RES1"));
        verify(reservationRepository, times(1)).findById("RES1");
    }



}


