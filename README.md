# Simple File Explorer (AWT)

A minimal, single-file Java AWT program that acts as a basic file explorer. It was built to be small and easy to read, focusing on fundamental file operations provided by `java.io.File` and a very simple user interface.

## Description
- A compact AWT GUI that starts by listing system partitions (roots).
- Ability to navigate directories by selecting entries and pressing "Open" or by double-clicking directories.
- Controls: Back (go to parent directory), Partitions (go back to root list), Open.
- A side panel that shows basic file/directory properties (name, existence, isFile/isDirectory for regular files, paths).
- No delete or write operations (read-only explorer).

## Why this program
- Educational: shows how to use `java.io.File` methods (`listRoots()`, `list()`, `listFiles()`, `getPath()`, `getAbsolutePath()`, `isFile()`, `isDirectory()`, `canRead()`, `canWrite()`, `length()`).
- Minimal and OOP-light: easy to inspect and adapt for learning or coursework.

## Requirements
- Java 8+ (any recent JDK will work)
- No external libraries

## Files
- `SimpleFileExplorer.java` — the full source code in a single file (AWT-based).

## Compile and run
1. Open a terminal/command prompt in the directory that contains `SimpleFileExplorer.java`.
2. Compile:
   ```
   javac SimpleFileExplorer.java
   ```
3. Run:
   ```
   java SimpleFileExplorer
   ```

## Usage
- On start, the program shows the system partitions (e.g., `C:\`, `D:\` on Windows).
- Select a partition and press "Open" or double-click it to list its top-level contents.
- Select a directory and "Open" (or double-click) to enter it.
- Press "Back" to go up one directory level. If there is no parent, the view returns to the partitions list.
- Press "Partitions" to immediately return to the root list.
- Select any file or directory to view its properties in the right-hand panel.


