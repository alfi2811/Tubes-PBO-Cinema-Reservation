/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ModelFilm;
import Model.ModelSchedule;
import Model.ModelStaff;
import Model.ModelTransaction;
import View.CashierDashboard;
import View.chooseSeat;
import dbhelper.DAOAdmin;
import dbhelper.DAOKasir;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author user
 */
public class ControllerCashier {
    private List<ModelFilm> listFilm;
    private List<ModelSchedule> listSchedule;
    private ModelStaff staff;
    private ModelTransaction transaction;
    private CashierDashboard frmCashierDashboard;
    private chooseSeat frmChooseSeat;
    private DAOKasir daokasir;
    private DAOAdmin daoadmin;
    
    public ControllerCashier(ModelStaff staff) {
        this.staff = staff;
        transaction = new ModelTransaction();
        frmCashierDashboard = new CashierDashboard();        
        this.frmCashierDashboard.mouseAdapter(new MousePressed());        
        this.frmCashierDashboard.getButtonChoose().setEnabled(false);
        
        frmChooseSeat = new chooseSeat();
        daokasir = new DAOKasir();
        daoadmin = new DAOAdmin();        
        this.frmCashierDashboard.setVisible(true);
        loadListMovieShowing();        
    }
    
    public final void loadListMovieShowing(){
        Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println(sqlDate);
        listFilm = daokasir.getNowShowing(sqlDate);
        DefaultListModel dataList = new DefaultListModel();
        listFilm.forEach((p) -> {
            System.out.println(p.getTitle());
            dataList.addElement(p.getTitle());
        });     
        
        this.frmCashierDashboard.getListFilm().setModel(dataList);   
    }
    
    public final void loadListSchedule(int idFilm){                
        listSchedule = daoadmin.getAllSchedule(idFilm);
        DefaultListModel dataList = new DefaultListModel();
        listSchedule.forEach((p) -> {
            System.out.println(p.getPrice());
            dataList.addElement(p.getTime());
        });     
        
        this.frmCashierDashboard.getListSchedule().setModel(dataList);   
    }
    
    public class MousePressed extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent me) {
            // compiled code
            Object source = me.getSource();
            if (source.equals(frmCashierDashboard.getListFilm())) {
                int i = frmCashierDashboard.getListFilm().getSelectedIndex();
                ModelFilm filmSel = listFilm.get(i);  
                System.out.println("aaaaa");
                loadListSchedule(filmSel.getId_film());
            } else if (source.equals(frmCashierDashboard.getListSchedule())) {
                int selected = frmCashierDashboard.getListSchedule().getSelectedIndex();
                ModelSchedule scheduleSel = listSchedule.get(selected);  
                System.out.println("schedule"); 
                transaction.setSchedule_id(scheduleSel.getId_schedule());
                frmChooseSeat.setVisible(true);
            }
        } 
    }
    
}
