package com.centa.salehouse;

import org.gradle.api.Project;

public class UploadExtension {

    //pgyer
    public String pgyApiKey;
    public String appName;
    public String changeLog;
    public String iconFilePath;

    //dingTalk
    public String  apiToken;
    public String msgTitle;
    public String msgContent;
    public String singleButtonTitle;
    public String singleButtonUrl;

    public static UploadExtension getConfig(Project project){
        UploadExtension config = project.getExtensions().findByType(UploadExtension.class);
        if (config==null){
            config = new UploadExtension();
        }
        return config;
    }
}
