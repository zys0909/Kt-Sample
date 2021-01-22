package com.centa.salehouse;

import com.android.build.gradle.api.BaseVariant;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.util.HashMap;
import java.util.Map;

public class SendToDingTalkTask extends DefaultTask {
    public BaseVariant variant;
    public Project targetProject;

    public void init(BaseVariant variant, Project project) {
        this.variant = variant;
        this.targetProject = project;
        setDescription("send  message to DingTalk");
        setGroup("release helper");
    }

    @TaskAction
    public void sendToDingTalk() {
        UploadExtension extension = UploadExtension.getConfig(targetProject);
        Map<String, Object> map = new HashMap<>(8);


        try {
            RetrofitUtil.get("https://oapi.dingtalk.com/")
                    .create(UploadApi.class)
                    .sendToDingTalk(

                    )
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
