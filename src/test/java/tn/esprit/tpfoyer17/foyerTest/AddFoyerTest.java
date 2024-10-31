package tn.esprit.tpfoyer17.foyerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.services.impementations.FoyerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)


public class AddFoyerTest {
    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerService foyerService; // The service class that contains addFoyer method

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Test Foyer");

        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Foyer result = foyerService.addFoyer(foyer);

        // Assert
        assertEquals(foyer, result);
        verify(foyerRepository, times(1)).save(foyer);
    }
}

