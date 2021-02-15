package com.aiposizi.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class HTMLGenerator {

    private StringBuffer htmlFile;
    private String path;
    private File folder;
    private final String DIRECTORY_PATH = "./lab1/src/main/resources";

    public HTMLGenerator(String path) throws FileNotFoundException {
        this.path = path;
        File file = new File(DIRECTORY_PATH + path);
        if (!file.exists() && !file.isDirectory()) {
            throw new FileNotFoundException();
        }
        folder = new File(DIRECTORY_PATH + path);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.out.println("It is not a folder!");
            return;
        }
        List<File> files = new ArrayList<>();
        List<File> directories = new ArrayList<>();
        for(File fileInList: listOfFiles){
                if(fileInList.isDirectory())
                    directories.add(fileInList);
               else if(fileInList.isFile())
                   files.add(fileInList);
        }
        createContent(files,folder);
    }

    private void createContent(List<File> files, File folder) {
        addBeginHTML();
        addEndHTML();
    }

    private void addEndHTML() {
        htmlFile.append("</head>\n</html>");
    }

    private void addBeginHTML() {
        htmlFile.append("<!DOCTYPE html>\\n<html lang=\\\"en\\\">\\n<head>\\n<meta charset=\\\"UTF-8\\\">\\n");
    }
}
