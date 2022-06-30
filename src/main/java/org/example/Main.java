package org.example;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        FileStructureChanger changer = new FileStructureChanger
    ("C:\\Users\\123\\Desktop\\СПбПУ\\практика 3 курс\\Тех. задание\\test_artifacts", "C:\\dest\\Дест");
        Parser parser = new Parser();
        List<File> fileList = changer.getAllCsvFiles("C:\\Users\\123\\Desktop\\СПбПУ\\практика 3 курс\\Тех. задание\\test_artifacts");
        changer.copyCsvFile(fileList);
        List<File> newFileList = changer.getAllCsvFiles("C:\\dest\\Дест");
        parser.deleteColumnOfTextInFiles(newFileList);
    }
}
