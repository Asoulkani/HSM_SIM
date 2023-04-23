package ma.soultech.hsmsimcore.factory;

import ma.soultech.hsmsimcore.commands.Command;
import org.slf4j.Logger;

import java.util.LinkedHashMap;

public class CmdUtils {

    public static String printCmdListFields(LinkedHashMap<String, String> fields, String commandType){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append("command : " + commandType);
        fields.entrySet().stream().forEach(stringStringEntry -> stringBuilder.append(stringStringEntry.getKey() + " : " + stringStringEntry.getValue() + "\n"));
        return stringBuilder.toString();
    }

}
