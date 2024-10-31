package tn.esprit.tpfoyer17;

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
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.impementations.BlocService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class BlocServiceTest {

    @Mock
    BlocRepository blocRepository;

    @InjectMocks
    BlocService blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    void addBlocTest() {
        // Arrange
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(100L);

        // Act
        when(blocRepository.save(bloc)).thenReturn(bloc);
        Bloc savedBloc = blocService.addBloc(bloc);

        // Assert
        assertNotNull(savedBloc);
        assertEquals("Bloc A", savedBloc.getNomBloc());
        log.info("Bloc ajouté avec succès : {}", savedBloc);
    }

    @Test
    @Order(2)
    void updateBlocTest() {
        // Arrange
        Bloc existingBloc = new Bloc();
        existingBloc.setNomBloc("Bloc A");
        existingBloc.setCapaciteBloc(100L);

        Bloc updatedBloc = new Bloc();
        updatedBloc.setNomBloc("Bloc B Updated");
        updatedBloc.setCapaciteBloc(150L);

        // Utilisation de lenient() pour éviter les erreurs de stubbing inutilisé
        lenient().when(blocRepository.findById(anyLong())).thenReturn(Optional.of(existingBloc));
        when(blocRepository.save(existingBloc)).thenReturn(updatedBloc);

        // Act
        Bloc result = blocService.updateBloc(existingBloc);

        // Assert
        assertNotNull(result);
        assertEquals("Bloc B Updated", result.getNomBloc());
        log.info("Bloc mis à jour avec succès : {}", result);
    }
    @Test
    @Order(3)
    void retrieveBlocTest() {
        // Arrange
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");

        // Act
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));
        Bloc result = blocService.retrieveBloc(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
        log.info("Bloc récupéré avec succès : {}", result);
    }

    @Test
    @Order(4)
    void removeBlocTest() {
        // Arrange
        Long blocId = 1L;

        // Act
        doNothing().when(blocRepository).deleteById(blocId);
        blocService.removeBloc(blocId);

        // Assert
        verify(blocRepository, times(1)).deleteById(blocId);
        log.info("Bloc supprimé avec succès : ID {}", blocId);
    }

    @Test
    @Order(5)
    void retrieveBlocsTest() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(new Bloc());

        // Act
        when(blocRepository.findAll()).thenReturn(blocs);
        List<Bloc> result = blocService.retrieveBlocs();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        log.info("Tous les blocs récupérés : {}", result);
    }

    @Test
    @Order(6)
    void findByFoyerIdFoyerTest() {
        // Arrange
        Long foyerId = 1L;
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(new Bloc());

        // Act
        when(blocRepository.findByFoyerIdFoyer(foyerId)).thenReturn(blocs);
        List<Bloc> result = blocService.findByFoyerIdFoyer(foyerId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        log.info("Blocs par Foyer ID récupérés : {}", result);
    }

    @Test
    @Order(7)
    void findByChambresIdChambreTest() {
        // Arrange
        Long chambreId = 1L;
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc C");

        // Act
        when(blocRepository.findByChambresIdChambre(chambreId)).thenReturn(bloc);
        Bloc result = blocService.findByChambresIdChambre(chambreId);

        // Assert
        assertNotNull(result);
        assertEquals("Bloc C", result.getNomBloc());
        log.info("Bloc par Chambre ID récupéré : {}", result);
    }
}