

package com.codeonline.action;

import ch.ethz.ssh2.SFTPv3Client;
import com.codeonline.Container.WebsocketSessionContainer;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


//是对每一个请求都new一个吗
@ServerEndpoint("/websocket")
public class Output {


    public void sendMessage(String message,Session session){
        try{
            session.getBasicRemote().sendText(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @OnMessage
    public void onMessage(String sessionId, Session session) {
        System.out.println("in output websocketSessionID:"+session.hashCode());
        WebsocketSessionContainer.webSocketSessions.put(sessionId,session);
     /*   if (message.equals("start")||message.equals("stop")) {

            try {
                System.out.println(message);
                File stdout = new File(PublicString.SOURCEFILEPUBLICPATH + sessionId + "/" + PublicString.STDOUT);
                File stderr = new File(PublicString.SOURCEFILEPUBLICPATH + sessionId + "/" + PublicString.STDERR);
                stdoutReader = new BufferedReader(new FileReader(stdout));
                stderrReader = new BufferedReader(new FileReader(stderr));
                stdoutWriter = new DataOutputStream(new FileOutputStream(stdout));
                stderrWriter = new DataOutputStream(new FileOutputStream(stderr));
                while ((!stdout.exists()) || (!stderr.exists())) {
                    Thread.sleep(30);
                }

                stderrWriter.writeBytes("");
                stdoutWriter.writeBytes("");
                if(!readStderr.isAlive()){
                    readStderr = new MyThread(stderrReader,session);
                    readStderr.start();
                }
                if(!readStdout.isAlive()){
                    readStdout = new MyThread(stdoutReader,session);
                    readStdout.start();
                }
                if(message.equals("stop")&&readStderr.isAlive()){
                    System.out.println("set stop");
                    readStderr.setStop();
                }
                if(message.equals("stop")&&readStdout.isAlive()){
                    System.out.println("set stop");
                    readStdout.setStop();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            this.sessionId = message;
            try {
                File stdout = new File(PublicString.SOURCEFILEPUBLICPATH + sessionId + "/" + PublicString.STDOUT);
                File stderr = new File(PublicString.SOURCEFILEPUBLICPATH + sessionId + "/" + PublicString.STDERR);
                File sourceDir = new File(PublicString.SOURCEFILEPUBLICPATH + sessionId);
                if (!sourceDir.exists()) {
                    sourceDir.mkdir();
                }
                if (!stdout.exists()) {
                    stdout.createNewFile();
                }
                if (!stderr.exists()) {
                    stderr.createNewFile();
                }

                stdoutReader = new BufferedReader(new FileReader(stdout));
                stderrReader = new BufferedReader(new FileReader(stderr));
                stdoutWriter = new DataOutputStream(new FileOutputStream(stdout));
                stderrWriter = new DataOutputStream(new FileOutputStream(stderr));
                readStdout = new MyThread(stdoutReader,session);

                readStderr = new MyThread(stderrReader,session);
                //do nothing

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(sessionId);*/
    }


    @OnOpen
    public void onOpen() {
        System.out.println("Client connected");
    }
    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }

}
