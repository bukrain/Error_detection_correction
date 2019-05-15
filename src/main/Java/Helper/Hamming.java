package Helper;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class Hamming {


    public static List<Integer> getIndexesPowerofTwo(String message){
        List<Integer> indexPowerOfTwo = new LinkedList<>();
        for(int i =0; i< message.length(); i++){
            int j = i+1;
            if(j > 0 && (j & (j -1)) == 0){
                indexPowerOfTwo.add(i);
            }
        }
        return indexPowerOfTwo;
    }

    public static String[] calcParities(String _message, boolean setIndexes, List<Integer> indexPowerOfTwo){
        StringBuilder bd = new StringBuilder(_message);
        if(setIndexes){
            for(int i =0; i< indexPowerOfTwo.size(); i++){
                bd.insert(indexPowerOfTwo.get(i), "0");
            }
        }

        String[] indexBit = new String[bd.length()];
        for(int i =0; i< bd.length(); i++){
            indexBit[i] = Integer.toBinaryString(i+1);
        }
        String[] parities = new String[indexPowerOfTwo.size()];
        for(int i =0; i< indexPowerOfTwo.size(); i++){
            parities[i] = getParity(indexBit, i, bd.toString(), indexPowerOfTwo);
        }
        return parities;
    }

    private static String getParity(String[] indexBit, int index, String _message, List<Integer> indexPowerOfTwo){
        int sum = 0;
        for(int i =0; i< indexBit.length; i++){
            if(indexBit[i].length() > index){
                char temp = indexBit[i].charAt(indexBit[i].length() - index - 1 );
                if( temp == '1' && !indexPowerOfTwo.contains(i)){
                    if(_message.charAt(i) == '1') {
                        sum++;
                    }
                }
            }
        }

        if(sum % 2 == 0){
            return "1";
        }else{
            return "0";
        }
    }

    public static String extractMessage(String _message, boolean isWithCrc, List<Integer> indexPowerOfTwo, int polymonialLength){
        StringBuilder bd = new StringBuilder();
        bd.append(_message, 0, indexPowerOfTwo.get(0));
        for(int i =1; i< indexPowerOfTwo.size();i++){
            bd.append(_message,indexPowerOfTwo.get(i-1) + 1,indexPowerOfTwo.get(i));
        }
        bd.append(_message.substring(indexPowerOfTwo.get(indexPowerOfTwo.size()-1)+1));
        if(isWithCrc){
            return bd.substring(0,bd.length() - polymonialLength + 1);
        }
        return bd.toString();
    }
}
