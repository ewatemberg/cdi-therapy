package frlp.utn.edu.ar.domain;

import static org.assertj.core.api.Assertions.assertThat;

import frlp.utn.edu.ar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeccionDTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeccionD.class);
        SeccionD seccionD1 = new SeccionD();
        seccionD1.setId(1L);
        SeccionD seccionD2 = new SeccionD();
        seccionD2.setId(seccionD1.getId());
        assertThat(seccionD1).isEqualTo(seccionD2);
        seccionD2.setId(2L);
        assertThat(seccionD1).isNotEqualTo(seccionD2);
        seccionD1.setId(null);
        assertThat(seccionD1).isNotEqualTo(seccionD2);
    }
}
