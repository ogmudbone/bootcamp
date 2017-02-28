package com.anotheria.bootcamp.file_transfer.helpers;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class DirectoryBrowser {

    private File directory;

    public File[] getFilesList(){
        File[] files = directory.listFiles();
        return files != null ? files : new File[]{};
    }

    public String[] getFileNamesList(){
        String[] names = directory.list();
        return names != null ? names : new String[]{};
    }

    public DirectoryBrowser(String path) throws IOException {
        directory = new File(path);
        if(!directory.isDirectory())
            throw new IOException("No directory located in this path.");
    }

    public Optional<File> getFile(String fileName){

        return Arrays.stream(getFilesList())
                .filter( file ->
                        !file.isDirectory() && Objects.equals(file.getName(), fileName)
                ).findFirst();

    }

    public long getFileSize(String fileName){

        Optional<File> fileOptional = getFile(fileName);
        return fileOptional.isPresent() ? fileOptional.get().length() : 0L;

    }

    public FileInputStream getFileInputStream(String fileName) throws IOException {

        Optional<File> file = getFile(fileName);

        if(file.isPresent())
            return new FileInputStream(
                    getFile(fileName).get()
            );
        else
            throw new FileNotFoundException("Invalid file path");

    }

    public FileOutputStream getFileOutputStream(String fileName) throws IOException {

        File file = getFile(fileName).orElse(new File(directory, fileName));
        file.createNewFile();
        return new FileOutputStream(file, false);

    }

}
