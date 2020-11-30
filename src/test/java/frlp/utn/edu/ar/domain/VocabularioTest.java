package frlp.utn.edu.ar.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import frlp.utn.edu.ar.web.rest.TestUtil;

public class VocabularioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vocabulario.class);
        Vocabulario vocabulario1 = new Vocabulario();
        vocabulario1.setId(1L);
        Vocabulario vocabulario2 = new Vocabulario();
        vocabulario2.setId(vocabulario1.getId());
        assertThat(vocabulario1).isEqualTo(vocabulario2);
        vocabulario2.setId(2L);
        assertThat(vocabulario1).isNotEqualTo(vocabulario2);
        vocabulario1.setId(null);
        assertThat(vocabulario1).isNotEqualTo(vocabulario2);
    }
}
