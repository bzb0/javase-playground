package com.bzb.javase.java.process;

import java.io.File;
import java.util.List;

/**
 * Program/command runner.
 */
public class CommandRunner {

  private Process process;
  private ProcessBuilder processBuilder;

  /**
   * Runs the specified command/program in the execution directory.
   *
   * @param command The program/command to be executed.
   * @param executionDirectory The directory where the command will be executed.
   * @param timeoutMillis Execution timeout.
   */
  public void runCommand(List<String> command, String executionDirectory, long timeoutMillis) {
    try {
      processBuilder = new ProcessBuilder(command);
      processBuilder.directory(new File(executionDirectory));

      long startNanos = System.nanoTime();
      run(timeoutMillis);
      long durationNanos = System.nanoTime() - startNanos;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Runs the command defined by the process builder.
   */
  private void run(long timeoutMillis) {
    try {
      process = processBuilder.start();
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            Thread.sleep(timeoutMillis);
          } catch (InterruptedException ex) {
            return;
          }
          process.destroy();
        }
      });
      thread.setDaemon(true);
      thread.start();

      try {
        process.waitFor();
        thread.interrupt();
      } catch (InterruptedException ex) {
        throw new RuntimeException("The process execution was interrupted", ex);
      } finally {
        Thread.interrupted();
      }
    } catch (Exception e) {
      throw new RuntimeException("Executable cannot be started.", e);
    } finally {
      // Closing the process streams used by the process builder
      try {
        process.getInputStream().close();
        process.getOutputStream().close();
        process.getErrorStream().close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}