package ma.soultech;

import java.util.HexFormat;

public class HsmDataConverter {
    public static long hexToDecimal(String hex){
        return Long.parseLong(hex, 16);
    }

    public static long stringToDecimal(String s){
        return Long.parseLong(s, 10);
    }
    public static byte[] hexToByte(String s){ return HexFormat.of().parseHex(s); }
    public static String byteToHex(byte[] b){ return HexFormat.of().formatHex(b); }

    public static byte[] xor(byte[] b1, byte[] b2) {
        byte[] xor = new byte[b1.length];
        for (int i = 0; i < b1.length; i++) {
            xor[i] = (byte) ((int) b1[i] ^ (int) b2[i]);
        }
        return xor;
    }

    public static String printCommandFromByte(byte[] cmd)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String command = new String(cmd);
        for (int i = 0; i < command.length(); i++) {
            if (!((int) command.charAt(i) > 32 && (int) command.charAt(i) < 127))
                stringBuilder.append(".");
            else
                stringBuilder.append(command.charAt(i));
        }
        return stringBuilder.toString();
    }
}
