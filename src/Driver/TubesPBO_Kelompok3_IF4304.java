/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver;

import Controller.ControllerLogin;
import View.Login;

/**
 *
 * @author Shadif
 */
public class TubesPBO_Kelompok3_IF4304 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here        
        Login frmLogin = new Login();
        ControllerLogin controller = new ControllerLogin(frmLogin);
        frmLogin.setVisible(true);
        frmLogin.setLocationRelativeTo(null);
    }
    
}
