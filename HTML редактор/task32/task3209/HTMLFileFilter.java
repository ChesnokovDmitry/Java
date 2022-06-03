package com.javarush.task.task32.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        String fileName = f.getName();
        Pattern pattern = Pattern.compile(".+\\.(html|htm)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches() || f.isDirectory();
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
