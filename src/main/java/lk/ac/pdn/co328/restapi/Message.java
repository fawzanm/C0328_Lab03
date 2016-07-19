package lk.ac.pdn.co328.restapi;

/**
 * Created by deshan on 7/19/16.
 */


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
    private String message;

    public Message(){}

    @XmlElement
    void setMessage(String message){
        this.message = message;
    }

    String getMessage(){
        return this.message;
    }

}
