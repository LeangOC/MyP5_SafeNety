package oc.p5.SafeNety;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SafeNetyApplicationTests {

    @Test
    void contextLoads() {
        // Juste pour s'assurer que le contexte se lance sans erreur
    }

    @Test
    void testMain() {
        // ➤ Appel explicite de la méthode main pour la couverture
        SafeNetyApplication.main(new String[] {});
    }
}
