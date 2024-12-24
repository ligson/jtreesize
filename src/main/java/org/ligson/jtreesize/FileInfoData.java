package org.ligson.jtreesize;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileInfoData {
    public Map<File, Long> fileSizeMap = new ConcurrentHashMap<>();

    public void init(File dir) {
        fileSizeMap.clear();
        calcDirSize(dir);
    }

    public long getFileSize(File file) {
        return fileSizeMap.get(file);
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
        if (file == null) {
            return 0L;
        }
        long total = 0;

        System.out.println(file.getAbsolutePath());
        if (file.isFile()) {
            total = file.length();
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File listFile : files) {
                    total += calcDirSize(listFile);
                }
            }
        }
        fileSizeMap.put(file, total);
        return total;
    }
}
