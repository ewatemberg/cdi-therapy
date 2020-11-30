package frlp.utn.edu.ar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import frlp.utn.edu.ar.web.rest.TestUtil;

public class SeccionATest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeccionA.class);
        SeccionA seccionA1 = new SeccionA();
        seccionA1.setId(1L);
        SeccionA seccionA2 = new SeccionA();
        seccionA2.setId(seccionA1.getId());
        assertThat(seccionA1).isEqualTo(seccionA2);
        seccionA2.setId(2L);
        assertThat(seccionA1).isNotEqualTo(seccionA2);
        seccionA1.setId(null);
        assertThat(seccionA1).isNotEqualTo(seccionA2);
    }
}
