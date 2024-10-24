package tn.esprit.tpfoyer17.services.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocServiceTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocService blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddBloc() {
        // Préparation des données
        Bloc mockBloc = new Bloc();
        mockBloc.setNomBloc("Bloc A");
        mockBloc.setCapaciteBloc(100);

        when(blocRepository.save(mockBloc)).thenReturn(mockBloc);

        // Appel de la méthode du service
        Bloc savedBloc = blocService.addBloc(mockBloc);

        // Vérifications
        assertNotNull(savedBloc);
        assertEquals("Bloc A", savedBloc.getNomBloc());
        assertEquals(100, savedBloc.getCapaciteBloc());
    }

    @Test
    void testRetrieveBloc() {
        // Préparation des données
        Bloc mockBloc = new Bloc();
        mockBloc.setIdBloc(1L);
        mockBloc.setNomBloc("Bloc B");

        when(blocRepository.findById(1L)).thenReturn(Optional.of(mockBloc));

        // Appel de la méthode du service
        Bloc retrievedBloc = blocService.retrieveBloc(1L);

        // Vérifications
        assertNotNull(retrievedBloc);
        assertEquals(1L, retrievedBloc.getIdBloc());
        assertEquals("Bloc B", retrievedBloc.getNomBloc());
    }

    @Test
    void testRemoveBloc() {
        // Préparation des données
        Bloc mockBloc = new Bloc();
        mockBloc.setIdBloc(1L);

        doNothing().when(blocRepository).deleteById(1L);

        // Appel de la méthode du service
        blocService.removeBloc(1L);

        // Vérification que la méthode du repository a bien été appelée
        verify(blocRepository, times(1)).deleteById(1L);
    }
}
