/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.xebia.xke.jsfdemo.web;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author yrenaut
 */
@ManagedBean
@ViewScoped
public class NewJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public NewJSFManagedBean() {
    }
    
    public String getName() {
        return "Toto";
    }
    
    public void doFakeAction() {
        System.out.println("Init action");
    }
    
    public void preDestroy() {
        System.out.println("Dying view scope managed bean");
    }
}
