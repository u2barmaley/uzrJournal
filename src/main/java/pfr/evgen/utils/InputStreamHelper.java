package pfr.evgen.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class InputStreamHelper {
    private String Filepath;
    private ArrayList<InputStream> streams = new ArrayList();
    private HashMap<String, Integer> position = new HashMap();

    public InputStreamHelper(String odt) {
        this.Filepath = odt;
    }

    public void closeStreams() {
        Iterator var1 = this.streams.iterator();

        while(var1.hasNext()) {
            InputStream s = (InputStream)var1.next();

            try {
                s.close();
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }

    }

    public InputStream getInputStreamByName(String name) {
        int i = (Integer)this.position.get(name);
        return (InputStream)this.streams.get(i);
    }

    public ArrayList<InputStream> readODT() {
        try {
            return this.read();
        } catch (IOException var2) {
            var2.printStackTrace();
            System.out.println("File not found or incorrect");
            return null;
        }
    }

    private ArrayList<InputStream> read() throws IOException {
        ZipFile zipFile = new ZipFile(this.Filepath);
        Enumeration entries = zipFile.entries();

        while(entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry)entries.nextElement();
            InputStream stream;
            if (entry.getName().equals("content.xml")) {
                stream = zipFile.getInputStream(entry);
                this.position.put("content.xml", this.streams.size());
                this.streams.add(stream);
            }

            if (entry.getName().contains("Pictures")) {
                stream = zipFile.getInputStream(entry);
                this.position.put(entry.getName().replace("Pictures/", ""), this.streams.size());
                this.streams.add(stream);
            }
        }

        return this.streams;
    }
}