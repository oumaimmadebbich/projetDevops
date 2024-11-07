package tn.esprit.tpfoyer17;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.ChambreService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ChambreTest {

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private ChambreService chambreService;

    private Chambre chambre;

    @BeforeEach
    public void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);

        // Création d'une chambre pour les tests
        chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101)
                .build();
    }

    @Test
    public void testRetrieveAllChambres() {
        // Préparer les données
        Chambre chambre1 = Chambre.builder().idChambre(1L).numeroChambre(101).build();
        Chambre chambre2 = Chambre.builder().idChambre(2L).numeroChambre(102).build();
        List<Chambre> chambres = Arrays.asList(chambre1, chambre2);

        // Comportement simulé pour le repository
        when(chambreRepository.findAll()).thenReturn(chambres);

        // Appeler la méthode du service
        List<Chambre> result = chambreService.retrieveAllChambres();

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    public void testAddChambre() {
        // Comportement simulé pour l'ajout d'une chambre
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        // Appeler la méthode du service
        Chambre result = chambreService.addChambre(chambre);

        // Vérifications
        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        assertEquals(101, result.getNumeroChambre());
        verify(chambreRepository, times(1)).save(any(Chambre.class));
    }

    @Test
    public void testRetrieveChambre() {
        // Préparer le mock pour le cas où une chambre est trouvée
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // Appeler la méthode du service
        Chambre result = chambreService.retrieveChambre(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveChambreNotFound() {
        // Comportement simulé pour le cas où la chambre n'existe pas
        when(chambreRepository.findById(99L)).thenReturn(Optional.empty());

        // Appeler la méthode du service
        Chambre result = chambreService.retrieveChambre(99L);

        // Vérifications
        assertNull(result);
        verify(chambreRepository, times(1)).findById(99L);
    }
}
