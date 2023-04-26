package ma.soultech.hsmsimtester.tests;

import ma.soultech.HsmDataConverter;
import ma.soultech.hsmsimtester.services.TcpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class Scenarios {
    Logger log = LoggerFactory.getLogger(Commands.class);
    @Autowired
    TcpClientService tcpClientService;

    public boolean scnCvvGenVerify(){
        log.info("scnCvvGenVerify scene Started");
        String header = "TEST";
        String key = "U013151701020406191B0D0F180A1C1E0";
        String pan = "4260000008000014";
        String expDate = "2112";
        String serviceCode = "206";
        long startTime = System.currentTimeMillis();
        byte[] request = String.format("%sCW%s%s;%s%s", header, key, pan, expDate, serviceCode)
                            .getBytes(StandardCharsets.UTF_8);
        byte[] response = tcpClientService.sendData(request);
        String generatedCvv = HsmDataConverter.printCommandFromByte(response).substring(8);
        request = String.format("%sCY%s%s%s;%s%s", header, key, generatedCvv, pan, expDate, serviceCode)
                            .getBytes(StandardCharsets.UTF_8);
        response = tcpClientService.sendData(request);
        long totMs = System.currentTimeMillis() - startTime;
        log.info("scnCvvGenVerify scene Ended in {}ms", totMs);
        return (HsmDataConverter.printCommandFromByte(response).substring(6, 8).equals("00"));
    }
}
