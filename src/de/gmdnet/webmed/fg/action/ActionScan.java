/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gmdnet.webmed.fg.action;

import com.asprise.util.jtwain.Source;
import com.asprise.util.jtwain.SourceManager;
import de.gmdnet.webmed.fg.FgComponent;
import de.gmdnet.webmed.internal.LoggerNames;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

/**
 *
 * @author Juan
 */
public class ActionScan extends FgActionAdapter{
    
    private static Logger logger = LoggerNames.FORM_LOGGER.getLogger();
    private static final String CLASS_NAME = ActionScan.class.getSimpleName();
    private static final Long serialVersionUID = Long.valueOf(-76538273649872413L);
    
    public ActionScan(){
        registerActionMethod("Scan", new String[] {"URL"});
    }
    
    public void methodScan(Hashtable<String, String> inputData, FgComponent requestingElement) {
        String url = (String)inputData.get("URL");
       try {
//            Source source = SourceManager.instance().getDefaultSource();
            Source source = SourceManager.instance().selectSourceUI();
            source.setUIEnabled(false);
            source.open();
//            Image image = source.acquireImage();
            BufferedImage image = source.acquireImageAsBufferedImage();
            ImageIO.write(image, "png", new File("C:\\destino\\test3.png"));
            imprimir(source.getPrinter());
            logger.error(url);
            source.close();
        } catch(Exception e){ 
        
        } 
    }
    
    public static void imprimir(Object o){
        System.out.println(0);
    }
    
//    public static void main (String args[]){
//        ActionScan scan = new ActionScan();
//        scan.methodScan(null, null);
//    }
}
