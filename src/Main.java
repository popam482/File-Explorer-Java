import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Main extends Frame implements ActionListener, ItemListener, MouseListener, WindowListener {
    private final TextField pathField;
    private final java.awt.List fileList;
    private final TextArea infoArea;
    private final Button btnBack, btnPartitions, btnOpen;

    private File currentDir;
    private File[] currentFiles;

    public Main() {
        super("Simple File Explorer");


        Panel top = new Panel(new BorderLayout(4, 4));
        pathField = new TextField();
        pathField.setEditable(false);
        top.add(pathField, BorderLayout.CENTER);

        Panel buttons = new Panel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        btnBack = new Button("Back");
        btnPartitions = new Button("Partitions");
        btnOpen = new Button("Open");
        buttons.add(btnBack);
        buttons.add(btnPartitions);
        buttons.add(btnOpen);
        top.add(buttons, BorderLayout.EAST);

        Panel center = new Panel(new GridLayout(1, 2, 6, 6));
        fileList = new java.awt.List();
        fileList.setMultipleMode(false);
        center.add(fileList);

        infoArea = new TextArea();
        infoArea.setEditable(false);
        center.add(infoArea);

        setLayout(new BorderLayout(6, 6));
        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);


        btnBack.addActionListener(this);
        btnPartitions.addActionListener(this);
        btnOpen.addActionListener(this);

        fileList.addItemListener(this);
        fileList.addMouseListener(this);
        addWindowListener(this);

        setSize(800, 480);
        setLocationRelativeTo(null);


        showRoots();
    }

    private void showRoots() {
        File[] roots = File.listRoots();
        currentDir = null;
        currentFiles = roots != null ? roots : new File[0];
        pathField.setText("Partitions (Roots)");
        fileList.removeAll();
        if (currentFiles.length == 0) {
            fileList.add("(no roots found)");
            infoArea.setText("No roots found on this system.");
        } else {
            for (File r : currentFiles) {
                fileList.add(r.getPath());
            }
            infoArea.setText("Select a partition and press Open or double-click to navigate.");
        }
    }

    private void listDirectory(File dir) {
        if (dir == null) return;
        if (!dir.exists()) {
            infoArea.setText("Directory does not exist: " + dir.getPath());
            return;
        }
        if (!dir.isDirectory()) {
            infoArea.setText("Not a directory: " + dir.getPath());
            return;
        }
        File[] files = dir.listFiles();
        currentDir = dir;
        currentFiles = files != null ? files : new File[0];
        pathField.setText(dir.getPath());
        fileList.removeAll();
        if (currentFiles.length == 0) {
            fileList.add("(empty)");
        } else {
            for (File f : currentFiles) {
                String name = f.getName();
                if (name == null || name.isEmpty()) name = f.getPath();
                if (f.isDirectory()) name += File.separator;
                fileList.add(name);
            }
        }
        infoArea.setText("Listed " + currentFiles.length + " items.");
    }

    private void showProperties(File f) {
        if (f == null) {
            infoArea.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(f.getName()).append("\n");
        sb.append("Exists: ").append(f.exists()).append("\n");
        sb.append("IsFile: ").append(f.isFile()).append("\n");
        sb.append("IsDirectory: ").append(f.isDirectory()).append("\n");
        sb.append("Path: ").append(f.getPath()).append("\n");
        sb.append("AbsolutePath: ").append(f.getAbsolutePath()).append("\n");
        infoArea.setText(sb.toString());
    }


    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnPartitions) {
            showRoots();
        } else if (src == btnBack) {
            if (currentDir == null) {
                showRoots();
            } else {
                File parent = currentDir.getParentFile();
                if (parent != null) listDirectory(parent);
                else showRoots();
            }
        } else if (src == btnOpen) {
            int idx = fileList.getSelectedIndex();
            if (idx < 0 || currentFiles == null || idx >= currentFiles.length) {
                infoArea.setText("Select an item first.");
                return;
            }
            File sel = currentFiles[idx];
            if (sel.isDirectory()) listDirectory(sel);
            else showProperties(sel);
        }
    }


    public void itemStateChanged(ItemEvent e) {
        int idx = fileList.getSelectedIndex();
        if (idx < 0 || currentFiles == null || idx >= currentFiles.length) {
            infoArea.setText("");
            return;
        }
        showProperties(currentFiles[idx]);
    }


    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int idx = fileList.getSelectedIndex();
            if (idx < 0 || currentFiles == null || idx >= currentFiles.length) return;
            File sel = currentFiles[idx];
            if (sel.isDirectory()) listDirectory(sel);
            else showProperties(sel);
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}


    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main fe = new Main();
            fe.setVisible(true);
        });
    }
}