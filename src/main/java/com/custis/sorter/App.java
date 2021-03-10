package com.custis.sorter;

import com.custis.sorter.generator.Generator;
import com.custis.sorter.generator.GeneratorImpl;
import com.custis.sorter.sorter.MergeSorter;
import com.custis.sorter.sorter.SortHelper;
import com.custis.sorter.sorter.Sorter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class App
{
    public static void main( String[] args )
    {
        long start = System.currentTimeMillis();
        Generator generator = new GeneratorImpl();

        File source = new File(".", "source");
        File target = new File(".", "sorted");
        try {
            source.createNewFile();
            target.createNewFile();

            SortHelper sortHelper = new SortHelper(source, target);
            Sorter sorter = new MergeSorter(sortHelper);

            System.out.println("Generating...");
            generator.fillFile(200, 17, new FileOutputStream(source));
            System.out.println("File has been generated");
            sorter.sort();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        long end = System.currentTimeMillis();
        System.out.println("WorkTime equals " + (end - start));
    }
}
