package org.example;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

public class Parser {
    public String newNameWithPath(File file, String rootPath) {
        StringBuilder newName = new StringBuilder();
        String sep = File.separator;
        String[] rootPaths = rootPath.split(sep + sep);
        String[] filePaths = file.getAbsolutePath().split(sep + sep);

        for (int i = rootPaths.length; i <= filePaths.length - 1; i++) {
            if (i < filePaths.length - 1) {
                newName.append(filePaths[i]).append(".");
            } else newName.append(filePaths[i]);
        }
        return newName.toString();
    }

    public void deleteColumnOfTextInFiles(List<File> files) throws IOException {
        int deleteBlockNumber = 3;
        for (File file: files) {
            int indexOfFirstStringInBlock = 0;
            List<String> fileText = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
            for ( int i = 0; i < fileText.size(); i++) {
                if (fileText.get(i).matches("^(number,indent,text,tags,notes)(,result)?(,issue)?(,comment)?")) {
                    indexOfFirstStringInBlock = i;
                }
            }
            for (int i = indexOfFirstStringInBlock; i < fileText.size() && !fileText.get(i).matches("^\\s*$");i++) {
                StringBuilder fixString = new StringBuilder();
                String[] blocks = (fileText.get(i) + ",f").split(",");
                if (blocks.length - 1 >= deleteBlockNumber) {
                    for (int k = 0; k < blocks.length - 1; k++) {
                        if (k != deleteBlockNumber) fixString.append(blocks[k]).append(",");
                    }
                    if (fixString.length() > 0) fixString.deleteCharAt(fixString.length() - 1);
                }
                fileText.set(i, fixString.toString());
            }
            Files.write(Paths.get(file.getAbsolutePath()), fileText, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
