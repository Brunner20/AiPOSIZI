package com.aiposizi.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class HTMLGenerator {

    private StringBuffer htmlFile;
    private String path;
    private File folder;
    private int port;
    private final String DIRECTORY_PATH ;

    public HTMLGenerator(String DIRECTORY_PATH,String path, int port) throws FileNotFoundException {
        this.DIRECTORY_PATH = DIRECTORY_PATH;
        this.path = path;
        this.port = port;
        htmlFile = new StringBuffer();
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
        createContent(files,directories);
    }

    private void createContent(List<File> files, List<File> folder) {
        addBeginHTML();
        htmlFile.append(createTag(1, path));
        createTags(false, folder);
        createTags(true, files);
        addEndHTML();
    }

    private void addEndHTML() {
        htmlFile.append("</head>\n</html>");
    }

    private void addBeginHTML() {
        htmlFile.append("<!DOCTYPE html>\n<html lang=\\\"en\\\">\n<head>\n<meta charset=\\\"UTF-8\\\">\n");
    }

    private void createTags(boolean checkFiles, List<File> list) {
        if (!list.isEmpty())
        {
            htmlFile.append(createTag(2, (checkFiles) ? "Read files:" : "Open directory"));
            for (File file : list) {
                 htmlFile.append(createTagWithLink(file));
            }
        }
    }
    private String createTag(int size, String message) {
        return format("<h%d>%s</h%d>\n", size, message, size);
    }

    private String createTagWithLink(File file) {
        String path = file.getPath().substring(DIRECTORY_PATH.length());
        return format("<p><a href=\"http://localhost:%d%s\"> open %s</a></p>\n", port,path, file.getName());
    }

    public String getHtmlFile() {
        return htmlFile.toString();
    }
}
