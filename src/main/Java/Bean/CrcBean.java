package Bean;

import Enums.CRC_Type;
import Helper.CRC;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigInteger;

@Named
@SessionScoped
public class CrcBean implements Serializable {

    private String messageWithCrc;
    private String crcChecksum;
    private String polymonial;
    private String check_crc;
    private String crcOfErrorMessage;
    private String crcErrors;
    private String messageWithErrorsCrc;
    private String asciiWithErrorsCrc;
    private int messageWithCrcLength;
    private CRC_Type crc_type = CRC_Type.CRC32;

    public String getMessageWithCrc() {
        return messageWithCrc;
    }

    public void setMessageWithCrc(String messageWithCrc) {
        this.messageWithCrc = messageWithCrc;
    }

    public String getCrcChecksum() {
        return crcChecksum;
    }

    public void setCrcChecksum(String crcChecksum) {
        this.crcChecksum = crcChecksum;
    }

    public String getPolymonial() {
        return polymonial;
    }

    public void setPolymonial(String polymonial) {
        this.polymonial = polymonial;
    }

    public String getCheck_crc() {
        return check_crc;
    }

    public void setCheck_crc(String check_crc) {
        this.check_crc = check_crc;
    }

    public String getCrcOfErrorMessage() {
        return crcOfErrorMessage;
    }

    public void setCrcOfErrorMessage(String crcOfErrorMessage) {
        this.crcOfErrorMessage = crcOfErrorMessage;
    }

    public String getCrcErrors() {
        return crcErrors;
    }

    public void setCrcErrors(String crcErrors) {
        this.crcErrors = crcErrors;
    }

    public String getMessageWithErrorsCrc() {
        return messageWithErrorsCrc;
    }

    public void setMessageWithErrorsCrc(String messageWithErrorsCrc) {
        this.messageWithErrorsCrc = messageWithErrorsCrc;
    }

    public String getAsciiWithErrorsCrc() {
        return asciiWithErrorsCrc;
    }

    public void setAsciiWithErrorsCrc(String asciiWithErrorsCrc) {
        this.asciiWithErrorsCrc = asciiWithErrorsCrc;
    }

    public CRC_Type getCrc_type() {
        return crc_type;
    }

    public void setCrc_type(CRC_Type crc_type) {
        this.crc_type = crc_type;
    }

    public CRC_Type[] getCrc_types() {
        return CRC_Type.values();
    }

    public CrcBean(){

    }

    public void calcCrcError(){
        if(messageWithErrorsCrc.length() != 0) {
            asciiWithErrorsCrc = new String(new BigInteger(messageWithErrorsCrc.substring(0, messageWithErrorsCrc.length() - crcChecksum.length()), 2).toByteArray());
            crcOfErrorMessage = CRC.checkcRC(messageWithErrorsCrc, crc_type);
        }
    }

    public void calcCRC(String _message){
        polymonial = CRC.getPolymonial(crc_type);
        crcChecksum = CRC.calculateCRC(_message, crc_type);
        messageWithCrc = _message + crcChecksum;
        messageWithCrcLength = messageWithCrc.length();
        check_crc = CRC.checkcRC(messageWithCrc, crc_type);
    }

}
