package com.custis.sorter.sorter;

import java.util.Comparator;

public class MergeSorter implements Sorter{
    private SortHelper helper;
    private Comparator<String> comparator;

    public MergeSorter(SortHelper helper) {
        this.helper = helper;
        this.comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
    }

    public MergeSorter(SortHelper helper, Comparator<String> comparator) {
        this.helper = helper;
        this.comparator = comparator;
    }

    @Override
    public void sort() {
        helper.setup();
        algo();
        helper.clear();
    }

    private void algo() {
        long sizeOfBlock = 1;

        while (countBlocksInTemp(sizeOfBlock) > 1) {
            split(sizeOfBlock);
            merge(sizeOfBlock);
            sizeOfBlock *= 2;
        }

        split(sizeOfBlock);
        merge(sizeOfBlock);
    }

    private long countBlocksInTemp(long sizeOfBlock) {
        long maxPredictableStrokes;

        if (helper.getMaxStrokesInSrc() % 2 == 0) {
            maxPredictableStrokes = helper.getMaxStrokesInSrc() / 2;
        } else {
            maxPredictableStrokes = helper.getMaxStrokesInSrc() / 2 + 1;
        }

        long integer = maxPredictableStrokes / sizeOfBlock;
        if (maxPredictableStrokes % sizeOfBlock != 0) {
            return integer + 1;
        } else {
            return integer;
        }
    }

    private void merge(long lengthOfSeries) {
        helper.prepareHelperForMergingBlock();

        long i = 0;
        long countOfBlock = countBlocksInTemp(lengthOfSeries);
        while (i++ < countOfBlock) {
            mergeBlock(lengthOfSeries);
        }

        helper.CloseStreamsAfterMerging();
    }

    private void mergeBlock(long size) {
        long indexA = 1;
        long indexB = 1;

        String lineA = helper.getNextLineFromTemp(TempType.A);
        String lineB = helper.getNextLineFromTemp(TempType.B);
        while (Math.max(indexA, indexB) <= size) {
            if (lineA == null) {
                break;
            }
            if (lineB == null) {
                break;
            }
            if (comparator.compare(lineA, lineB) < 0) {
                helper.writeStringToTarget(lineA);
                lineA = null;
            } else {
                helper.writeStringToTarget(lineB);
                lineB = null;
            }
            if (lineA == null) {
                if (indexA == size) {
                    break;
                }
                lineA = helper.getNextLineFromTemp(TempType.A);
                indexA++;
            } else {
                if (indexB == size) {
                    break;
                }
                lineB = helper.getNextLineFromTemp(TempType.B);
                indexB++;
            }
        }
        if (lineA == null && lineB != null) {
            helper.writeStringToTarget(lineB);
            helper.writeRestString(size - indexB, TempType.B);
        }
        if (lineB == null && lineA != null) {
            helper.writeStringToTarget(lineA);
            helper.writeRestString(size - indexA, TempType.A);
        }
    }

    private void split(long lengthOfSeries) {
        helper.prepareHelperForSplitting();
        String line;
        long i = 0;
        while ((line = helper.getNextLineFromTarget()) != null) {
            if ((i / lengthOfSeries) % 2 == 0) {
                helper.writeStringToTemp(line, TempType.A);
            } else {
                helper.writeStringToTemp(line, TempType.B);
            }
            i++;
        }
        helper.CloseStreamsAfterSplitting();
    }
}
