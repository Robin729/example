package example.effective.java;

import java.io.*;

public class TryWithResources {

    public static void main(String[] args) throws Exception {
        // firsttLineOfFile("db");
        // firstLineOfFile("dd");

        try(TryWithResources.Close close = new TryWithResources.Close()) {
            throw new Exception("2 exception");
            //System.out.println("hh");
        }
    }

    // try-with-resources - the the best way to close resources!
    static String firsttLineOfFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }

    // try-with-resources on multiple resources - short and sweet
    static void copy(String src, String dst) throws IOException {
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dst)) {
            byte[] buf = new byte[10];
            int n;
            while((n = in.read(buf)) >= 0)
                out.write(buf, 0, n);
        }
    }

    // try-finally - No longer the best way to close resources!
    static String firstLineOfFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            return br.readLine();
        } finally {
            br.close();
        }
    }


    public static class Close implements AutoCloseable {

        public Close() throws Exception {
            // construct();
        }

        private void construct() throws Exception {
            throw new Exception("1 exception");
        }
        @Override
        public void close() throws Exception {
            throw new Exception("3 exception");
        }
    }
}
