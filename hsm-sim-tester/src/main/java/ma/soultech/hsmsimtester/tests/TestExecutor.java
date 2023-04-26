package ma.soultech.hsmsimtester.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TestExecutor implements ApplicationRunner {

    @Autowired
    Performance performance;
    @Autowired
    Commands commands;

    private final Logger logger = LoggerFactory.getLogger(TestExecutor.class);

    private int nbrTest = 0;
    private int nbrTestOk = 0;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Tests Started");
        performanceTests();
        commandsTests();
        logger.info("{} executed Tests | OK : {} | NOK : {}",nbrTest, nbrTestOk, nbrTest - nbrTestOk);
    }

    public void performanceTests()
    {
        nbrTest += 1;
        if (performance.multipleRequestSameCmd(1000, 4500)) {
            nbrTestOk++;
        } else {
            logger.warn("Test failed: multipleRequestSameCmd, response time exceeded");
        }
    }

    public void commandsTests()
    {
        nbrTest += 4;
        if (commands.CW_OK()) {
            nbrTestOk++;
        } else {
            logger.warn("Test failed: CW_OK");
        }
        if (commands.CY_OK()) {
            nbrTestOk++;
        } else {
            logger.warn("Test failed: CY_OK");
        }
        if (commands.CY_NOK()) {
            nbrTestOk++;
        } else {
            logger.warn("Test failed: CY_NOK");
        }
        if (commands.B2_OK()) {
            nbrTestOk++;
        } else {
            logger.warn("Test failed: B2_OK");
        }
    }

}
