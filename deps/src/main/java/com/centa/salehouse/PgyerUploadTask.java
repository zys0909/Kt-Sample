package com.centa.salehouse;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.BaseVariant;
import com.android.build.gradle.api.BaseVariantOutput;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.centa.salehouse.ReleaseHelperPlugin.ANDROID_EXTENSION_NAME;

public class PgyerUploadTask extends DefaultTask {
    public BaseVariant variant;
    public Project targetProject;

    public void init(BaseVariant variant, Project project) {
        this.variant = variant;
        this.targetProject = project;
        setDescription("Upload release apk to Pgyer");
        setGroup("release helper");
    }

    @TaskAction
    private void uploadToPgy() {
        AppExtension appExtension = (AppExtension) targetProject.getExtensions().findByName(ANDROID_EXTENSION_NAME);
        for (BaseVariantOutput it : variant.getOutputs()) {
            File apkFile = it.getOutputFile();
            if (apkFile == null || !apkFile.exists()) {
                throw new GradleException(apkFile + "is not existed !");
            }
            UploadExtension extension = UploadExtension.getConfig(targetProject);
            try {
                RetrofitUtil.get("https://www.pgyer.com/apiv2")
                        .create(UploadApi.class)
                        .uploadFile(getTextBody(extension.pgyApiKey),
                                getFilePart("application/vnd.android.package-archive", apkFile),
                                getTextBody("2"),
                                getTextBody("123456"),
                                getTextBody("test"),
                                getTextBody("android-plugin"),
                                getTextBody("2"))
                        .execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public RequestBody getTextBody(String text) {
        return RequestBody.create(MediaType.parse("text/plain"), text);
    }

    public MultipartBody.Part getFilePart(String mediaType, File file) {
        return MultipartBody.Part.createFormData("file", file.getAbsoluteFile().getName(), RequestBody.create(MediaType.parse(mediaType), file));
    }
}
