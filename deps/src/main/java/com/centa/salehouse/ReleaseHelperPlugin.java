package com.centa.salehouse;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;

import org.gradle.api.Action;
import org.gradle.api.DomainObjectSet;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ReleaseHelperPlugin implements Plugin<Project> {
    public static final String sPluginExtensionName = "releaseHelper";
    public static final String ANDROID_EXTENSION_NAME = "android";

    @Override
    public void apply(Project project) {
        project.getExtensions().create(sPluginExtensionName, UploadExtension.class, project);
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(@NotNull Project project) {
                DomainObjectSet<ApplicationVariant> variants = ((AppExtension) project.getExtensions().findByName(ANDROID_EXTENSION_NAME)).getApplicationVariants();
                variants.forEach(new Consumer<ApplicationVariant>() {
                    @Override
                    public void accept(ApplicationVariant variant) {
                        if (variant.getBuildType().getName().equalsIgnoreCase("release")) {
                            String variantName = variant.getName().substring(0, 1).toUpperCase() + variant.getName().substring(1);
                            PgyerUploadTask pgyerUploadTask = project.getTasks().create("pgyUploadFor" + variantName, PgyerUploadTask.class);
                            pgyerUploadTask.init(variant, project);

                            SendToDingTalkTask dingTalkTask = project.getTasks().create("sendMsgToDingTalkFor" + variantName, SendToDingTalkTask.class);
                            dingTalkTask.init(variant, project);

                            variant.getAssembleProvider().get().dependsOn(project.getTasks().findByName("clean"));
                            pgyerUploadTask.dependsOn(variant.getAssembleProvider().get());
                            dingTalkTask.dependsOn(pgyerUploadTask);
                        }
                    }
                });
            }
        });
    }
}
