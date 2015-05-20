/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gmdnet.webmed.fg.action;

import de.gmdnet.webmed.fg.FgComponent;
import de.gmdnet.webmed.internal.LoggerNames;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Juan
 */
public class ActionScan extends FgActionAdapter {

    private static Logger logger = LoggerNames.FORM_LOGGER.getLogger();
    private static final String CLASS_NAME = ActionScan.class.getSimpleName();
    private static final Long serialVersionUID = Long.valueOf(-76538273649872413L);
    private static Image targetFile;
    private static String Servlet;
    private static String idParent;
    private static String Parent;
    private static String uploadDir;
    private static String filePath;

    public ActionScan() {
        registerActionMethod("Scan", new String[]{"URL", "Parent", "idParent", "uploadDir", "filePath"});
    }

    public void methodScan(Hashtable<String, String> inputData, FgComponent requestingElement) {
        try {
            //        Servlet = (String) inputData.get("URL");
            //        Parent = (String) inputData.get("Parent");
            //        idParent = (String) inputData.get("idParent");
            //        uploadDir = (String) inputData.get("PATH");
            //        filePath = (String) inputData.get("filePath");

            Servlet = "http://localhost:8080/NMCLUploadArchivoServlet/uploadFile";
            uploadDir = null;
            filePath = "C:\\Users\\Juan\\Desktop\\Capturas\\modulo_busqueda.jpg";
            //        try {
            ////            Source source = SourceManager.instance().getDefaultSource();
            //            Source source = SourceManager.instance().selectSourceUI();
            //            source.setUIEnabled(false);
            //            source.open();
            //            targetFile = source.acquireImage();
            ////            targetFile = (File)image;
            ////            BufferedImage image = source.acquireImageAsBufferedImage();
            ////            ImageIO.write(image, "png", new File("C:\\destino\\test3.png"));
            //            imprimir(source.getPrinter());
            //            logger.error(Servlet+ " " + " " + Parent +" "+ idParent+ " " + uploadDir);
            //            SaveImage sv = new SaveImage();
            //            sv.enviarImagen(Servlet, filePath, uploadDir);
            //            source.close();
            //        } catch (Exception e) {
            //        }
            SaveImage sv = new SaveImage();
            sv.enviarImagen(Servlet, filePath, uploadDir);
        } catch (MalformedURLException ex) {
            java.util.logging.Logger.getLogger(ActionScan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ActionScan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void imprimir(Object o) {
        System.out.println(o);
    }

    public static void main(String args[]) {
        ActionScan scan = new ActionScan();
        scan.methodScan(null, null);
    }

    public class SaveImage {

        static final int BUFFER_SIZE = 16384;

        public void enviarImagen(String Servlet, String filePath, String uploadDir) throws MalformedURLException, IOException {
            String charset = "UTF-8";
            File uploadFile1 = new File(filePath);
            String requestURL = Servlet;

            try {
                MultipartUtility multipart = new MultipartUtility(requestURL, charset);

                multipart.addFilePart("file1", uploadFile1);

                List<String> response = multipart.finish();

                System.out.println("SERVER REPLIED:");

                for (String line : response) {
                    System.out.println(line);
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
