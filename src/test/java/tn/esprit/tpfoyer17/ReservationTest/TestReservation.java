package tn.esprit.tpfoyer17.ReservationTest;

import org.junit.jupiter.api.Test;

import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.entities.Reservation;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.repositories.ReservationRepository;
import tn.esprit.tpfoyer17.services.impementations.ReservationService;


import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;


import java.util.*;


import java.util.Arrays;
import java.util.List;
public class TestReservation {

  /*  private TestReservation testReservation;
    @BeforeEach
    void setUp() {
        testReservation = new TestReservation();
    }


    /*@Test
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
    }*/
    ReservationService reservationService;
    ReservationRepository reservationRepository;
    EtudiantRepository etudiantRepository;
    ChambreRepository chambreRepository;
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
        when(reservationRepository.findAll()).thenReturn(Arrays.asList());

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
    @Test
    void testAnnulerReservationSuccess() {
        Etudiant etudiant = new Etudiant();
        etudiant.setCinEtudiant(123456);
        Reservation reservation = new Reservation();
        reservation.setIdReservation("RES1");
        reservation.setEtudiants(Set.of(etudiant));
        Chambre chambre = new Chambre();
        chambre.setTypeChambre(TypeChambre.SIMPLE);
        chambre.setReservations(Set.of(reservation));

        when(etudiantRepository.findByCinEtudiant(123456)).thenReturn(etudiant);
        when(chambreRepository.findByReservationsIdReservation("RES1")).thenReturn(chambre);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationService.annulerReservation(123456);

        assertNotNull(result);
        verify(etudiantRepository, times(1)).findByCinEtudiant(123456);
        verify(chambreRepository, times(1)).findByReservationsIdReservation("RES1");
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testAnnulerReservationEtudiantNotFound() {
        when(etudiantRepository.findByCinEtudiant(123456)).thenReturn(null);

        Reservation result = reservationService.annulerReservation(123456);

        assertNull(result);
        verify(etudiantRepository, times(1)).findByCinEtudiant(123456);
        verifyNoInteractions(chambreRepository);
        verifyNoInteractions(reservationRepository);
    }
    @Test
    void testAjouterReservationSuccess() {
        Etudiant etudiant = new Etudiant();
        etudiant.setCinEtudiant(123456);
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1);
        chambre.setNumeroChambre(101);
        chambre.setTypeChambre(TypeChambre.SIMPLE);
        chambre.setReservations(new HashSet<>());

        when(etudiantRepository.findByCinEtudiant(123456)).thenReturn(etudiant);
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        Reservation result = reservationService.ajouterReservation(1L, 123456);

        assertNotNull(result);
        verify(etudiantRepository, times(1)).findByCinEtudiant(123456);
        verify(chambreRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testAjouterReservationChambreNotFound() {
        when(chambreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AssertionError.class, () -> reservationService.ajouterReservation(1L, 123456));
        verify(chambreRepository, times(1)).findById(1L);
        verifyNoInteractions(etudiantRepository);
        verifyNoInteractions(reservationRepository);
    }


}
