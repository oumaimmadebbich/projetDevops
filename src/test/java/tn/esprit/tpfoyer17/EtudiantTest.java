package tn.esprit.tpfoyer17;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.services.impementations.EtudiantService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EtudiantServiceTest {

    @InjectMocks
    private EtudiantService etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllEtudiants() {
        // Given
        Etudiant etudiant1 = new Etudiant(1, "John", "Doe", 123456, null, null);
        Etudiant etudiant2 = new Etudiant(2, "Jane", "Doe", 654321, null, null);
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        // When
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Then
        assertEquals(2, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testAddEtudiants() {
        // Given
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 123456, null, null);
        when(etudiantRepository.saveAll(any())).thenReturn(Arrays.asList(etudiant));

        // When
        List<Etudiant> result = etudiantService.addEtudiants(Arrays.asList(etudiant));

        // Then
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getNomEtudiant());
        verify(etudiantRepository, times(1)).saveAll(any());
    }

    @Test
    void testUpdateEtudiant() {
        // Given
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 123456, null, null);
        when(etudiantRepository.save(any())).thenReturn(etudiant);

        // When
        Etudiant result = etudiantService.updateEtudiant(etudiant);

        // Then
        assertEquals("John", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(any());
    }

    @Test
    void testRetrieveEtudiant() {
        // Given
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 123456, null, null);
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // When
        Etudiant result = etudiantService.retrieveEtudiant(1L);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoveEtudiant() {
        // Given
        long idEtudiant = 1L;

        // When
        etudiantService.removeEtudiant(idEtudiant);

        // Then
        verify(etudiantRepository, times(1)).deleteById(idEtudiant);
    }

    @Test
    void testFindByReservationsAnneeUniversitaire() {
        // Given
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 123456, null, null);
        when(etudiantRepository.findByReservationsAnneeUniversitaire(LocalDate.now())).thenReturn(Arrays.asList(etudiant));

        // When
        List<Etudiant> result = etudiantService.findByReservationsAnneeUniversitaire();

        // Then
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getNomEtudiant());
        verify(etudiantRepository, times(1)).findByReservationsAnneeUniversitaire(LocalDate.now());
    }
}

