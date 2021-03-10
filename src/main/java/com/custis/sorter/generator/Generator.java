package com.custis.sorter.generator;

import java.io.OutputStream;

public interface Generator {

    void fillFile(long countOfStrokes, int maxLength, OutputStream out);
}
