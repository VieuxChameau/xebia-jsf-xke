/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.xebia.xke.jsfdemo.web;

import com.google.common.collect.ImmutableList;
import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author yrenaut
 */
@ManagedBean
@ViewScoped
public class LanguageBean implements Serializable{

    private static final ImmutableList<String> supportedLangs = ImmutableList.of("fr", "en");
    private String lang;
    private Locale locale;

    /**
     * Creates a new instance of LanguageBean
     */
    public LanguageBean() {
    }

    public void initLanguage() {
        if (!supportedLangs.contains(lang)) {
            lang = "fr";
        }
        locale = new Locale(lang);
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Locale getLocale() {
        return locale;
    }
}
