// generated from template 'DocHelperTest.ftl' on 2025-09-30T15:48:18.539+02:00
package test.org.fugerit.java.demo.venussamplevalidatingsource;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.demo.venussamplevalidatingsource.DocHelper;
import org.fugerit.java.demo.venussamplevalidatingsource.People;

import org.fugerit.java.doc.base.config.DocConfig;
import org.fugerit.java.doc.base.process.DocProcessContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * This is a basic example of Fugerit Venus Doc usage,
 * running this junit will :
 * - creates data to be used in document model
 * - renders the 'document.ftl' template
 * - print the result in markdown format
 *
 * For further documentation :
 * https://github.com/fugerit-org/fj-doc
 *
 * NOTE: This is a 'Hello World' style example, adapt it to your scenario, especially :
 *  - change the doc handler and the output mode (here a ByteArrayOutputStream buffer is used)
 */
@Slf4j
class DocHelperTest {

    private int testDocProcessWorker( boolean createFailCondition) throws Exception {
        try ( ByteArrayOutputStream baos = new ByteArrayOutputStream() ) {
            // creates the doc helper
            DocHelper docHelper = new DocHelper();
            // create custom data for the fremarker template 'document.ftl'
            List<People> listPeople = Arrays.asList( new People( "Luthien", "Tinuviel", "Queen" ), new People( "Thorin", "Oakshield", "King" ) );
            String chainId = "document";
            // handler id
            String handlerId = DocConfig.TYPE_XML;
            // output generation
            docHelper.getDocProcessConfig().fullProcess( chainId,
                    DocProcessContext.newContext( "listPeople", listPeople )
                            .withAtt( "createFailCondition",  createFailCondition),
                    handlerId, baos );
            // print the output
            log.info( "html output : \n{}", new String( baos.toByteArray(), StandardCharsets.UTF_8 ) );
            return baos.size();
        }
    }

    @Test
    void testDocProcessValidationOk() throws Exception {
        // generate a document with no errors
        Assertions.assertNotEquals( 0, this.testDocProcessWorker( Boolean.FALSE ) );
    }

    @Test
    void testDocProcessValidationFail() throws Exception {
        // generate a document with validation error and fail
        Assertions.assertThrows(ConfigRuntimeException.class, () -> this.testDocProcessWorker( Boolean.TRUE ) );
    }

}
