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
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.services.impementations.BlocService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@Slf4j

class BlocServiceTest {
    @Autowired
    BlocService blocService;

    @Test
    void addBlocTest() {
        Bloc newBloc= Bloc.builder().nomBloc("BlocA").capaciteBloc(12).build();
        Bloc saveBloc=blocService.addBloc(newBloc);
        Assertions.assertNotNull(saveBloc.getIdBloc());


    }

}
