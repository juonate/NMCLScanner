/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gmdnet.webmed.fg.action.tools;

/**
 *
 * @author Juan
 */
public class Config {
    public static String getClientDllPath(){
        String basePath = System.getProperty("user.home");
        String pathDll = basePath + "AspriseJTwain.dll";
        return pathDll;
    }
}
