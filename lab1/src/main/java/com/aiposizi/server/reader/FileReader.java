package com.aiposizi.server.reader;

import java.io.IOException;
import java.io.InputStream;

public class FileReader implements Reader {

    @Override
    public byte[] read(InputStream inputStream) throws IOException {
        byte[] fileData = new byte[inputStream.available()];
        try {
            inputStream.read(fileData);
        } finally {
            inputStream.close();
        }
        return fileData;
    }
}
