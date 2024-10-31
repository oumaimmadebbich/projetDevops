package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.UniversiteService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
@ExtendWith(MockitoExtension.class)

public class UniversiteServiceTests{
    @InjectMocks
   private UniversiteService universiteService;
     @Mock
    private UniversiteRepository universiteRepository;
    @Test
    void addUniversitytest() {
        Universite newuniversitie= new Universite();
        when(universiteRepository.save(newuniversitie)).thenReturn(newuniversitie);
        Universite saveUniversity=universiteService.addUniversity(newuniversitie);
        assertEquals(newuniversitie,saveUniversity);
    }
}
