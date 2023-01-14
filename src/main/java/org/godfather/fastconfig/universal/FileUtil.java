package org.godfather.fastconfig.universal;

import org.godfather.fastconfig.bukkit.FastConfig;

import java.io.*;

@SuppressWarnings("unused")
public final class FileUtil {

    public static void copy(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                boolean mkdir = destination.mkdir();
                if (!mkdir)
                    FastConfig.LOGGER.warning("§cDirectory created.");
            }
            String[] files = source.list();
            if (files == null)
                return;

            for (String file : files) {
                File newSource = new File(source, file);
                File newDestination = new File(destination, file);
                copy(newSource, newDestination);
            }
        } else {
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(destination);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        }
    }

    public static void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null)
                return;
            for (File child : files) {
                delete(child);
            }
        }
        boolean delete = file.delete();
        if(!delete)
            FastConfig.LOGGER.warning("§cFile removal failed.");
    }
}
