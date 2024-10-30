package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.services.impementations.UniversiteService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@Slf4j
class UniversiteServiceTests {
    @Autowired
    UniversiteService universiteService;
    @Test
    void addUniversitytest() {
        Universite newuniversitie= Universite.builder().nomUniversite("ESPRIT").adresse("ariena")
                .build();
        Universite saveUniversity=universiteService.addUniversity(newuniversitie);
        Assertions.assertNotNull(saveUniversity.getIdUniversite());
    }

}