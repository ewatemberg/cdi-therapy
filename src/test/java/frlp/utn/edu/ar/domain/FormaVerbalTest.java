package frlp.utn.edu.ar.domain;

import static org.assertj.core.api.Assertions.assertThat;

import frlp.utn.edu.ar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormaVerbalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormaVerbal.class);
        FormaVerbal formaVerbal1 = new FormaVerbal();
        formaVerbal1.setId(1L);
        FormaVerbal formaVerbal2 = new FormaVerbal();
        formaVerbal2.setId(formaVerbal1.getId());
        assertThat(formaVerbal1).isEqualTo(formaVerbal2);
        formaVerbal2.setId(2L);
        assertThat(formaVerbal1).isNotEqualTo(formaVerbal2);
        formaVerbal1.setId(null);
        assertThat(formaVerbal1).isNotEqualTo(formaVerbal2);
    }
}
