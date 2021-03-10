package com.custis.sorter.generator;

import com.custis.sorter.exception.CantWriteInFileException;

import java.io.IOException;
import java.io.OutputStream;

public class GeneratorImpl implements Generator{

    @Override
    public void fillFile(int countOfStrokes, int maxLength, OutputStream out) {
        if (countOfStrokes <= 0) {
            throw new IllegalArgumentException("countOfStrokes cannot be lesser or equal to zero");
        }
        if (maxLength <= 0) {
            throw new IllegalArgumentException("maxLength cannot be lesser or equal to zero");
        }

        int i = 0;
        while (i < countOfStrokes) {
            try {
                byte[] line = generateRandomString(maxLength);
                out.write(line);
            } catch (IOException ex) {
                throw new CantWriteInFileException(ex);
            }
            i++;
        }
    }

    private byte[] generateRandomString(int length) {
        final int MAX = 126;
        final int MIN = 32;
        byte[] line = new byte[length + 1];

        int i = 0;
        while (i < length) {
            byte c = (byte) ((Math.random() * ((MAX - MIN) + 1)) + MIN);
            line[i] = c;
            i++;
        }

        line[length] = '\n';
        return line;
    }
}
