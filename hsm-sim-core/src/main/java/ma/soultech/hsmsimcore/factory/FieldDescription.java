package ma.soultech.hsmsimcore.factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class FieldDescription {
    public static final char HEX='H';
    public static final char NUMBER='N';
    public static final char BINARY='B';
    public static final char TYPENOTDEFINED ='0';
    private char delimiter = TYPENOTDEFINED;
    private int length = 0;
    private boolean max = false;
    private char type = TYPENOTDEFINED;
    private boolean keyScheme = false;
    private String lengthFromServerConf = null;

    private String lengthFromField = null;
    private char lengthFromFieldType = TYPENOTDEFINED;

    public FieldDescription() {
    }

    public FieldDescription(char delimiter, int length, boolean max, char type, boolean keyScheme, String lengthFromServerConf) {
        this.delimiter = delimiter;
        this.length = length;
        this.max = max;
        this.type = type;
        this.keyScheme = keyScheme;
        this.lengthFromServerConf = lengthFromServerConf;
    }

    public char getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isMax() {
        return max;
    }

    public void setMax(boolean max) {
        this.max = max;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public boolean isKeyScheme() {
        return keyScheme;
    }

    public void setKeyScheme(boolean keyScheme) {
        this.keyScheme = keyScheme;
    }

    public String getLengthFromServerConf() {
        return lengthFromServerConf;
    }

    public void setLengthFromServerConf(String lengthFromServerConf) {
        this.lengthFromServerConf = lengthFromServerConf;
    }

    public String getLengthFromField() {
        return lengthFromField;
    }

    public void setLengthFromField(String lengthFromField) {
        this.lengthFromField = lengthFromField;
    }

    public char getLengthFromFieldType() {
        return lengthFromFieldType;
    }

    public void setLengthFromFieldType(char lengthFromFieldType) {
        this.lengthFromFieldType = lengthFromFieldType;
    }
}
