package com.codeonline.language;

import com.codeonline.file.SourceFile;
import com.codeonline.publicString.PublicString;

import java.util.logging.SocketHandler;

/**
 * Created by zhf on 2016/8/6.
 */
public class LanguageC implements Language {
    private String languageName="c/c++";
    private String runCommand;

    @Override
    public String getLanguage(){
        return languageName;
    }

    @Override
    public String getRunCommand(String fileParentPath,String noExtensionName,String extensionName){
        runCommand="chmod 755 "+fileParentPath+";"+"cd "+fileParentPath+";"+"chmod 755 "+noExtensionName+"."+extensionName+";";
        runCommand+="g++ "+noExtensionName+"."+extensionName+";";

        runCommand+="./a.out"+";";
        return runCommand;
    }
}
