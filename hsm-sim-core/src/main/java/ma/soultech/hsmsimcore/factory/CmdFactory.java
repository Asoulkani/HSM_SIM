package ma.soultech.hsmsimcore.factory;

import ma.soultech.hsmsimcore.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CmdFactory {
    public CmdFactory() {
        System.out.println("Factory scanned");
    }

    @Autowired
    ApplicationContext applicationContext;

    public Command getCmd(String name){
        return (Command) applicationContext.getBean(name);
    }
}
