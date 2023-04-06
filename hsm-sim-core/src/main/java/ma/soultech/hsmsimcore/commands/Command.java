package ma.soultech.hsmsimcore.commands;

import ma.soultech.HsmDataConverter;
import ma.soultech.hsmsimcore.factory.FieldDescription;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Command {
    public abstract String execute(String cmd);
    public abstract LinkedHashMap<String, FieldDescription> parserRules();
    public abstract Boolean isActive();
    public abstract String getDescription();

    public LinkedHashMap<String,String> parser(String cmd){
        LinkedHashMap<String, String> values = new LinkedHashMap<>();
        String command = cmd;

        for (Map.Entry<String, FieldDescription> entry : parserRules().entrySet()) {
            FieldDescription fieldDescription = entry.getValue();
            if(fieldDescription.getType() == FieldDescription.HEX || fieldDescription.getType() == FieldDescription.NUMBER)
            {
                if(fieldDescription.isKeyScheme())
                {
                    values.put(entry.getKey(),command.substring(1, fieldDescription.getLength() + 1));
                    command = command.substring(fieldDescription.getLength() + 1);
                } else if (fieldDescription.isMax()) {
                    int delimiterIndex = command.indexOf(fieldDescription.getDelimiter());
                    if(delimiterIndex == -1)
                    {
                        values.put(entry.getKey(),command.substring(0, fieldDescription.getLength()));
                        command = command.substring(fieldDescription.getLength());
                    }
                    else
                    {
                        values.put(entry.getKey(),command.substring(0, delimiterIndex));
                        command = command.substring(delimiterIndex + 1);
                    }
                } else if(StringUtils.hasText(fieldDescription.getLengthFromField())){
                    int length = (int) ((fieldDescription.getLengthFromFieldType() == FieldDescription.HEX)
                            ? HsmDataConverter.hexToDecimal(values.get(fieldDescription.getLengthFromField()))
                            : HsmDataConverter.stringToDecimal(values.get(fieldDescription.getLengthFromField())));
                    values.put(entry.getKey(),command.substring(0, length));
                    command = command.substring(length);
                } else {
                    values.put(entry.getKey(),command.substring(0, fieldDescription.getLength()));
                    command = command.substring(fieldDescription.getLength());
                }

            }
            else if(fieldDescription.getType() == FieldDescription.BINARY)
            {
                //TO DO
            }
        }

        return values;
    }
}
