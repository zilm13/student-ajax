package it._7bits.web.student.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Object which will play cookie role in app
 */
@Component
@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class Cookah {
    private String returnPath;
    private String returnUri;
    private String redMessage;
    private String greenMessage;

    public Cookah() {}

    public String getReturnPath() {
        return returnPath;
    }

    public void setReturnPath(String returnPath) {
        this.returnPath = returnPath;
    }

    public String getRedMessage() {
        return redMessage;
    }

    public void setRedMessage(String redMessage) {
        this.redMessage = redMessage;
    }

    public String getGreenMessage() {
       return greenMessage;
    }

    public void setGreenMessage(String greenMessage) {
        this.greenMessage = greenMessage;
    }

    public String getReturnUri() {
        return returnUri;
    }

    public void setReturnUri(String returnUri) {
        this.returnUri = returnUri;
    }
}
