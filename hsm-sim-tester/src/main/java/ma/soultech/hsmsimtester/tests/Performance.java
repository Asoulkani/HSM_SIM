package ma.soultech.hsmsimtester.tests;

import ma.soultech.hsmsimtester.HsmSimTesterApplication;
import ma.soultech.hsmsimtester.services.TcpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class Performance {
    Logger log = LoggerFactory.getLogger(Performance.class);
    @Autowired
    TcpClientService tcpClientService;
    public boolean multipleRequestSameCmd(int repNbr, long maxMs){
        log.info("Performance Test Started with {} repetitions", repNbr);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < repNbr; i++) {
            byte[] request = "TESTCWU013151701020406191B0D0F180A1C1E04260000000500014;2112206".getBytes(StandardCharsets.UTF_8);
            tcpClientService.sendData(request);
        }
        long totMs = System.currentTimeMillis() - startTime;
        log.info("Performance Test Ended in {}ms",totMs);
        return (totMs < maxMs);
    }
}
