/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ModelStaff;
import View.Login;
import dbhelper.DAOStaff;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author user
 */
public class ControllerLogin {
    private Login frmLogin;
    private List<ModelStaff> listStaff;
    private ModelStaff staff;
    private DAOStaff daostaff;
    private ControllerAdmin cAdmin;
    private ControllerCashier cCashier;
    
    
    public ControllerLogin() {        
        this.frmLogin = new Login();        
        this.frmLogin.actionListener(new ButtonListener());
        this.frmLogin.setVisible(true);
        this.frmLogin.setLocationRelativeTo(null);
        daostaff = new DAOStaff();
    }
    public void checkLogin(){
        staff = new ModelStaff();
        
        String username = frmLogin.getUsername();        
        String password = frmLogin.getPassword();                
        staff = daostaff.getStaff(username, password);        
        if(staff.getRole().equals("manajer")) {
            this.frmLogin.setVisible(false);
            cAdmin = new ControllerAdmin(staff);        
        } else if (staff.getRole().equals("kasir")) {
            this.frmLogin.setVisible(false);
            cCashier = new ControllerCashier(staff); 
        } else {            
            this.frmLogin.displayError();
        }
    }
    public class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {                
                case "Login":                    
                    checkLogin();
                    break;                
                default:
                    break;
            }
        }        
    } 
}
