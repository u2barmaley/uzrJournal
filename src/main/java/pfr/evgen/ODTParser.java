package pfr.evgen;

import org.apache.commons.io.IOUtils;
import pfr.evgen.utils.InputStreamHelper;
import pfr.evgen.utils.XMLHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ODTParser {
    private String path_to_odt;

    private InputStreamHelper inputStreamHelper;

    public ODTParser() {
    }

    public ODTParser(String path_to_odt) {
        this.path_to_odt = path_to_odt;
    }

    public void setODT(String path_to_odt) {
        this.path_to_odt = path_to_odt;
    }

    public String transform() {
        this.inputStreamHelper = new InputStreamHelper(this.path_to_odt);
        this.inputStreamHelper.readODT();
        XMLHelper xmlHelper = new XMLHelper();
        String text_with_placeholder = xmlHelper.getTextsWithImagePlaceholder(this.inputStreamHelper.getInputStreamByName("content.xml"));
        String filled = "";

        try {
            filled = this.fillPlaceholderWithPictures(text_with_placeholder);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        this.inputStreamHelper.closeStreams();
        return filled;
    }

    private String fillPlaceholderWithPictures(String text_with_placeholder) throws IOException {
        String[] row = text_with_placeholder.split("\n");

        for(int i = 0; i < row.length; ++i) {
            if (row[i].toLowerCase().contains("pictures")) {
                InputStream inputStream = this.inputStreamHelper.getInputStreamByName(row[i].replace("Pictures/", ""));
                byte[] bytes = IOUtils.toByteArray(inputStream);
                String encodedBase64 = new String(Base64.getEncoder().encode(bytes));
                row[i] = "<img src=\"data:image;base64," + encodedBase64 + "\" />";
            }
        }

        return String.join("\n", row);
    }
}
