package ma.soultech.hsmsimcore.commands;

import ma.soultech.hsmsimcore.factory.FieldDescription;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

public class B2 extends Command{

    public B2() {
    }

    @Override
    public String execute(String cmd) {
        LinkedHashMap<String, String> values = parser(cmd);

        return "B300" + values.get("Data");
    }

    @Override
    public LinkedHashMap<String, FieldDescription> parserRules() {
        LinkedHashMap<String,FieldDescription> fields = new LinkedHashMap<>();
        FieldDescription fieldDescription;
        //==================== Length ====================
        fieldDescription = new FieldDescription();
        fieldDescription.setLength(4);
        fieldDescription.setType(FieldDescription.HEX);
        fields.put("Length", fieldDescription);
        //==================== Data ====================
        fieldDescription = new FieldDescription();
        fieldDescription.setLengthFromField("Length");
        fieldDescription.setLengthFromFieldType(FieldDescription.HEX);
        fieldDescription.setType(FieldDescription.HEX);
        fields.put("Data", fieldDescription);
        return fields;
    }

    @Override
    public Boolean isActive() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Echo command";
    }
}
