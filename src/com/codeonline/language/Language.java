package com.codeonline.language;

import com.codeonline.file.SourceFile;

/**
 * Created by zhf on 2016/8/1.
 */
public interface Language {

    /*获得语言名称
    * */
    public String getLanguage();

    /*根据源文件sourceFile获得使其运行的命令
    * */
    public String getRunCommand(String fileParentPath,String noExtensionName,String extensionName);



}
