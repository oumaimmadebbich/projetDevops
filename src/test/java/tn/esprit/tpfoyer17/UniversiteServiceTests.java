package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.UniversiteService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UniversiteServiceTests {

    @Mock
    UniversiteRepository universiteRepository;

    @Mock
    FoyerRepository foyerRepository;

    @InjectMocks
    UniversiteService universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUniversityTest() {
        // Arrange
        Universite universite = new Universite();
        universite.setNomUniversite("Université de Test");
        universite.setAdresse("Adresse de Test");

        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite savedUniversite = universiteService.addUniversity(universite);

        // Assert
        assertNotNull(savedUniversite);
        assertEquals("Université de Test", savedUniversite.getNomUniversite());
    }

    @Test
    void updateUniversityTest() {
        // Arrange
        Universite existingUniversite = new Universite();
        existingUniversite.setNomUniversite("Université de Test");
        existingUniversite.setAdresse("Adresse de Test");

        when(universiteRepository.save(existingUniversite)).thenReturn(existingUniversite);

        // Act
        Universite updatedUniversite = universiteService.updateUniversity(existingUniversite);

        // Assert
        assertNotNull(updatedUniversite);
        assertEquals("Université de Test", updatedUniversite.getNomUniversite());
    }

    @Test
    void retrieveUniversityTest() {
        // Arrange
        Universite universite = new Universite();
        universite.setNomUniversite("Université de Test");
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Act
        Universite result = universiteService.retrieveUniversity(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Université de Test", result.getNomUniversite());
    }

    @Test
    void desaffecterFoyerAUniversiteTest() {
        // Arrange
        Universite universite = new Universite();
        universite.setNomUniversite("Université de Test");
        universite.setFoyer(new Foyer());

        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.desaffecterFoyerAUniversite(1L);

        // Assert
        assertNotNull(result);
        assertNull(result.getFoyer());
    }

    @Test
    void affecterFoyerAUniversiteTest() {
        // Arrange
        Foyer foyer = new Foyer();
        Universite universite = new Universite();
        universite.setNomUniversite("Université de Test");

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversiteLike("Université de Test")).thenReturn(universite);
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.affecterFoyerAUniversite(1L, "Université de Test");

        // Assert
        assertNotNull(result);
        assertNotNull(result.getFoyer());
    }

    @Test
    void retrieveAllUniversitiesTest() {
        // Arrange
        Universite universite1 = new Universite();
        universite1.setNomUniversite("Université A");

        Universite universite2 = new Universite();
        universite2.setNomUniversite("Université B");

        List<Universite> universites = List.of(universite1, universite2);
        when(universiteRepository.findAll()).thenReturn(universites);

        // Act
        List<Universite> result = universiteService.retrieveAllUniversities();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}