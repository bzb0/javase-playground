package com.bzb.javase.java.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookRenamer {

  private static final String SURNAME_NAME_PATTERN = "^(?<surname>\\w+),\\s+(?<name>\\w+)";

  public void renameBooks(File sourceDir, File targetDir) throws IOException {
    Pattern r = Pattern.compile(SURNAME_NAME_PATTERN);

    for (File directory : sourceDir.listFiles(File::isDirectory)) {
      String name = directory.getName();
      Matcher m = r.matcher(name);
      if (m.matches()) {
        String authorName = m.group("name");
        String authorSurname = m.group("surname");

        File[] books = directory.listFiles(File::isFile);
        for (File book : books) {
          String bookName = book.getName();
          String newBookName = authorName + " " + authorSurname + " - " + bookName;
          copyFile(book, new File(targetDir, newBookName));
        }
      }
    }
  }

  private void copyFile(File source, File target) throws IOException {
    int len;
    byte[] buf = new byte[1024];
    InputStream in = new FileInputStream(source);
    OutputStream out = new FileOutputStream(target);
    while ((len = in.read(buf)) > 0) {
      out.write(buf, 0, len);
    }
    in.close();
    out.close();
  }
}
