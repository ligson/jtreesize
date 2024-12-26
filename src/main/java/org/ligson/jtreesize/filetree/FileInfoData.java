package org.ligson.jtreesize.filetree;

import org.ligson.jtreesize.SizeTask;
import org.ligson.jtreesize.core.ApplicationContext;
import org.ligson.jtreesize.core.annotation.Component;
import org.ligson.jtreesize.event.FileSizeRefreshEvent;
import org.ligson.jtreesize.event.StatusBarChangeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

@Component
public class FileInfoData {
    public Map<File, Long> fileSizeMap = new ConcurrentHashMap<>();

    private final ApplicationContext applicationContext;

    public FileInfoData(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void init(File dir) {
        fileSizeMap.clear();
        calcDirSize(dir);
        FileSizeRefreshEvent fileSizeRefreshEvent = new FileSizeRefreshEvent(this, List.of(dir));
        applicationContext.publishEvent(fileSizeRefreshEvent);
    }

    public static List<File> getAllParentDirectories(File file) {
        List<File> parentDirectories = new ArrayList<>();
        File parent = file.getParentFile();
        while (parent != null) {
            parentDirectories.add(parent);
            parent = parent.getParentFile();
        }
        return parentDirectories;
    }

    public void refresh(File file) {
        StatusBarChangeEvent statusBarChangeEvent1 = new StatusBarChangeEvent(this, "正在计算目录大小....");
        applicationContext.publishEvent(statusBarChangeEvent1);
        calcDirSize(file);
        List<File> files = new ArrayList<>();
        files.add(file);
        files.addAll(getAllParentDirectories(file));
        FileSizeRefreshEvent fileSizeRefreshEvent = new FileSizeRefreshEvent(this, files);
        applicationContext.publishEvent(fileSizeRefreshEvent);
        StatusBarChangeEvent statusBarChangeEvent2 = new StatusBarChangeEvent(this, "计算大小完成");
        applicationContext.publishEvent(statusBarChangeEvent2);
    }

    public long getFileSize(File file) {
        return fileSizeMap.getOrDefault(file, 0L);
    }

    public String getFileSizeHuman(File file) {
        long size = getFileSize(file);
        if (size < 1024) {
            return size + "B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2fKB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2fMB", size / (1024 * 1024.0));
        } else {
            return String.format("%.2fGB", size / (1024 * 1024.0 * 1024));
        }
    }

    private long calcDirSize(File file) {
        return calculateDirectorySize(file);
    }


    private long calculateDirectorySize(File rootDir) {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            return pool.invoke(new SizeTask(rootDir, fileSizeMap));
        }
    }
}
