package tn.esprit.tpfoyer17.TestEtudiant;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.controllers.EtudiantController;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.services.interfaces.IEtudiantService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EtudiantControllerTest {

    @Mock
    IEtudiantService etudiantService;

    @InjectMocks
    EtudiantController etudiantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    void findByReservationsAnneeUniversitaireTest() {
        // Arrange
        List<Etudiant> etudiants = new ArrayList<>();
        // Ajouter des étudiants à la liste pour simuler une réponse
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("Etudiant A"); // Utilisation correcte de setNomEtudiant
        etudiants.add(etudiant);

        when(etudiantService.findByReservationsAnneeUniversitaire()).thenReturn(etudiants);

        // Act
        List<Etudiant> result = etudiantController.findByReservationsAnneeUniversitaire();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Etudiant A", result.get(0).getNomEtudiant()); // Utilisation correcte de getNomEtudiant
        log.info("Etudiants récupérés par année universitaire : {}", result);
    }

    @Test
    @Order(2)
    void retrieveAllEtudiantsTest() {
        // Arrange
        List<Etudiant> etudiants = new ArrayList<>();
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("Etudiant B"); // Utilisation correcte de setNomEtudiant
        etudiants.add(etudiant);

        when(etudiantService.retrieveAllEtudiants()).thenReturn(etudiants);

        // Act
        List<Etudiant> result = etudiantController.retrieveAllEtudiants();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Etudiant B", result.get(0).getNomEtudiant()); // Utilisation correcte de getNomEtudiant
        log.info("Tous les étudiants récupérés : {}", result);
    }

    @Test
    @Order(3)
    void addEtudiantsTest() {
        // Arrange
        List<Etudiant> etudiants = new ArrayList<>();
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("Etudiant C"); // Utilisation correcte de setNomEtudiant
        etudiants.add(etudiant);

        when(etudiantService.addEtudiants(etudiants)).thenReturn(etudiants);

        // Act
        List<Etudiant> result = etudiantController.addEtudiants(etudiants);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Etudiant C", result.get(0).getNomEtudiant()); // Utilisation correcte de getNomEtudiant
        log.info("Étudiants ajoutés avec succès : {}", result);
    }

    @Test
    @Order(4)
    void updateEtudiantTest() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("Etudiant D"); // Utilisation correcte de setNomEtudiant

        when(etudiantService.updateEtudiant(etudiant)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantController.updateEtudiant(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals("Etudiant D", result.getNomEtudiant()); // Utilisation correcte de getNomEtudiant
        log.info("Étudiant mis à jour avec succès : {}", result);
    }

    @Test
    @Order(5)
    void retrieveEtudiantTest() {
        // Arrange
        long idEtudiant = 1L;
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(idEtudiant);
        etudiant.setNomEtudiant("Etudiant E"); // Utilisation correcte de setNomEtudiant

        when(etudiantService.retrieveEtudiant(idEtudiant)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantController.retrieveEtudiant(idEtudiant);

        // Assert
        assertNotNull(result);
        assertEquals("Etudiant E", result.getNomEtudiant()); // Utilisation correcte de getNomEtudiant
        log.info("Étudiant récupéré avec succès : {}", result);
    }

    @Test
    @Order(6)
    void removeEtudiantTest() {
        // Arrange
        long idEtudiant = 1L;

        // Act
        doNothing().when(etudiantService).removeEtudiant(idEtudiant);
        etudiantController.removeEtudiant(idEtudiant);

        // Assert
        verify(etudiantService, times(1)).removeEtudiant(idEtudiant);
        log.info("Étudiant supprimé avec succès : ID {}", idEtudiant);
    }
}
