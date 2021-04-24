package frlp.utn.edu.ar.domain;

import static org.assertj.core.api.Assertions.assertThat;

import frlp.utn.edu.ar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraseComplejaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraseCompleja.class);
        FraseCompleja fraseCompleja1 = new FraseCompleja();
        fraseCompleja1.setId(1L);
        FraseCompleja fraseCompleja2 = new FraseCompleja();
        fraseCompleja2.setId(fraseCompleja1.getId());
        assertThat(fraseCompleja1).isEqualTo(fraseCompleja2);
        fraseCompleja2.setId(2L);
        assertThat(fraseCompleja1).isNotEqualTo(fraseCompleja2);
        fraseCompleja1.setId(null);
        assertThat(fraseCompleja1).isNotEqualTo(fraseCompleja2);
    }
}
