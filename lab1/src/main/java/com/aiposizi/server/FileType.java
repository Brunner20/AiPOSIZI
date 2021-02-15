package com.aiposizi.server;


import java.util.Locale;

public enum FileType {
    PLAIN("text/plain", "txt"),
    HTML("text/html", "html"),
    CSS("text/css", "css"),
    JS("application/javascript", "js"),
    PNG("image/png", "png"),
    JPEG("image/jpeg", "jpeg"),
    SVG("image/svg+xml", "svg");

    private String type;
    private String extension;


    FileType(String type, String extension) {
        this.type = type;
        this.extension = extension;

    }

    public String getType() {
        return type;
    }

    public String getExtension() {
        return extension;
    }


    public static FileType getFileTypeByFilename(String filename){

        String extension = filename.substring(filename.lastIndexOf(".")+1);
       return FileType.valueOf(extension.toUpperCase(Locale.ROOT));

    }
}
