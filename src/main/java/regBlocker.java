/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author radloff_859936
 */
public class regBlocker {

    private ArrayList<String> programs;

    public regBlocker() {
        programs = new ArrayList<String>();
        try {
        Runtime.getRuntime().exec("REG ADD HKEY_CURRENT_USER/Software/Microsoft/Windows/CurrentVersion/Policies/Explorer");
        Runtime.getRuntime().exec("REG ADD HKEY_CURRENT_USER/Software/Microsoft/Windows/CurrentVersion/Policies/Explorer /v DisallowRun /t REG_DWORD /d 1");
        Runtime.getRuntime().exec("REG ADD HKEY_CURRENT_USER/Software/Microsoft/Windows/CurrentVersion/Policies/Explorer/DisallowRun");
        } catch (IOException ex) {
            Logger.getLogger(regBlocker.class.getName()).log(Level.SEVERE, null, ex);
        }
        On();
    }

    public void On() {
        for (String s : programs) {
            addProgram(s);
        }
    }

    public void Off() {
        try {
            Runtime.getRuntime().exec("REG DELETE HKEY_CURRENT_USER/Software/Microsoft/Windows/CurrentVersion/Policies/Explorer/DisallowRun /va /f");
            Runtime.getRuntime().exec("taskkill /im explorer.exe /f");
            Runtime.getRuntime().exec("start explorer.exe");
        } catch (IOException ex) {
            Logger.getLogger(regBlocker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addProgram(String tname) {
        try {
            Runtime.getRuntime().exec("REG ADD HKEY_CURRENT_USER/Software/Microsoft/Windows/CurrentVersion/Policies/Explorer/DisallowRun /v " + tname + " /t REG_SZ /d " + tname);
            Runtime.getRuntime().exec("taskkill /im explorer.exe /f");
            Runtime.getRuntime().exec("start explorer.exe");
        } catch (IOException ex) {
            Logger.getLogger(regBlocker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeProgram(String tname) {
        try {
            Runtime.getRuntime().exec("REG DELETE HKEY_CURRENT_USER/Software/Microsoft/Windows/CurrentVersion/Policies/Explorer/DisallowRun /v " + tname + " /f");
            Runtime.getRuntime().exec("taskkill /im explorer.exe /f");
            Runtime.getRuntime().exec("start explorer.exe");
        } catch (IOException ex) {
            Logger.getLogger(regBlocker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
