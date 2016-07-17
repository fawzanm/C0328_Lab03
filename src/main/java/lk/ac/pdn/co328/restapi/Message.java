package lk.ac.pdn.co328.restapi;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

	
	private String message;
public Message() {
}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
