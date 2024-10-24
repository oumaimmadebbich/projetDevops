package tn.esprit.tpfoyer17.services.impementations;
import mockito;
import static mockito.Mockito.*;
import static unit.jupiter.api.Assertions.*;
import mockito.InjectMocks;
import mockito.Mock;
import mockito.MockitoAnnotations;
import junit.jupiter.api.BeforeEach;
import junit.jupiter.api.Test;

import static mockito.Mockito.*;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UniversiteServiceTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteService universiteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
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
    public void testRetrieveAllUniversities() {
        // Arrange
        Universite universite1 = new Universite();
        universite1.setNom("University 1");
        Universite universite2 = new Universite();
        universite2.setNom("University 2");
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(universite1, universite2));

        // Act
        List<Universite> result = universiteService.retrieveAllUniversities();

        // Assert
        assertEquals(2, result.size());
        verify(universiteRepository, times(1)).findAll();
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
