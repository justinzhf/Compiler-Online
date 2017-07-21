package com.codeonline.action;

import ch.ethz.ssh2.*;
import com.codeonline.Container.WebsocketSessionContainer;
import com.codeonline.file.SourceFile;
import com.codeonline.language.Language;
import com.codeonline.language.LanguageC;
import com.codeonline.language.LanguageJava;
import com.codeonline.language.LanguagePy;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import com.codeonline.publicString.PublicString;

import java.io.*;
import java.util.Map;

/**
 * Created by zhf on 2016/7/23.
 */
public class Run extends ActionSupport implements SessionAware {
    private String code;
    private Map<String, Object> session;
    private String language;
    private Connection conn;


    /*制造一个shellSession，存放到httpSession中
    * 刷新、关闭标签页时需要将conn、shellsession、pw均关闭
    *
    * */
    private void makeSession() {
        Session shellSession = (Session) session.get(PublicString.SHELLSESSION);
        conn = (Connection) session.get(PublicString.CONNECTION);
        PrintWriter pw = (PrintWriter) session.get(PublicString.PRINTWRITER);
        String sessionId = ServletActionContext.getRequest().getRequestedSessionId();
        if (shellSession == null) {
            try {
                System.out.println("shellsession is null");
                conn = new Connection(PublicString.SSH_SERVER_ADDRESS);
                conn.connect();
                boolean result = conn.authenticateWithPassword(PublicString.USERNAME, PublicString.PASSWORD);
                System.out.println(result);
                shellSession = conn.openSession();
                pw = new PrintWriter(shellSession.getStdin());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                pw.close();
                shellSession.close();//该shell下所执行的所有进程也被终止

                shellSession = conn.openSession();
                pw = new PrintWriter(shellSession.getStdin());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        session.put(PublicString.SHELLSESSION, shellSession);
        session.put(PublicString.PRINTWRITER, pw);
        session.put(PublicString.CONNECTION, conn);
    }


    @Override
    public String execute() {
        try {
            makeSession();
            final Session shellSession = (Session) session.get(PublicString.SHELLSESSION);
            final String sessionId = ServletActionContext.getRequest().getRequestedSessionId();

            System.out.println(language);
            if (language.equals("java")) {
                try {
                    SFTPv3Client sc = new SFTPv3Client(conn);
                    sc.mkdir(PublicString.SOURCEFILEPUBLICPATH + sessionId, 755);
                    SFTPv3FileHandle sf = sc.createFileTruncate(PublicString.SOURCEFILEPUBLICPATH + sessionId + "/" + "CodeOnline.java");
                    sc.write(sf, 0, code.getBytes(), 0, code.getBytes().length);
                    sc.closeFile(sf);
                    sc.close();
                } catch (SFTPException e) {
                    System.out.println("an SFTPException in Run");
                    SFTPv3Client sc = new SFTPv3Client(conn);
                    SFTPv3FileHandle sf = sc.createFileTruncate(PublicString.SOURCEFILEPUBLICPATH + sessionId + "/" + "CodeOnline.java");
                    sc.write(sf, 0, code.getBytes(), 0, code.getBytes().length);
                    sc.closeFile(sf);
                    sc.close();
                }

                Language languageJava = new LanguageJava();
                String command = languageJava.getRunCommand(PublicString.SOURCEFILEPUBLICPATH + sessionId, "CodeOnline", "java");
                System.out.println(command);
                shellSession.execCommand(command);

            } else if (language.equals("c/c++")) {

                try {
                    SFTPv3Client sc = new SFTPv3Client(conn);
                    sc.mkdir(PublicString.SOURCEFILEPUBLICPATH + sessionId, 755);
                    SFTPv3FileHandle sf = sc.createFileTruncate(PublicString.SOURCEFILEPUBLICPATH + sessionId + "/" + "CodeOnline.c");
                    sc.write(sf, 0, code.getBytes(), 0, code.getBytes().length);
                    sc.closeFile(sf);
                    sc.close();
                } catch (SFTPException e) {
                    System.out.println("an SFTPException in Run");
                    SFTPv3Client sc = new SFTPv3Client(conn);
                    SFTPv3FileHandle sf = sc.createFileTruncate(PublicString.SOURCEFILEPUBLICPATH + sessionId + "/" + "CodeOnline.c");
                    sc.write(sf, 0, code.getBytes(), 0, code.getBytes().length);
                    sc.closeFile(sf);
                    sc.close();
                }

                Language languageC = new LanguageC();
                String command = languageC.getRunCommand(PublicString.SOURCEFILEPUBLICPATH + sessionId, "CodeOnline", "c");
                System.out.println(command);
                shellSession.execCommand(command);

            } else if (language.equals("python")) {
                try {
                    SFTPv3Client sc = new SFTPv3Client(conn);
                    sc.mkdir(PublicString.SOURCEFILEPUBLICPATH + sessionId, 755);
                    SFTPv3FileHandle sf = sc.createFileTruncate(PublicString.SOURCEFILEPUBLICPATH + sessionId + "/" + "CodeOnline.py");
                    sc.write(sf, 0, code.getBytes(), 0, code.getBytes().length);
                    sc.closeFile(sf);
                    sc.close();
                } catch (SFTPException e) {
                    System.out.println("an SFTPException in Run");
                    SFTPv3Client sc = new SFTPv3Client(conn);
                    SFTPv3FileHandle sf = sc.createFileTruncate(PublicString.SOURCEFILEPUBLICPATH + sessionId + "/" + "CodeOnline.py");
                    sc.write(sf, 0, code.getBytes(), 0, code.getBytes().length);
                    sc.closeFile(sf);
                    sc.close();
                }

                Language languagePy = new LanguagePy();
                String command = languagePy.getRunCommand(PublicString.SOURCEFILEPUBLICPATH + sessionId, "CodeOnline", "py");
                System.out.println(command);
                shellSession.execCommand(command);

            }
            Thread readStdout = new Thread() {
                @Override
                public void run() {
                    try {
                        char[] content = new char[1024];
                        int len = 0;
                        Output output = new Output();
                        BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(new StreamGobbler(shellSession.getStdout())));
                        javax.websocket.Session webSocketSession = (javax.websocket.Session) WebsocketSessionContainer.webSocketSessions.get(sessionId);
                        while ((len = stdoutReader.read(content, 0, 1024)) > 0) {
                            char[] temp = new char[1024];
                            System.arraycopy(content, 0, temp, 0, len);
                            output.sendMessage(String.valueOf(temp), webSocketSession);
                            System.out.println(String.valueOf(temp));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread readStderr = new Thread() {
                @Override
                public void run() {
                    try {
                        char[] content = new char[1024];
                        int len = 0;
                        Output output = new Output();
                        BufferedReader stderrReader = new BufferedReader(new InputStreamReader(new StreamGobbler(shellSession.getStderr())));
                        javax.websocket.Session webSocketSession = (javax.websocket.Session) WebsocketSessionContainer.webSocketSessions.get(sessionId);
                        while ((len = stderrReader.read(content, 0, 1024)) > 0) {
                            char[] temp = new char[1024];
                            System.arraycopy(content, 0, temp, 0, len);
                            output.sendMessage(String.valueOf(temp), webSocketSession);
                            System.out.println(String.valueOf(temp));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            readStdout.start();
            readStderr.start();


        } catch (Exception e) {
            System.out.println("Run failed");
            e.printStackTrace();
        }

        return SUCCESS;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }


    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }


}
