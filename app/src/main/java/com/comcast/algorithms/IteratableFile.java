package com.comcast.algorithms;


//Implement a (Java) Iterable object that iterates lines one by one from a text file.
//public class TextFile implements Iterable
//{
//    public TextFile(String fileName) { // please implement this.
// Begin reading the file, line by line. The returned Iterator.next() will return a line. /
//@Override
//public Iterator iterator() { // please implement this

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class IteratableFile implements Iterable {

    class FileItertor implements Iterator<String> {
        private String nextLine;
        private BufferedReader reader;

        public FileItertor(String fileName) {
            try {
                reader = new BufferedReader(new FileReader(fileName));
                nextLine = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean hasNext() {
            return nextLine != null;
        }

        @Override
        public String next() {
            String returnString = nextLine;

            nextLine = null;
            try {
                nextLine = reader.readLine();
                if (nextLine == null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return returnString;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("don't support remove");
        }
    }

    private String fileName;

    public IteratableFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Iterator iterator() {
        return new FileItertor(fileName);
    }
}
