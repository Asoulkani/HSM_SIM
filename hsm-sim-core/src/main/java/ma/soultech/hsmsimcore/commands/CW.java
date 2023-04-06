package ma.soultech.hsmsimcore.commands;

import ma.soultech.hsmsimcore.algo.cvv.Cvv;
import ma.soultech.hsmsimcore.factory.FieldDescription;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;

public class CW extends Command {
    @Autowired
    Cvv cvv;
    @Override
    public String execute(String cmd) {
        LinkedHashMap<String, String> values = parser(cmd);
        String generatedCvv;
        try {
            generatedCvv = cvv.cvvGeneration(values.get("PAN"), values.get("Expiration date"), values.get("Service code"), values.get("CVK A/B"));
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return "CX00" + generatedCvv;
    }

    @Override
    public LinkedHashMap<String, FieldDescription> parserRules() {
        LinkedHashMap<String,FieldDescription> fields = new LinkedHashMap<>();
        FieldDescription fieldDescription;
        //==================== CVK A/B ====================
        fieldDescription = new FieldDescription();
        fieldDescription.setLength(32);
        fieldDescription.setKeyScheme(true);
        fieldDescription.setType(FieldDescription.HEX);
        fields.put("CVK A/B", fieldDescription);
        //==================== PAN ====================
        fieldDescription = new FieldDescription();
        fieldDescription.setLength(19);
        fieldDescription.setMax(true);
        fieldDescription.setDelimiter(';');
        fieldDescription.setType(FieldDescription.NUMBER);
        fields.put("PAN", fieldDescription);
        //==================== Expiration date ====================
        fieldDescription = new FieldDescription();
        fieldDescription.setLength(4);
        fieldDescription.setType(FieldDescription.NUMBER);
        fields.put("Expiration date", fieldDescription);
        //==================== Service code ====================
        fieldDescription = new FieldDescription();
        fieldDescription.setLength(3);
        fieldDescription.setType(FieldDescription.NUMBER);
        fields.put("Service code", fieldDescription);

        return fields;
    }

    @Override
    public Boolean isActive() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Verify a VISA CVV";
    }
}
