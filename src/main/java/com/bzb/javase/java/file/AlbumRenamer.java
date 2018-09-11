package com.bzb.javase.java.file;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlbumRenamer {

  private static final String YEAR_ARTIST_ALBUM_PATTERN = "^\\[(?<year>\\d+)\\]\\s(?<artist>.+)\\s-\\s(?<album>.+)";
  private static final String ARTIST_ALBUM_PATTERN = "^\\[(?<year>\\d+)\\]\\s(?<artistAlbum>.+)";

  public void renameAlbumDirectories(File rootDir) {
    Pattern r = Pattern.compile(YEAR_ARTIST_ALBUM_PATTERN);
    for (File directory : rootDir.listFiles(File::isDirectory)) {
      String name = directory.getName();
      Matcher m = r.matcher(name);
      if (m.matches()) {
        String year = m.group("year");
        String artist = m.group("artist");
        String album = m.group("album");
        // String artistAlbum = m.group("artistAlbum");

        String newDirectoryName = artist + " - " + album + " (" + year + ")";
        File newDir = new File(directory.getParent(), newDirectoryName);
        directory.renameTo(newDir);
      }
    }
  }
}