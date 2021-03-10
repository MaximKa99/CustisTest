package com.custis.sorter.sorter;

import com.custis.sorter.exception.SmthGoneWrongException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SortHelper {
    private File src;
    private File target;
    private File tempA;
    private File tempB;
    private OutputStream tempAOut;
    private OutputStream tempBOut;
    private InputStream tempAIn;
    private InputStream tempBIn;
    private BufferedReader brA;
    private BufferedReader brB;
    private OutputStream targetOut;
    private InputStream targetStream;
    private BufferedReader brFromTarget;
    private long maxStrokesInSrc = 0;

    public SortHelper(File src, File target) {
        this.src = src;
        this.target = target;
    }

    public void setup() {
        countMaxStrokesInSrc(src);
        copySrcToTarget();
        createTempFile();
    }

    public void clear() {
        try {
            this.tempA.delete();
            this.tempB.delete();
        } catch (SecurityException ex) {
            throw new SmthGoneWrongException(ex);
        }
    }

    public String getNextLineFromTemp(TempType type) {
        String nextLine;
        if (type == TempType.A) {
            try {
                nextLine = this.brA.readLine();
            } catch (IOException e) {
                throw new SmthGoneWrongException(e);
            }
            return nextLine;
        } else {
            try {
                nextLine = this.brB.readLine();
            } catch (IOException e) {
                throw new SmthGoneWrongException(e);
            }
            return nextLine;
        }
    }

    public String getNextLineFromTarget() {
        String line;

        try {
            line = this.brFromTarget.readLine();
        } catch (IOException e) {
            throw new SmthGoneWrongException(e);
        }
        return line;
    }

    public void writeStringToTarget(String line) {
        try {
            targetOut.write((line + "\n").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new SmthGoneWrongException(e);
        }
    }

    public void writeStringToTemp(String line, TempType type) {
        if (type == TempType.B) {
            try {
                this.tempBOut.write((line + "\n").getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new SmthGoneWrongException(e);
            }
        } else {
            try {
                this.tempAOut.write((line + "\n").getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new SmthGoneWrongException(e);
            }
        }
    }

    public void writeRestString(long count, TempType type) {
        int i = 0;
        while (i < count) {
            String line = getNextLineFromTemp(type);
            if (line != null) {
                writeStringToTarget(line);
            }
            i++;
        }
    }

    private long countMaxStrokesInSrc(File src) {
        try {
            InputStream inputStream = new FileInputStream(src);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (bufferedReader.readLine() != null) {
                maxStrokesInSrc++;
            }
        } catch (IOException ex) {
            throw new SmthGoneWrongException(ex);
        }
        return maxStrokesInSrc;
    }

    private void copySrcToTarget() {
        try (InputStream srcStream = new FileInputStream(src);
             OutputStream targetStream = new FileOutputStream(target)) {
            int c;
            while ((c = srcStream.read()) != -1) {
                targetStream.write(c);
            }

        } catch (IOException ex) {
            throw new SmthGoneWrongException(ex);
        }
    }

    private void createTempFile() {
        this.tempA = new File(".", "tempA");
        this.tempB = new File(".", "tempB");

        try {
            this.tempA.createNewFile();
            this.tempB.createNewFile();
        } catch (IOException ex) {
            throw new SmthGoneWrongException(ex);
        }
    }

    public void prepareHelperForMergingBlock() {
        try {
            this.tempAIn = new FileInputStream(this.tempA);
            this.tempBIn = new FileInputStream(this.tempB);

            brA = new BufferedReader(new InputStreamReader(this.tempAIn));
            brB = new BufferedReader(new InputStreamReader(this.tempBIn));
            targetOut = new FileOutputStream(target);
        } catch (FileNotFoundException e) {
            throw new SmthGoneWrongException(e);
        }
    }

    public void prepareHelperForSplitting() {
        try {
            this.targetStream = new FileInputStream(target);
            this.brFromTarget = new BufferedReader(new InputStreamReader(this.targetStream));
            this.tempAOut = new FileOutputStream(this.tempA);
            this.tempBOut = new FileOutputStream(this.tempB);
        } catch (FileNotFoundException e) {
            throw new SmthGoneWrongException(e);
        }
    }

    public void CloseStreamsAfterMerging() {
        try {
            this.tempAIn.close();
            this.tempBIn.close();
            this.targetOut.close();
        } catch (IOException e) {
            throw new SmthGoneWrongException(e);
        }
    }


    public void CloseStreamsAfterSplitting() {
        try {
            this.tempAOut.close();
            this.tempBOut.close();
        } catch (IOException e) {
            throw new SmthGoneWrongException(e);
        }
    }

    public long getMaxStrokesInSrc() {
        return maxStrokesInSrc;
    }

    public BufferedReader getBrA() {
        return brA;
    }

    public BufferedReader getBrB() {
        return brB;
    }
}
