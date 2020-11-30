package frlp.utn.edu.ar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import frlp.utn.edu.ar.web.rest.TestUtil;

public class CuestionarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cuestionario.class);
        Cuestionario cuestionario1 = new Cuestionario();
        cuestionario1.setId(1L);
        Cuestionario cuestionario2 = new Cuestionario();
        cuestionario2.setId(cuestionario1.getId());
        assertThat(cuestionario1).isEqualTo(cuestionario2);
        cuestionario2.setId(2L);
        assertThat(cuestionario1).isNotEqualTo(cuestionario2);
        cuestionario1.setId(null);
        assertThat(cuestionario1).isNotEqualTo(cuestionario2);
    }
}
