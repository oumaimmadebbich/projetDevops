package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.entities.Reservation;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.repositories.ReservationRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.ReservationService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

 class ReservationTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    private Reservation reservation;
    private Bloc bloc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bloc = Bloc.builder()
                .idBloc(1L)
                .nomBloc("BlocA")
                .capaciteBloc(100)
                .build();

        reservation = Reservation.builder()
                .idReservation("1-BlocA-2024")
                .anneeUniversitaire(LocalDate.of(2024, 1, 1))
                .estValide(true)
                .etudiants(new HashSet<>())
                .build();
    }

    @Test
    void testRetrieveReservation() {
        when(reservationRepository.findById("1-BlocA-2024")).thenReturn(Optional.of(reservation));

        Reservation result = reservationService.retrieveReservation("1-BlocA-2024");
        assertNotNull(result);
        assertEquals("1-BlocA-2024", result.getIdReservation());
    }

    @Test
    void testUpdateReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation updatedReservation = reservationService.updateReservation(reservation);
        assertNotNull(updatedReservation);
        assertEquals(reservation.getIdReservation(), updatedReservation.getIdReservation());
    }

     @Test
     void testAjouterReservation() {
         Etudiant etudiant = new Etudiant();
         etudiant.setCinEtudiant(1001L);

         Chambre chambre = Chambre.builder()
                 .idChambre(201L)
                 .numeroChambre(101L)
                 .typeChambre(TypeChambre.SIMPLE)
                 .bloc(bloc)
                 .reservations(new HashSet<>())
                 .build();

         when(etudiantRepository.findByCinEtudiant(1001L)).thenReturn(etudiant);
         when(chambreRepository.findById(201L)).thenReturn(Optional.of(chambre));
         when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

         Reservation newReservation = reservationService.ajouterReservation(201L, 1001L);
         assertNotNull(newReservation);
         assertEquals("1-BlocA-2024", newReservation.getIdReservation()); // Ajustez l'ID ici pour correspondre à la logique réelle
     }

     @Test
     void testAnnulerReservation() {
         Etudiant etudiant = new Etudiant();
         etudiant.setCinEtudiant(1001L);

         Set<Reservation> reservations = new HashSet<>();
         reservations.add(reservation);
         etudiant.setReservations(reservations);

         Chambre chambre = Chambre.builder()
                 .idChambre(201L)
                 .numeroChambre(101L)
                 .typeChambre(TypeChambre.SIMPLE)
                 .bloc(bloc)
                 .reservations(new HashSet<>())
                 .build();

         when(etudiantRepository.findByCinEtudiant(1001L)).thenReturn(etudiant);
         when(reservationRepository.findById(reservation.getIdReservation())).thenReturn(Optional.of(reservation));
         when(chambreRepository.findByReservationsIdReservation(reservation.getIdReservation())).thenReturn(chambre);

         Reservation result = reservationService.annulerReservation(1001L);
         assertNull(result);
         verify(reservationRepository, times(1)).save(any(Reservation.class));
     }


}