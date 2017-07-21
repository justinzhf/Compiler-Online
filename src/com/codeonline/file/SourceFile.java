package com.codeonline.file;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.sftp.Packet;
import com.codeonline.publicString.PublicString;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by zhf on 2016/8/1.
 */
public class SourceFile {
    public static final String sourceFilePublicPath= PublicString.SOURCEFILEPUBLICPATH;
    private File sourceFile;
    private String noExetensionfileName;
    private String extensionName;
    private String content;

    public SourceFile(String sessionId,String noExetensionfileName,String extensionName){

        File sourceDir=new File(sourceFilePublicPath+sessionId);
        this.noExetensionfileName=noExetensionfileName;
        this.extensionName=extensionName;
        chmod(sourceDir.getPath());
        try{
            if(sourceDir.exists()&&sourceDir.isDirectory()){
                sourceFile=new File(sourceFilePublicPath+sessionId+"/"+noExetensionfileName+"."+extensionName);
                sourceFile.createNewFile();
            }else{
                sourceDir.mkdir();
                sourceFile=new File(sourceFilePublicPath+sessionId+"/"+noExetensionfileName+"."+extensionName);
                sourceFile.createNewFile();
            }
            //chmod(sourceFile.getPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void chmod(String path){
        Session shellSession;
        try{
            Connection conn=new Connection("127.0.0.1");
            conn.connect();
            conn.authenticateWithPassword("root","15651791178Zhf");
            shellSession=conn.openSession();
            shellSession.execCommand("chmod 777 -R "+path);
            shellSession.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getSourceFilePath(){
        return sourceFile.getPath();
    }

    public String getSourceFileParentPath(){
        return sourceFile.getParent  ();
    }

    public String getNoExetensionfileName(){
        return noExetensionfileName;
    }

    public String getExtensionName(){
        return extensionName;
    }

    public String getFileName(){
        return noExetensionfileName+"."+extensionName;
    }

    public void setContent(String code){
        try{
            DataOutputStream dos=new DataOutputStream(new FileOutputStream(sourceFile));
            dos.writeBytes(code);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
