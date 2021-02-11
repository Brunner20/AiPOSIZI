package com.aiposizi.server.reader;

import java.io.IOException;
import java.io.InputStream;

public interface Reader {
    byte[] read(InputStream inputStream) throws IOException;
}
