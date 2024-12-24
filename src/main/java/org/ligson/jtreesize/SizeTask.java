package org.ligson.jtreesize;

import java.io.File;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class SizeTask extends RecursiveTask<Long> {
    private final File file;
    private final Map<File, Long> fileSizeMap;

    public SizeTask(File file, Map<File, Long> fileSizeMap) {
        this.file = file;
        this.fileSizeMap = fileSizeMap;
    }

    @Override
    protected Long compute() {
        if (file.isFile()) {
            fileSizeMap.put(file, file.length());
            return file.length();
        } else {
            File[] files = file.listFiles();
            if (files == null) {
                return 0L;
            }
            long total = 0;
            SizeTask[] tasks = new SizeTask[files.length];
            for (int i = 0; i < files.length; i++) {
                tasks[i] = new SizeTask(files[i], fileSizeMap);
                tasks[i].fork();
            }
            for (SizeTask task : tasks) {
                long taskSize = task.join();
                total += taskSize;
                fileSizeMap.put(task.file, taskSize);
            }
            fileSizeMap.put(file, total);
            return total;
        }
    }
}
