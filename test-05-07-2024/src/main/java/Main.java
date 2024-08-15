import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class Main extends Thread {
    static String[] Pictures = {"jpg", "png", "bmp", "gif", "webp", "cr2", "nef", "psd", "tiff"};
    static String[] Videos = {"mp4", "avi", "mov", "m4v", "mkv", "mpg", "mpeg", "wmv", "3gp"};
    static String[] Audios = {"mp3", "ogg", "acc", "aac", "wav", "opus", "wma", "flac", "mpa"};
    static String[] Docs = {"doc", "docx", "pdf", "rtf", "cdr", "xls", "xlsx", "pptx", "ppt"};
    static String[] Archives = {"rar", "zip", "7z", "tar", "arj"};
    static String[] Trashs = {"tmp", "thumb0", "thumb1", "thumb2", "thumb3", "thumb4", "thumb5", "thumb6", "thumb7", "thumb8", "thumb9", "thumb10"};
    static String[] Folders = {"Pictures", "Videos", "Audios", "Docs", "Archives", "Trashs"};
    private static File directory; // /home/mike/Downloads/Telegram

    public static void main(String[] args) {
//        System.out.println("the folder is empty");
//    }
        MyFrame frame = new MyFrame();

        while (frame.getUserInput() == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        directory = new File(frame.getUserInput());

        if (directory.exists()) {
            newFolders(directory, Folders);
            File[] files = directory.listFiles();
            mySearch(files);
            deleteFoders(directory, Folders);
        } else {
            System.out.println("Invalid directory or directory does not exist.");
        }
//        System.out.println(Arrays.toString(directory.listFiles()));

        if (directory.listFiles().length > 0) {
            System.out.println("the folder is not empty");
        } else {
            System.out.println("the folder is empty");
            MyFrame.endShow();
        }

    }

    public static void mySearch(File[] files) {
        if (files != null && files.length > 0) {
            int count = 1;
            for (File file : files) {
                if (file.isFile()) {
                    count = writeHash(file, count);
//
                    String end = endTypes(file);

                    for (String type : Pictures) {
                        if (end.equals(type.toLowerCase())) {
                            moveFile(file, new File(directory.getParent() + File.separator + "Pictures" + File.separator + file.getName()));
                            System.out.println(" ");
                        }
                    }
                    for (String type : Videos) {
                        if (end.equals(type.toLowerCase())) {
                            moveFile(file, new File(directory.getParent() + File.separator + "Videos" + File.separator + file.getName()));
                            System.out.println(" ");
                        }
                    }
                    for (String type : Audios) {
                        if (end.equals(type.toLowerCase())) {
                            moveFile(file, new File(directory.getParent() + File.separator + "Audios" + File.separator + file.getName()));
                            System.out.println(" ");
                        }
                    }
                    for (String type : Docs) {
                        if (end.equals(type.toLowerCase())) {
                            moveFile(file, new File(directory.getParent() + File.separator + "Docs" + File.separator + file.getName()));
                            System.out.println(" ");
                        }
                    }
                    for (String type : Archives) {
                        if (end.equals(type.toLowerCase())) {
                            moveFile(file, new File(directory.getParent() + File.separator + "Archives" + File.separator + file.getName()));
                            System.out.println(" ");
                        }
                    }
                    for (String type : Trashs) {
                        if (end.equals(type.toLowerCase())) {
                            moveFile(file, new File(directory.getParent() + File.separator + "Trashs" + File.separator + file.getName()));
                            System.out.println(" ");
                        }
                    }
                } else if (file.isDirectory()) {
                    File podDirect = new File(file.getPath());
                    File[] podDirectFiles = podDirect.listFiles();
                    mySearch(podDirectFiles);
                }

            }
        }
    }

    public static void newFolders(File directory, String[] folderNames) {
        for (String f : folderNames) {
            File folder = new File(directory.getParent() + File.separator + f);
            if (!folder.exists()) {
                folder.mkdir();
//                System.out.println("Створено новий каталог: " + folder.getAbsolutePath());
            } else {
//                System.out.println("Каталог вже існує: " + folder.getAbsolutePath());
            }
        }
    }

    public static void deleteFoders(File directory, String[] folderNames) {
        for (String f : folderNames) {
            File folder = new File(directory.getParent() + File.separator + f);
            if (folder.exists()) {
//                System.out.println("Каталог видалено: " + folder.getName());
                folder.delete();
            } else {
//                System.out.println("Каталог: " + folder.getName() +  " не можна видалити, він не порожний.");
            }
        }
    }

    public static void moveFile(File file, File directory) {
        try {
            Files.move(file.toPath(), directory.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String endTypes(File file) {
        String[] extension = file.getName().split("\\.");
        return extension[1].toLowerCase();
    }

    public static String foundHash(File file) {
        String hash = "";
        try {
            String filePath = file.getParent() + File.separator + file.getName();
            MessageDigest md = MessageDigest.getInstance("MD5");

            try (FileInputStream fis = new FileInputStream(filePath);
                 DigestInputStream dis = new DigestInputStream(fis, md)) {
                while (dis.read() != -1) {}
                byte[] digest = md.digest();  // Оновлене ім'я змінної
                StringBuilder hexHash = new StringBuilder();
                for (byte b : digest) {
                    hexHash.append(String.format("%02x", b));
                }
                System.out.println("MD5 hash file: " + hexHash.toString());
                hash = hexHash.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static int writeHash(File file, int count) {
        long fileSize = file.length();
        System.out.println("Lp: " + count);
        System.out.println("Name: " + file.getName());
        System.out.println("Size: " + fileSize);

        String hash = foundHash(file);
        NewExcel.writeExcel(file, count, hash);
        count++;
        return count;
    }
}