package tn.esprit.tpfoyer17;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.FoyerService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoyerTest {

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private FoyerService foyerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Test Foyer");

        when(foyerRepository.save(foyer)).thenReturn(foyer);

        Foyer result = foyerService.addFoyer(foyer);

        assertEquals(foyer, result);
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    public void testUpdateFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Updated Foyer");

        when(foyerRepository.save(foyer)).thenReturn(foyer);

        Foyer result = foyerService.updateFoyer(foyer);

        assertEquals(foyer, result);
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    public void testRetrieveFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Test Foyer");
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        Foyer result = foyerService.retrieveFoyer(1L);

        assertNotNull(result);
        assertEquals("Test Foyer", result.getNomFoyer());
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    public void testRemoveFoyer() {
        long foyerId = 1L;

        foyerService.removeFoyer(foyerId);

        verify(foyerRepository, times(1)).deleteById(foyerId);
    }

    @Test
    public void testRetrieveAllFoyers() {
        Foyer foyer1 = new Foyer();
        foyer1.setNomFoyer("Foyer 1");

        Foyer foyer2 = new Foyer();
        foyer2.setNomFoyer("Foyer 2");

        List<Foyer> foyers = List.of(foyer1, foyer2);
        when(foyerRepository.findAll()).thenReturn(foyers);

        List<Foyer> result = foyerService.retrieveAllFoyers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(foyers));
        verify(foyerRepository, times(1)).findAll();
    }

    /*@Test
    public void testAjouterFoyerEtAffecterAUniversite() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("New Foyer");

        Universite universite = new Universite();
        universite.setNomUniversite("Université de Test");

        Bloc bloc1 = new Bloc();
        bloc1.setNomBloc("Bloc 1");
        Bloc bloc2 = new Bloc();
        bloc2.setNomBloc("Bloc 2");
        foyer.setBlocs(Set.of(bloc1, bloc2));

        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(foyerRepository.save(foyer)).thenReturn(foyer);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc1, bloc2); // Mock bloc saves
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Foyer result = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, 1L);

        // Assert
        assertNotNull(result);
        assertEquals("New Foyer", result.getNomFoyer());
        assertEquals(2, result.getBlocs().size());
        assertEquals(foyer, universite.getFoyer());  // Check that foyer is set in universite

        // Verify repository interactions
        verify(universiteRepository, times(1)).findById(1L);
        verify(foyerRepository, times(1)).save(foyer);
        verify(blocRepository, times(1)).save(bloc1);
        verify(blocRepository, times(1)).save(bloc2);
        verify(universiteRepository, times(1)).save(universite);

        // Verify that each bloc was set with the foyer
        assertEquals(foyer, bloc1.getFoyer());
        assertEquals(foyer, bloc2.getFoyer());
    }*/



}