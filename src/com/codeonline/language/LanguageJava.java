package com.codeonline.language;

import com.codeonline.file.SourceFile;
import com.codeonline.publicString.PublicString;

/**
 * Created by zhf on 2016/8/1.
 */
public class LanguageJava implements Language {

    private String languageName="java";
    private String runCommand;


    /*获得语言名称
    * */
    @Override
    public String getLanguage(){
        return languageName;
    }

    /*根据源文件sourceFile获得使其运行的命令
    * */
    @Override
    public String getRunCommand(String fileParentPath,String noExtensionName,String extensionName){
        runCommand="chmod 755 "+fileParentPath+";"+"cd "+fileParentPath+";"+"chmod 755 "+noExtensionName+"."+extensionName+";";
        runCommand+="javac "+noExtensionName+"."+extensionName+";";

        runCommand+="java "+noExtensionName+";";
        return runCommand;
    }


}
