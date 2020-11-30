package frlp.utn.edu.ar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import frlp.utn.edu.ar.web.rest.TestUtil;

public class SeccionBTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeccionB.class);
        SeccionB seccionB1 = new SeccionB();
        seccionB1.setId(1L);
        SeccionB seccionB2 = new SeccionB();
        seccionB2.setId(seccionB1.getId());
        assertThat(seccionB1).isEqualTo(seccionB2);
        seccionB2.setId(2L);
        assertThat(seccionB1).isNotEqualTo(seccionB2);
        seccionB1.setId(null);
        assertThat(seccionB1).isNotEqualTo(seccionB2);
    }
}
