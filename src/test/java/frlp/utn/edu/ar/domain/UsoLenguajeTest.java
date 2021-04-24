package frlp.utn.edu.ar.domain;

import static org.assertj.core.api.Assertions.assertThat;

import frlp.utn.edu.ar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsoLenguajeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsoLenguaje.class);
        UsoLenguaje usoLenguaje1 = new UsoLenguaje();
        usoLenguaje1.setId(1L);
        UsoLenguaje usoLenguaje2 = new UsoLenguaje();
        usoLenguaje2.setId(usoLenguaje1.getId());
        assertThat(usoLenguaje1).isEqualTo(usoLenguaje2);
        usoLenguaje2.setId(2L);
        assertThat(usoLenguaje1).isNotEqualTo(usoLenguaje2);
        usoLenguaje1.setId(null);
        assertThat(usoLenguaje1).isNotEqualTo(usoLenguaje2);
    }
}
