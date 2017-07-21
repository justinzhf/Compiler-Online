package com.codeonline.action;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.codeonline.publicString.PublicString;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by zhf on 2016/8/3.
 */
public class Cleaner extends ActionSupport implements SessionAware {
    Map<String,Object> session;

    @Override
    public String execute(){
        try{
            PrintWriter pw=(PrintWriter)session.get(PublicString.PRINTWRITER);
            if(pw!=null){
                pw.close();
            }
            Session shellSession=(Session)session.get(PublicString.SHELLSESSION);
            if(shellSession!=null){
                shellSession.close();
                Session shellSession2=((Connection)session.get(PublicString.CONNECTION)).openSession();
                String sessionId=(String) ServletActionContext.getRequest().getRequestedSessionId();
                System.out.println("cd "+PublicString.SOURCEFILEPUBLICPATH+sessionId+";"+"rm *");
                shellSession2.execCommand("cd "+PublicString.SOURCEFILEPUBLICPATH+sessionId+";"+"rm *");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String,Object> session){
        this.session=session;
    }

}
