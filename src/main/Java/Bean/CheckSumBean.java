package Bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigInteger;

@Named
@SessionScoped
public class CheckSumBean implements Serializable {

    @Inject
    private CrcBean crcBean;

    @Inject
    private HammingBean hammingBean;

    private String message;
    private String ascii_message;
    private String messageHaveError;

    public String getMessageHaveError() {
        return messageHaveError;
    }

    public void setMessageHaveError(String messageHaveError) {
        this.messageHaveError = messageHaveError;
    }

    public CheckSumBean(){

    }

    @PostConstruct
    public void init(){
        hammingBean.setCrcBean(crcBean);
    }

    public HammingBean getHammingBean() {
        return hammingBean;
    }

    public void setHammingBean(HammingBean hammingBean) {
        this.hammingBean = hammingBean;
    }

    public CrcBean getCrcBean() {
        return crcBean;
    }

    public void setCrcBean(CrcBean crcBean) {
        this.crcBean = crcBean;
    }

    public String getAscii_message() {
        return ascii_message;
    }

    public void setAscii_message(String ascii_message) {
        this.ascii_message = ascii_message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void calcHammingAndCrc(AjaxBehaviorEvent event){
        if (ascii_message != null && !ascii_message.equals("")) {
            message = "0" + new BigInteger(ascii_message.getBytes()).toString(2);
            crcBean.calcCRC(message);
            hammingBean.calcHamming(crcBean.getMessageWithCrc());
        }else{
            message = "";
        }
    }

    public void calcHammingAndCrcError(AjaxBehaviorEvent event){
        hammingBean.calcHammingError(true);
        crcBean.calcCrcError();
        if(crcBean.getCrcOfErrorMessage().equals("No remainder")){
            messageHaveError = "Message have no error";
        }else{
            messageHaveError = "Message have errors";
        }
    }

    public static String addError(String _message, String errors) {
        if (errors != null) {
            if (!errors.equals("")) {
                StringBuilder bd = new StringBuilder();
                String[] er = errors.split(",");
                int[] errorIndex = new int[er.length];
                for (int i = 0; i < er.length; i++) {
                    errorIndex[i] = Integer.parseInt(er[i]);
                }
                boolean isError;
                for (int i = 0; i < _message.length(); i++) {
                    isError = false;
                    for (int j = 0; j < errorIndex.length; j++) {
                        if (errorIndex[j] == i) {
                            isError = true;
                            break;
                        }
                    }
                    if (isError) {
                        if (_message.charAt(i) == '0') {
                            bd.append('1');
                        } else if (_message.charAt(i) == '1') {
                            bd.append('0');
                        }
                    } else {
                        bd.append(_message.charAt(i));
                    }
                }
                return bd.toString();
            } else {
                return _message;
            }
        }
        return "";
    }


}
