package ma.soultech.hsmsimtester.tests;

import ma.soultech.HsmDataConverter;
import ma.soultech.hsmsimtester.services.TcpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class Commands {
    Logger log = LoggerFactory.getLogger(Commands.class);
    @Autowired
    TcpClientService tcpClientService;
    public boolean CW_OK() {
        log.info("Command CW OK Test Started");
        long startTime = System.currentTimeMillis();
        byte[] request = "TESTCWU013151701020406191B0D0F180A1C1E04260000000500014;2112206".getBytes(StandardCharsets.UTF_8);
        byte[] response = tcpClientService.sendData(request);
        long totMs = System.currentTimeMillis() - startTime;
        log.info("Command CW OK Test Ended in {}ms", totMs);
        return (HsmDataConverter.printCommandFromByte(response).substring(6, 8).equals("00")
                && HsmDataConverter.printCommandFromByte(response).substring(8).equals("961"));
    }

    public boolean CY_OK() {
        log.info("Command CY OK Test Started");
        long startTime = System.currentTimeMillis();
        byte[] request = "TESTCYU013151701020406191B0D0F180A1C1E09614260000000500014;2112206".getBytes(StandardCharsets.UTF_8);
        byte[] response = tcpClientService.sendData(request);
        long totMs = System.currentTimeMillis() - startTime;
        log.info("Command CY OK Test Ended in {}ms", totMs);
        return (HsmDataConverter.printCommandFromByte(response).substring(6, 8).equals("00"));
    }

    public boolean CY_NOK() {
        log.info("Command CY NOK Test Started");
        long startTime = System.currentTimeMillis();
        byte[] request = "TESTCYU013151701020406191B0D0F180A1C1E09994260000000500014;2112206".getBytes(StandardCharsets.UTF_8);
        byte[] response = tcpClientService.sendData(request);
        long totMs = System.currentTimeMillis() - startTime;
        log.info("Command CY NOK Test Ended in {}ms", totMs);
        return (!HsmDataConverter.printCommandFromByte(response).substring(6, 8).equals("00"));
    }

    public boolean B2_OK() {
        log.info("Command B2 OK Test Started");
        long startTime = System.currentTimeMillis();
        byte[] request = "TESTB20004ECHO".getBytes(StandardCharsets.UTF_8);
        byte[] response = tcpClientService.sendData(request);
        long totMs = System.currentTimeMillis() - startTime;
        log.info("Command B2 OK Test Ended in {}ms", totMs);
        String responseString = HsmDataConverter.printCommandFromByte(response);
        return (responseString.substring(6, 8).equals("00") && responseString.substring(8).equals("ECHO"));
    }
}
