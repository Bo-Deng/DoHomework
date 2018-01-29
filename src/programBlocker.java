/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dohomework;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author radloff_859936
 */
public class programBlocker extends Thread {

    private ArrayList<String> programs;
    private int on;

    public programBlocker() {
        programs = new ArrayList<String>();
        On();
    }

    public void On() {
        on = 1;
    }

    public void Off() {
        on = 0;
    }

    public void addProgram(String z) {
        programs.add(z);
    }

    public void removeProgram(String y) {
        programs.remove(y);
    }

    @Override
    public void run() {
        try {
            while (1 == 1) {
                if (on == 1) {
                    Process p = Runtime.getRuntime().exec("tasklist");
                    Scanner read = new Scanner(new InputStreamReader(p.getInputStream()));
                    while (read.hasNextLine()) {
                        String b = read.nextLine();
                        for (String s : programs) {
                            if (b.contains(s)) {
                                Runtime.getRuntime().exec("TASKKILL /F /IM " + s);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
