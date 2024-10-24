package tn.esprit.tpfoyer17.services.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UniversiteServiceTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteService universiteService;

    @BeforeEach
    public void setup() {
        // Initialise les mocks avant chaque test
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllUniversities() {
        // Arrange
        Universite universite1 = new Universite();
        Universite universite2 = new Universite();
        List<Universite> universites = Arrays.asList(universite1, universite2);

        when(universiteRepository.findAll()).thenReturn(universites);

        // Act
        List<Universite> result = universiteService.retrieveAllUniversities();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(universiteRepository, times(1)).findAll();
    }
    @Test
    public void testAddUniversity() {
        // Arrange
        Universite universite = new Universite();
        universite.setNom("Test University");
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.addUniversity(universite);

        // Assert
        assertNotNull(result);
        assertEquals("Test University", result.getNom());
        verify(universiteRepository, times(1)).save(universite);
    }


    @Test
    public void testRetrieveUniversity() {
        // Arrange
        Universite universite = new Universite();
        universite.setNom("Test University");
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Act
        Universite result = universiteService.retrieveUniversity(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test University", result.getNom());
        verify(universiteRepository, times(1)).findById(1L);
    }
}
