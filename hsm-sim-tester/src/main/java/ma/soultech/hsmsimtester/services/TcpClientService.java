package ma.soultech.hsmsimtester.services;

import ma.soultech.HsmDataConverter;
import ma.soultech.hsmsimtester.config.TcpClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TcpClientService {
    private static final Logger log = LoggerFactory.getLogger(TcpClientService.class);
    @Autowired
    TcpClientConfig.TcpClientGateway tcpClientGateway;

    public byte[] sendData(byte[] data){
        log.info("Request sent : [{}]", HsmDataConverter.printCommandFromByte(data));
        long startTime = System.currentTimeMillis();
        byte[] response = tcpClientGateway.send(data);
        log.info("Response time: {} ms", (System.currentTimeMillis() - startTime));
        log.info("Response : [{}]", HsmDataConverter.printCommandFromByte(response));
        return  response;
    }
}
/*
    package
            config
                    TcpClientConfig
            services
                    TcpClientService
            tests
                    Performance
                    TestLauncher
            HsmSimTesterApplication
 */
