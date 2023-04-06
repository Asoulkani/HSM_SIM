package ma.soultech;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testHexToDecimal(){
        String hex = "3FA6";
        long expectedDecimal = 16294l;
        assertEquals(expectedDecimal, HsmDataConverter.hexToDecimal(hex));
    }

    public void testStringToDecimal(){
        String s = "1000";
        long expectedDecimal = 1000l;
        assertEquals(expectedDecimal, HsmDataConverter.stringToDecimal(s));
    }

    public void testHexToByte(){
        String s = "deadbeef";
        byte[] expected = { (byte) 0xde, (byte) 0xad, (byte) 0xbe, (byte) 0xef };
        byte[] actual = HsmDataConverter.hexToByte(s);
        assertTrue(Arrays.equals(expected,actual));
    }
    public void testByteToHex(){
        String expected = "10001000";
        byte[] bytes = { (byte) 0x10, (byte) 0x00, (byte) 0x10, (byte) 0x00 };
        String actual = HsmDataConverter.byteToHex(bytes);
        assertEquals(expected, actual);
    }

    public void testXor(){
        byte[] b1 = { (byte) 0xE0, (byte) 0x01 };
        byte[] b2 = { (byte) 0xA0, (byte) 0x02 };
        byte[] expected = { (byte) 0x40, (byte) 0x03 };
        byte[] actual = HsmDataConverter.xor(b1, b2);
        assertTrue(Arrays.equals(expected,actual));
    }
}
