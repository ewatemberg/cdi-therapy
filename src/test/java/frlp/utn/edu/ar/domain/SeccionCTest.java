package frlp.utn.edu.ar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import frlp.utn.edu.ar.web.rest.TestUtil;

public class SeccionCTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeccionC.class);
        SeccionC seccionC1 = new SeccionC();
        seccionC1.setId(1L);
        SeccionC seccionC2 = new SeccionC();
        seccionC2.setId(seccionC1.getId());
        assertThat(seccionC1).isEqualTo(seccionC2);
        seccionC2.setId(2L);
        assertThat(seccionC1).isNotEqualTo(seccionC2);
        seccionC1.setId(null);
        assertThat(seccionC1).isNotEqualTo(seccionC2);
    }
}
