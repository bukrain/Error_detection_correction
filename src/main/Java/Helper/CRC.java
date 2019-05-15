package Helper;

import Enums.CRC_Type;

public class CRC {

    public static String calculateCRC(String binary, CRC_Type crc_type){
        String polymonial = getPolymonial(crc_type);
        String temp_binary = addZeros(binary, polymonial);
        StringBuilder remaining = new StringBuilder(div(temp_binary, polymonial));
        int l = polymonial.length() - 1 - remaining.length();
        for(int i =0; i < l; i++){
            remaining.insert(0,'0');
        }
        return  remaining.toString();
    }

    private static String addZeros(String binary, String polymonial){
        StringBuilder temp_binary = new StringBuilder(binary);
        for(int i =0; i< polymonial.length() -1; i++){
            temp_binary.append('0');
        }
        return temp_binary.toString();
    }

    public static String checkcRC(String binary, CRC_Type crc_type){
        String polymonial = getPolymonial(crc_type);
        String temp_binary = addZeros(binary, polymonial);
        String rem = div(temp_binary, polymonial);
        boolean isZero = true;
        for(int i =0; i < rem.length(); i++){
            if(rem.charAt(i) == '1'){
                isZero = false;
            }
        }

        if(isZero){
            return "No remainder";
        }else{
            return rem;
        }
    }

    public static String getPolymonial(CRC_Type crc_type){
        String crc_value = "";

        switch (crc_type){
            case CRC_5_EPC:
                crc_value = "1001";
                break;
            case CRC8:
                crc_value = "11010101";
                break;
            case CRC12:
                crc_value = "100000001111";
                break;
            case CRC16:
                crc_value = "1000000000000101";
                break;
            case CRC16_REVERSE:
                crc_value = "1010000000000001";
                break;
            case CRC24:
                crc_value = "100001100100110011111011";
                break;
            case CRC32:
                crc_value = "100110000010001110110110111";
                break;
            case SDLC:
                crc_value = "1000000100001";
                break;
            case SDLC_REVERSE:
                crc_value = "1000010000001000";
                break;
            case CRC_ITU:
                crc_value = "1000000100001";
                break;
            case ATM:
                crc_value = "111";
                break;
        }

        return crc_value;
    }

    public static String div(String divident, String divisor) {
        int old_length = divident.length() - divisor.length();
        int index;
        StringBuilder temp = new StringBuilder(divident);
        while (temp.substring(0,old_length+1).contains("1")){
            index = temp.indexOf("1");
            for(int i=0; i< divisor.length(); i++){
                temp.setCharAt(index+i,Character.forDigit((divisor.charAt(i) != temp.charAt(index +i) ? 1 : 0), 2));
            }
            divident = temp.toString();
        }

        return divident.substring(old_length+1);
    }

}
