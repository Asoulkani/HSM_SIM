package ma.soultech.hsmsimcore;

import ma.soultech.hsmsimcore.commands.B2;
import ma.soultech.hsmsimcore.commands.CW;
import ma.soultech.hsmsimcore.commands.CY;
import ma.soultech.hsmsimcore.commands.Command;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Command CY(){ return new CY(); }
    @Bean
    public Command B2(){ return new B2(); }
    @Bean
    public Command CW(){ return new CW(); }
}
