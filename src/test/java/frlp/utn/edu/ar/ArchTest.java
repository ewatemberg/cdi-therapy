package frlp.utn.edu.ar;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("frlp.utn.edu.ar");

        noClasses()
            .that()
                .resideInAnyPackage("frlp.utn.edu.ar.service..")
            .or()
                .resideInAnyPackage("frlp.utn.edu.ar.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..frlp.utn.edu.ar.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
