package com.japhar.pdfparsersample;



import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

public class PdfExtractor {
  public Map<String, Object> processRecord(File file) {
    Map<String, Object> map = new HashMap<String, Object>();
    try {
      InputStream input = null;
      if (file != null) {
        System.out.println("about to parse...");
        try {
          input = FileUtils.openInputStream(file); //new DataInputStream(new FileInputStream(file));
          BodyContentHandler handler = new BodyContentHandler();
          Metadata metadata = new Metadata();
          AutoDetectParser parser = new AutoDetectParser();
          ParseContext parseContext = new ParseContext();
          parser.parse(input, handler, metadata, parseContext);
          System.out.println(handler);
          map.put("text", handler.toString());
          //map.put("title", metadata.get(TikaCoreProperties.TITLE));
          //map.put("pageCount", metadata.get("xmpTPg:NPages"));
          //map.put("status_code", response.getStatusLine().getStatusCode() + "");
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (input != null) {
            try {
              input.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return map;
  }

  public static void main(String arg[]) {
    PdfExtractor webPagePdfExtractor = new PdfExtractor();
    String homeDirectory = System.getProperty("user.dir");
    String filePath = Paths.get(homeDirectory, "_Docs", "invoice", "detail-file-02.pdf").toString();
    File file = new File(filePath);
    Map<String, Object> extractedMap = webPagePdfExtractor.processRecord(file);
    System.out.println(extractedMap.get("text"));
  }
}
