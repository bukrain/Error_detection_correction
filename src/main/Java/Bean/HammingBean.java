package Bean;

import Helper.Hamming;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Named
@SessionScoped
public class HammingBean implements Serializable {

    private CrcBean crcBean;
    private String messageWithHammingCode;
    private String extractedMessage;
    private String messageWithErrors;
    private String hammingErrors;
    private String asciiErrored;
    private String asciiAfterCorrection;
    private String messageAfterCorrection;
    private List<Integer> indexPowerOfTwo;
    private String strIndexOfError;
    private int indexOfError;

    public CrcBean getCrcBean() {
        return crcBean;
    }

    public void setCrcBean(CrcBean crcBean) {
        this.crcBean = crcBean;
    }

    public String getStrIndexOfError() {
        return strIndexOfError;
    }

    public void setStrIndexOfError(String strIndexOfError) {
        this.strIndexOfError = strIndexOfError;
    }

    public String getAsciiAfterCorrection() {
        return asciiAfterCorrection;
    }

    public void setAsciiAfterCorrection(String asciiAfterCorrection) {
        this.asciiAfterCorrection = asciiAfterCorrection;
    }

    public String getMessageWithHammingCode() {
        return messageWithHammingCode;
    }

    public void setMessageWithHammingCode(String messageWithHammingCode) {
        this.messageWithHammingCode = messageWithHammingCode;
    }

    public String getExtractedMessage() {
        return extractedMessage;
    }

    public void setExtractedMessage(String extractedMessage) {
        this.extractedMessage = extractedMessage;
    }

    public String getMessageWithErrors() {
        return messageWithErrors;
    }

    public void setMessageWithErrors(String messageWithErrors) {
        this.messageWithErrors = messageWithErrors;
    }

    public String getHammingErrors() {
        return hammingErrors;
    }

    public void setHammingErrors(String hammingErrors) {
        this.hammingErrors = hammingErrors;
    }

    public String getAsciiErrored() {
        return asciiErrored;
    }

    public void setAsciiErrored(String asciiErrored) {
        this.asciiErrored = asciiErrored;
    }

    public String getMessageAfterCorrection() {
        return messageAfterCorrection;
    }

    public void setMessageAfterCorrection(String messageAfterCorrection) {
        this.messageAfterCorrection = messageAfterCorrection;
    }

    public List<Integer> getIndexPowerOfTwo() {
        return indexPowerOfTwo;
    }

    public void setIndexPowerOfTwo(List<Integer> indexPowerOfTwo) {
        this.indexPowerOfTwo = indexPowerOfTwo;
    }

    public HammingBean(){

    }

    public void calcHamming(String _message){
        indexPowerOfTwo = Hamming.getIndexesPowerofTwo(_message);
        String[] parities = Hamming.calcParities(_message,true, indexPowerOfTwo);
        StringBuilder bd = new StringBuilder(_message);
        for(int i =0; i< indexPowerOfTwo.size(); i++){
            bd.insert(indexPowerOfTwo.get(i), parities[i]);
        }
        messageWithHammingCode = bd.toString();
    }

    public void calcHammingError(boolean isWithCrc){
        messageWithErrors = CheckSumBean.addError(messageWithHammingCode, hammingErrors);
        extractedMessage = Hamming.extractMessage(messageWithErrors, isWithCrc, indexPowerOfTwo, crcBean.getPolymonial().length());
        String _extracted_message = Hamming.extractMessage(messageWithErrors, false, indexPowerOfTwo, crcBean.getPolymonial().length());
        crcBean.setMessageWithErrorsCrc(_extracted_message);
        asciiErrored = new String(new BigInteger(extractedMessage, 2).toByteArray());
        findAndCorrecterror(isWithCrc);
    }

    private void findAndCorrecterror(boolean isWithCrc){
        indexOfError = findError() -1;
        if(indexOfError >=0) {
            strIndexOfError = Integer.toString(indexOfError);
            correctError(indexOfError, isWithCrc);
        }else{
            strIndexOfError = "No error";
            asciiErrored = "No errors found";
        }
    }

    private int findError(){
        String[] parities = Hamming.calcParities(messageWithErrors, false, indexPowerOfTwo);
        int errorSum = 0;
        for(int i =0; i< parities.length; i++){
            if(parities[i].charAt(0) != messageWithErrors.charAt(indexPowerOfTwo.get(i))){
                errorSum += indexPowerOfTwo.get(i)+1;
            }
        }
        return errorSum;
    }

    private void correctError(int errorIndex, boolean isWithCrc){
        StringBuilder bd = new StringBuilder(messageWithErrors);
        if(messageWithErrors.charAt(errorIndex) == '1'){
            bd.setCharAt(errorIndex,'0');
        }else{
            bd.setCharAt(errorIndex,'1');
        }
        messageAfterCorrection = bd.toString();
        String _extracted_message = Hamming.extractMessage(messageAfterCorrection, false, indexPowerOfTwo, crcBean.getPolymonial().length());
        crcBean.setMessageWithErrorsCrc(_extracted_message);
        asciiAfterCorrection = new String(new BigInteger(Hamming.extractMessage(messageAfterCorrection,isWithCrc, indexPowerOfTwo, crcBean.getPolymonial().length()), 2).toByteArray());
    }
}
