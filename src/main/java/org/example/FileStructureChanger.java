package org.example;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStructureChanger {

    private final String rootPath;

    private final String newRootPath;

    public FileStructureChanger(String rootPath, String newRootPath) {
        this.rootPath = rootPath;
        this.newRootPath = newRootPath;
    }

    public List<File> getAllCsvFiles(String dirPath) {
        List<File> allCsvFiles = new ArrayList<File>();
        File[] dirFiles = new File(dirPath).listFiles();
        assert dirFiles != null;
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                allCsvFiles.addAll(getAllCsvFiles(file.getPath()));
            } else {
                String extension = "";
                int i = file.getName().lastIndexOf('.');
                if (i >= 0) {
                    extension = file.getName().substring(i + 1);
                }
                if (extension.equals("csv")) {
                    allCsvFiles.add(file);
                }
            }
        }
        return allCsvFiles;
    }

    public void copyCsvFile(List<File> csvFiles) throws IOException {
        Parser parser = new Parser();
        for (File file : csvFiles) {
            String sep = File.separator;
            File copy = new File(newRootPath + sep + parser.newNameWithPath(file, rootPath));
            copy.createNewFile();
            FileChannel inputChannel = null;
            FileChannel outputChannel = null;
            try {
                inputChannel = new FileInputStream(file.getAbsolutePath()).getChannel();
                outputChannel = new FileOutputStream(copy.getAbsolutePath()).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            } finally {
                inputChannel.close();
                outputChannel.close();
            }
        }
    }

}



