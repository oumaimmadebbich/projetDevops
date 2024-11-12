package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.ChambreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChambreServiceTest {

    @InjectMocks
    private ChambreService chambreService;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllChambres() {
        List<Chambre> chambreList = new ArrayList<>();
        chambreList.add(new Chambre());
        chambreList.add(new Chambre());

        when(chambreRepository.findAll()).thenReturn(chambreList);

        List<Chambre> result = chambreService.retrieveAllChambres();
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    public void testAddChambre() {
        Chambre chambre = new Chambre();
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Chambre result = chambreService.addChambre(chambre);
        assertNotNull(result);
        verify(chambreRepository, times(1)).save(chambre);
    }


    @Test
    public void testUpdateChambre() {
        Chambre chambre = new Chambre();
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Chambre result = chambreService.updateChambre(chambre);
        assertNotNull(result);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    public void testRetrieveChambre() {
        long id = 1L;
        Chambre chambre = new Chambre();
        when(chambreRepository.findById(id)).thenReturn(Optional.of(chambre));

        Chambre result = chambreService.retrieveChambre(id);
        assertNotNull(result);
        verify(chambreRepository, times(1)).findById(id);
    }

    @Test
    public void testFindByTypeChambre() {
        List<Chambre> chambreList = new ArrayList<>();
        chambreList.add(new Chambre());
        when(chambreRepository.findByTypeChambreAndReservationsEstValide(TypeChambre.DOUBLE, true)).thenReturn(chambreList);

        List<Chambre> result = chambreService.findByTypeChambre();
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).findByTypeChambreAndReservationsEstValide(TypeChambre.DOUBLE, true);
    }

    @Test
    public void testAffecterChambresABloc() {
        long idBloc = 1L;
        List<Long> chambreIds = List.of(101L, 102L);
        Bloc bloc = new Bloc();
        List<Chambre> chambreList = List.of(new Chambre(), new Chambre());

        when(blocRepository.findById(idBloc)).thenReturn(Optional.of(bloc));
        when(chambreRepository.findByNumeroChambreIn(chambreIds)).thenReturn(chambreList);
        when(chambreRepository.save(any(Chambre.class))).thenReturn(new Chambre());

        Bloc result = chambreService.affecterChambresABloc(chambreIds, idBloc);
        assertNotNull(result);
        verify(chambreRepository, times(2)).save(any(Chambre.class));
        verify(chambreRepository, times(1)).findByNumeroChambreIn(chambreIds);
    }

    @Test
    public void testGetChambresParNomUniversite() {
        String nomUniversite = "Test University";
        List<Chambre> chambreList = new ArrayList<>();
        chambreList.add(new Chambre());

        when(chambreRepository.findByBlocFoyerUniversiteNomUniversiteLike(nomUniversite)).thenReturn(chambreList);

        List<Chambre> result = chambreService.getChambresParNomUniversite(nomUniversite);
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).findByBlocFoyerUniversiteNomUniversiteLike(nomUniversite);
    }

    @Test
    public void testGetChambresParBlocEtType() {
        // Initialisation des données pour le test
        long idBloc = 1L;
        TypeChambre typeChambre = TypeChambre.DOUBLE;

        // Création d'une chambre simulée
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(1L);  // L'ID de la chambre
        chambre.setTypeChambre(typeChambre);  // Type de chambre
        List<Chambre> chambreList = new ArrayList<>();
        chambreList.add(chambre);

        // Simuler le comportement du repository
        when(chambreRepository.findByBlocIdBlocAndTypeChambre(idBloc, typeChambre)).thenReturn(chambreList);

        // Appeler la méthode du service
        List<Chambre> result = chambreService.getChambresParBlocEtType(idBloc, typeChambre);

        // Vérification : la liste doit contenir une chambre
        assertEquals(1, result.size());  // Vérifie qu'une chambre est retournée
        verify(chambreRepository, times(1)).findByBlocIdBlocAndTypeChambre(idBloc, typeChambre);
    }

    @Test
    public void testGetChambresParBlocEtTypeJPQL() {
        // Données de test
        long idBloc = 1L;
        TypeChambre typeChambre = TypeChambre.DOUBLE;

        // Simuler le retour du repository
        when(chambreRepository.recupererParBlocEtTypeChambre(idBloc, typeChambre))
                .thenReturn(List.of(new Chambre()));

        // Appeler la méthode du service
        List<Chambre> result = chambreService.getChambresParBlocEtTypeJPQL(idBloc, typeChambre);

        // Vérification
        assertEquals(1, result.size()); // La taille doit être 1
        verify(chambreRepository).recupererParBlocEtTypeChambre(idBloc, typeChambre); // Vérifier que la méthode a été appelée
    }


    @Test
    public void testGetChambresNonReserveParNomUniversiteEtTypeChambre() {
        String nomUniversite = "Test University";
        TypeChambre typeChambre = TypeChambre.SIMPLE;
        List<Chambre> chambreList = new ArrayList<>();
        chambreList.add(new Chambre());

        when(chambreRepository.getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, typeChambre)).thenReturn(chambreList);

        List<Chambre> result = chambreService.getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, typeChambre);
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, typeChambre);
    }

    @Test
    public void testGetChambresNonReserve() {
        List<Chambre> chambreList = new ArrayList<>();
        chambreList.add(new Chambre());

        when(chambreRepository.getChambresNonReserve()).thenReturn(chambreList);

        chambreService.getChambresNonReserve();
        verify(chambreRepository, times(1)).getChambresNonReserve();
    }
}
