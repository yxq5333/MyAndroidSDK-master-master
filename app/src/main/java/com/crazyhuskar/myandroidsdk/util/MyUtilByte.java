package com.crazyhuskar.myandroidsdk.util;

/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public class MyUtilByte {
    /**
     * byte数组大小数据格式互转
     *
     * @param src
     * @return
     */
    public static byte[] byteToByte(byte[] src) {
        byte[] data = new byte[src.length];
        for (int i = 0; i < src.length; i++) {
            data[i] = src[src.length - 1 - i];
        }
        return data;
    }

    /**
     * byte数组转hexString
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv.toUpperCase());
        }
        return stringBuilder.toString();
    }

    /**
     * 单个byte转hexString
     *
     * @param src
     * @return
     */
    public static String byteToHexString(byte src) {
        StringBuilder stringBuilder = new StringBuilder("");
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv.toUpperCase());
        return stringBuilder.toString();
    }

    /**
     * hexString转byte数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        char[] hexChars = transformArrayToEven(hexString.toCharArray());
        byte[] d = new byte[hexChars.length / 2];
        for (int i = 0; i < hexChars.length; i++) {
            byte b;
            if (i % 2 == 0) {
                b = (byte) (charToByte(hexChars[i]) << 4);
            } else {
                b = charToByte(hexChars[i]);
            }
            d[i / 2] += b;
        }
        return d;
    }

    private static char[] transformArrayToEven(char[] c) {
        if (c.length % 2 == 0) {
            return c;
        } else {
            char[] result = new char[c.length + 1];
            result[0] = '0';
            for (int i = 0; i < c.length; i++) {
                result[i + 1] = c[i];
            }
            return result;
        }
    }

    /**
     * char转byte
     *
     * @param c
     * @return
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * byte转char
     *
     * @param b
     * @return
     */
    public static char byteToChar(byte b) {
        return "0123456789ABCDEF".toCharArray()[b];
    }

    /**
     * byte数组转hexString（不足两位不补0）
     *
     * @param bt
     * @return
     */
    public static String getNumFromBytes(byte[] bt) {
        String str = "";
        for (int i = 0; i < bt.length; i++) {
            str += String.valueOf(bt[i]);
        }
        return str;
    }

    /**
     * int转byte数组
     *
     * @param value  数据源
     * @param length byte长度(1~4)
     * @return
     */
    public static byte[] intToBytes(int value, int length) {
        byte[] src = new byte[length];
        switch (length) {
            case 1:
                src[0] = (byte) (value & 0xFF);
                return src;
            case 2:
                src[0] = (byte) ((value >> 8) & 0xFF);
                src[1] = (byte) (value & 0xFF);
                return src;
            case 3:
                src[0] = (byte) ((value >> 16) & 0xFF);
                src[1] = (byte) ((value >> 8) & 0xFF);
                src[2] = (byte) (value & 0xFF);
                return src;
            case 4:
                src[0] = (byte) ((value >> 24) & 0xFF);
                src[1] = (byte) ((value >> 16) & 0xFF);
                src[2] = (byte) ((value >> 8) & 0xFF);
                src[3] = (byte) (value & 0xFF);
                return src;
            default:
                return src;
        }
    }

    /**
     * byte转int数组
     *
     * @param src    数据源
     * @param offset 起始位
     * @param length byte长度(1~4)
     * @return
     */
    public static int bytesToInt(byte[] src, int offset, int length) {
        int value = 0;
        switch (length) {
            case 1:
                value = (int) ((src[offset] & 0xFF));
                return value;
            case 2:
                value = (int) (((src[offset] & 0xFF) << 8) | (src[offset + 1] & 0xFF));
                return value;
            case 3:
                value = (int) (((src[offset] & 0xFF) << 16) | ((src[offset + 1] & 0xFF) << 8) | (src[offset + 2] & 0xFF));
                return value;
            case 4:
                value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16)
                        | ((src[offset + 2] & 0xFF) << 8) | (src[offset + 3] & 0xFF));
                return value;
            default:
                return value;
        }
    }

    /**
     * byte转int数组
     *
     * @param src 数据源
     * @return
     */
    public static int byteToInt(byte src) {
        int value = 0;
        value = (int) ((src & 0xFF));
        return value;
    }

    /**
     * 带符号位
     *
     * @param src
     * @param offset
     * @param length
     * @return
     */
    public static int bytesToInt_f(byte[] src, int offset, int length) {
        int j = MyUtilByte.bytesToInt(src, offset, length);
        int value = 0;
        switch (length) {
            case 1:
                value = j < 128 ? j : j - 128 * 2;
                return value;
            case 2:
                value = j < 32768 ? j : j - 32768 * 2;
                return value;
            case 3:
                value = j < 8388608 ? j : j - 8388608 * 2;
                return value;
            case 4:
                value = j <= 2147483647 ? j : j - (2147483647 + 1) * 2;
                return value;
            default:
                return value;
        }
    }

    /**
     * 带符号位
     *
     * @param src
     * @return
     */
    public static int byteToInt_f(byte src) {
        int j = MyUtilByte.byteToInt(src);
        int value = 0;
        value = j < 128 ? j : j - 128 * 2;
        return value;
    }

    /**
     * 异或计算
     *
     * @param bytes
     * @param begin (byte[begin])从0开始
     * @param end   (byte[end])从0开始
     * @return
     */
    public static byte getXor(byte[] bytes, int begin, int end) {
        byte xor = bytes[begin];
        for (int i = begin + 1; i <= end; i++) {
            xor ^= bytes[i];
        }
        return xor;

    }

    /**
     * 异或验证
     *
     * @param bytes 必填
     * @param begin 异或开始位
     * @param end   异或结束位
     * @param xor   异或验证位
     * @return
     */
    public static boolean isXor(byte[] bytes, int begin, int end, int xor) {
        if (bytes.length > 0) {
            byte t = getXor(bytes, begin, end);
            byte i = bytes[xor];
            return t == i;
        } else {
            return false;
        }
    }

    private static char[] crctab16 = new char[]{0X0000, 0X1189, 0X2312, 0X329B, 0X4624, 0X57AD, 0X6536, 0X74BF,
            0X8C48, 0X9DC1, 0XAF5A, 0XBED3, 0XCA6C, 0XDBE5, 0XE97E, 0XF8F7, 0X1081, 0X0108, 0X3393, 0X221A, 0X56A5,
            0X472C, 0X75B7, 0X643E, 0X9CC9, 0X8D40, 0XBFDB, 0XAE52, 0XDAED, 0XCB64, 0XF9FF, 0XE876, 0X2102, 0X308B,
            0X0210, 0X1399, 0X6726, 0X76AF, 0X4434, 0X55BD, 0XAD4A, 0XBCC3, 0X8E58, 0X9FD1, 0XEB6E, 0XFAE7, 0XC87C,
            0XD9F5, 0X3183, 0X200A, 0X1291, 0X0318, 0X77A7, 0X662E, 0X54B5, 0X453C, 0XBDCB, 0XAC42, 0X9ED9, 0X8F50,
            0XFBEF, 0XEA66, 0XD8FD, 0XC974, 0X4204, 0X538D, 0X6116, 0X709F, 0X0420, 0X15A9, 0X2732, 0X36BB, 0XCE4C,
            0XDFC5, 0XED5E, 0XFCD7, 0X8868, 0X99E1, 0XAB7A, 0XBAF3, 0X5285, 0X430C, 0X7197, 0X601E, 0X14A1, 0X0528,
            0X37B3, 0X263A, 0XDECD, 0XCF44, 0XFDDF, 0XEC56, 0X98E9, 0X8960, 0XBBFB, 0XAA72, 0X6306, 0X728F, 0X4014,
            0X519D, 0X2522, 0X34AB, 0X0630, 0X17B9, 0XEF4E, 0XFEC7, 0XCC5C, 0XDDD5, 0XA96A, 0XB8E3, 0X8A78, 0X9BF1,
            0X7387, 0X620E, 0X5095, 0X411C, 0X35A3, 0X242A, 0X16B1, 0X0738, 0XFFCF, 0XEE46, 0XDCDD, 0XCD54, 0XB9EB,
            0XA862, 0X9AF9, 0X8B70, 0X8408, 0X9581, 0XA71A, 0XB693, 0XC22C, 0XD3A5, 0XE13E, 0XF0B7, 0X0840, 0X19C9,
            0X2B52, 0X3ADB, 0X4E64, 0X5FED, 0X6D76, 0X7CFF, 0X9489, 0X8500, 0XB79B, 0XA612, 0XD2AD, 0XC324, 0XF1BF,
            0XE036, 0X18C1, 0X0948, 0X3BD3, 0X2A5A, 0X5EE5, 0X4F6C, 0X7DF7, 0X6C7E, 0XA50A, 0XB483, 0X8618, 0X9791,
            0XE32E, 0XF2A7, 0XC03C, 0XD1B5, 0X2942, 0X38CB, 0X0A50, 0X1BD9, 0X6F66, 0X7EEF, 0X4C74, 0X5DFD, 0XB58B,
            0XA402, 0X9699, 0X8710, 0XF3AF, 0XE226, 0XD0BD, 0XC134, 0X39C3, 0X284A, 0X1AD1, 0X0B58, 0X7FE7, 0X6E6E,
            0X5CF5, 0X4D7C, 0XC60C, 0XD785, 0XE51E, 0XF497, 0X8028, 0X91A1, 0XA33A, 0XB2B3, 0X4A44, 0X5BCD, 0X6956,
            0X78DF, 0X0C60, 0X1DE9, 0X2F72, 0X3EFB, 0XD68D, 0XC704, 0XF59F, 0XE416, 0X90A9, 0X8120, 0XB3BB, 0XA232,
            0X5AC5, 0X4B4C, 0X79D7, 0X685E, 0X1CE1, 0X0D68, 0X3FF3, 0X2E7A, 0XE70E, 0XF687, 0XC41C, 0XD595, 0XA12A,
            0XB0A3, 0X8238, 0X93B1, 0X6B46, 0X7ACF, 0X4854, 0X59DD, 0X2D62, 0X3CEB, 0X0E70, 0X1FF9, 0XF78F, 0XE606,
            0XD49D, 0XC514, 0XB1AB, 0XA022, 0X92B9, 0X8330, 0X7BC7, 0X6A4E, 0X58D5, 0X495C, 0X3DE3, 0X2C6A, 0X1EF1,
            0X0F78,};

    /**
     * 计算给定长度数据的16位CRC。
     *
     * @param bytes
     * @param begin
     * @param end
     * @return
     */
    public static byte[] getCrc16(byte[] bytes, int begin, int end) {
        char fcs = 0xffff; // 初始化
        int k = begin;
        int nLength = end - begin + 1;
        while (nLength > 0) {
            fcs = (char) ((fcs >> 8) ^ crctab16[(fcs ^ bytes[k++]) & 0xff]);
            nLength--;
        }
        fcs = (char) (~fcs);// 取反
        byte[] crc = new byte[2];
        crc[0] = (byte) (fcs & 0x00ff);
        crc[1] = (byte) ((fcs & 0xff00) >> 8);
        return crc;
    }

    public static boolean isCrc16(byte[] bytes, int begin, int end, int crcbegin, int crcend) {
        if (bytes.length > 0) {
            byte[] t = getCrc16(bytes, begin, end);
            return bytes[crcbegin] == t[0] && bytes[crcend] == t[1];
        } else {
            return false;
        }
    }
}
