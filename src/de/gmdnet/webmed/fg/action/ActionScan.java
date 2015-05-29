/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gmdnet.webmed.fg.action;

import com.asprise.util.jtwain.Source;
import com.asprise.util.jtwain.SourceManager;
import de.gmdnet.webmed.fg.action.tools.MultipartUtility;
import de.gmdnet.webmed.fg.FgComponent;
import de.gmdnet.webmed.internal.LoggerNames;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.String;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Juan
 */
public class ActionScan extends FgActionAdapter {

    private static Logger logger = LoggerNames.FORM_LOGGER.getLogger();
    private static final String CLASS_NAME = ActionScan.class.getSimpleName();
    private static final Long serialVersionUID = Long.valueOf(-76538273649872413L);
    private static Image targetFile;
    private static File tempFile;
    private static String Servlet;
    private static String idParent;
    private static String Parent;
    private static String uploadDir;
    private static String filePath;

    public ActionScan() {
        registerActionMethod("Scan", new String[]{"Servlet", "pathFileTarget", "dateFileTarget"});
    }

    public void methodScan(Hashtable<String, String> inputData, FgComponent requestingElement) throws SAXException, TransformerConfigurationException, TransformerException, ParserConfigurationException {
        try {
            Servlet = parseParam(inputData, "Servlet");
            String pathFile = (String) inputData.get("pathFileTarget");
            String dateFile = (String) inputData.get("dateFileTarget");

//            Servlet = "http://localhost:8080/NMCLUploadArchivoServlet/uploadFile";
//            String pathFile = "PATH_FILE";
//            String dateFile = "DATE_FILE";

            //            Source source = SourceManager.instance().getDefaultSource();
            Source source = SourceManager.instance().selectSourceUI();
            source.setUIEnabled(false);
            source.open();
            source.acquireImage();
//                        targetFile = (File)image;
            tempFile = source.saveLastAcquiredImageIntoTemporaryFile();
            imprimir(tempFile);
//            BufferedImage image = source.acquireImageAsBufferedImage();
//            ImageIO.write(image, "png", new File("C:\\destino\\test3.png"));
//            filePath = "C:\\destino\\test3.png";
            SaveImage sv = new SaveImage();
            imprimir("pase por aquí");
            sv.enviarImagen(Servlet, tempFile, pathFile, dateFile);
            imprimir("pase por aquí");
            source.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void imprimir(Object o) {
        System.out.println(o);
    }

//    public static void main(String args[]) throws SAXException, TransformerConfigurationException, TransformerException, ParserConfigurationException {
//        ActionScan scan = new ActionScan();
//        scan.methodScan(null, null);
//    }
    public class SaveImage {

        static final int BUFFER_SIZE = 16384;

        public void enviarImagen(String Servlet, File file, String pathFile, String dateFile) throws MalformedURLException, TransformerConfigurationException, TransformerException, ParserConfigurationException, SAXException {
            String charset = "UTF-8";
            String requestURL = Servlet;
            String xmlResponse = null;
            imprimir("empieza servlet");
            try {
                MultipartUtility multipart = new MultipartUtility(requestURL, charset);
                multipart.addFilePart("file1", file);
                List<String> response = multipart.finish();

                imprimir("SERVER REPLIED:");
                for (String line : response) {
                    xmlResponse = line;
                }
                DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
                DocumentBuilder bldr = fctr.newDocumentBuilder();
                InputSource insrc = new InputSource(new StringReader(xmlResponse));
                Document doc = bldr.parse(insrc);
                NodeList fechaList = doc.getElementsByTagName("fechaText");
                NodeList rutaList = doc.getElementsByTagName("rutaArchivo");

                String fechaArchivo = fechaList.item(0).getChildNodes().item(0).getNodeValue();
                String rutaArchivo = rutaList.item(0).getChildNodes().item(0).getNodeValue();
                imprimir(fechaArchivo + " " + rutaArchivo);

                getForm().setValue(pathFile, rutaArchivo);
                getForm().setValue(dateFile, fechaArchivo);

            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
