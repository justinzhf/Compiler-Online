package com.codeonline.action;
import ch.ethz.ssh2.Session;
import com.codeonline.publicString.PublicString;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by zhf on 2016/7/24.
 */
public class Input extends ActionSupport implements SessionAware {
    private String input;
    private Map<String, Object> session;


    @Override
    public String execute() {
        PrintWriter pw=(PrintWriter)session.get(PublicString.PRINTWRITER);

        pw.println(input);
        pw.flush();
        return SUCCESS;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session= session;
    }

}
