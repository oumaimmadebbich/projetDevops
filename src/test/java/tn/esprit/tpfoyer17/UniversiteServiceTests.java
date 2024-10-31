package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.UniversiteService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest

@Slf4j
public class UniversiteServiceTests {
    @Autowired
    UniversiteService universiteService;
    UniversiteRepository universiteRepository;
    @Test
    void addUniversitytest() {
        Universite newuniversitie= Universite.builder().nomUniversite("ESPRIT").adresse("ariena")
                .build();
        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(newuniversitie);
        Universite saveUniversity=universiteService.addUniversity(newuniversitie);
    }
}
