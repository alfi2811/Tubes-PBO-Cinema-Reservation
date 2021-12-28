/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.List;
import Model.ModelFilm;
import Model.ModelSchedule;
import Model.ModelStaff;
import View.AdminDashboard;
import View.addFilm;
import View.addSchedule;
import View.deleteFilm;
import View.editFilm;
import dbhelper.DAOAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author user
 */
public class ControllerAdmin {
    private List<ModelFilm> list;
    private List<ModelSchedule> listSchedule;
    private ModelStaff staff;
    private ModelFilm film;
    private AdminDashboard frmAdminDashboard;
    private addFilm frmAddFilm;
    private editFilm frmEditFilm;
    private deleteFilm frmDeleteFilm;
    private addSchedule frmAddSchedule;
    
    private DAOAdmin daoadmin;
    
    public ControllerAdmin(ModelStaff staff) {        
        this.staff = staff;        
        frmAdminDashboard = new AdminDashboard();
        this.frmAdminDashboard.getButtonEdit().setEnabled(false);
        this.frmAdminDashboard.getButtonDelete().setEnabled(false);        
        
        this.frmAdminDashboard.actionListener(new ControllerAdmin.ButtonListener());
        this.frmAdminDashboard.mouseAdapter(new MousePressed());
        
        frmAddFilm = new addFilm();        
        this.frmAddFilm.actionListener(new ControllerAdmin.ButtonListener());
        frmEditFilm = new editFilm();  
        this.frmEditFilm.actionListener(new ControllerAdmin.ButtonListener());        
        frmDeleteFilm = new deleteFilm(); 
        this.frmDeleteFilm.actionListener(new ControllerAdmin.ButtonListener());
        
        frmAddSchedule = new addSchedule();
        this.frmAddSchedule.actionListener(new ControllerAdmin.ButtonListener());
        
        daoadmin = new DAOAdmin();
        this.frmAdminDashboard.setVisible(true);
        loadList();                
    }
    public final void loadList(){
        DefaultTableModel tbl = (DefaultTableModel) frmAdminDashboard.getTabelFilm().getModel();        
        
        tbl.setRowCount(0);        
        list = daoadmin.getAllFilm();
        
        list.forEach((data) -> {
            System.out.println(data.getTitle());
            tbl.insertRow(tbl.getRowCount(), new Object[]{data.getId_film(), data.getTitle(), data.getGenre(), data.getDate_start(), data.getDate_end()});
        });
    }
    
    public final void loadListSchedule(){
        DefaultTableModel tblSchedule = (DefaultTableModel) frmAdminDashboard.getTabelSchedule().getModel();
        int i = frmAdminDashboard.getTabelFilm().getSelectedRow();
        System.out.println("i: " + i);
        ModelFilm filmSelected = list.get(i);
        tblSchedule.setRowCount(0);
        
        listSchedule = daoadmin.getAllSchedule(filmSelected.getId_film());
        listSchedule.forEach((data) -> {
            System.out.println(data.getPrice());
            tblSchedule.insertRow(tblSchedule.getRowCount(), 
                    new Object[]{data.getId_schedule(), data.getFilm_id(), data.getTheater(), data.getTime(), data.getPrice()});
        });
    }
    
    public final void insertFilm() throws ParseException{
        ModelFilm filmNew = new ModelFilm();
        filmNew.setTitle(frmAddFilm.getTitle());
        filmNew.setGenre(frmAddFilm.getGenre());
        Date get_date_start = new Date(frmAddFilm.getDateStart().getTime());        
        Date get_date_end = new Date(frmAddFilm.getDateEnd().getTime());        
        filmNew.setDate_start(get_date_start);
        filmNew.setDate_end(get_date_end);
        System.out.println(filmNew.getTitle());
        daoadmin.insertFilm(filmNew);
        loadList();
        frmAddFilm.setVisible(false);
        frmAdminDashboard.setVisible(true);
    }
    
    public final void insertSchedule() throws ParseException{
        ModelSchedule scheduleNew = new ModelSchedule();
        scheduleNew.setFilm_id(frmAddSchedule.getFilm());
        scheduleNew.setTheater(frmAddSchedule.getTheater());
        scheduleNew.setPrice(frmAddSchedule.getPrice());
        scheduleNew.setTime(frmAddSchedule.getTime());
        System.out.println(scheduleNew.getFilm_id());
        daoadmin.insertSchedule(scheduleNew);
        loadListSchedule();
        frmAddSchedule.setVisible(false);
        frmAdminDashboard.setVisible(true);
    }
    
    public final void editFilm() throws ParseException{
        ModelFilm filmNew = new ModelFilm();
        filmNew.setId_film(frmEditFilm.getID());
        filmNew.setTitle(frmEditFilm.getTitle());
        filmNew.setGenre(frmEditFilm.getGenre());
        
        Date get_date_start = new Date(frmEditFilm.getDateStart().getTime());        
        Date get_date_end = new Date(frmEditFilm.getDateEnd().getTime());        
        filmNew.setDate_start(get_date_start);
        filmNew.setDate_end(get_date_end);
        
        System.out.println(filmNew.getTitle());
        daoadmin.updateFilm(filmNew);
        loadList();
        frmEditFilm.setVisible(false);
        frmAdminDashboard.setVisible(true);
    }
    
    public final void deleteFilm() {
        int id = frmDeleteFilm.getID();
        daoadmin.deleteFilm(id);
        loadList();
        frmDeleteFilm.setVisible(false);
        frmAdminDashboard.setVisible(true);
    }
    
    public class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source.equals(frmAdminDashboard.getButtonAdd())) {
                int tabIdx = frmAdminDashboard.getTabAdmin().getSelectedIndex();                    
                if(tabIdx == 0) {
                    System.out.println("tab: " + tabIdx);
                    frmAddFilm.setVisible(true);                    
                } else if (tabIdx == 1) {
                    frmAddSchedule.setVisible(true);                    
                }
                frmAdminDashboard.setVisible(false);
            } else if (source.equals(frmAddFilm.getAddButton())) {
                try {
                    insertFilm();
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (source.equals(frmAddSchedule.getAddButton())) {
                try {
                    insertSchedule();
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (source.equals(frmAdminDashboard.getButtonEdit())) {
                int i = frmAdminDashboard.getTabelFilm().getSelectedRow();
                System.out.println("i: " + i);
                ModelFilm filmSelected = list.get(i);
                System.out.println("selected: " + filmSelected.getTitle());
                frmEditFilm.setDataFilm(
                        filmSelected.getId_film(), 
                        filmSelected.getTitle(), 
                        filmSelected.getGenre(), 
                        filmSelected.getDate_start().toString(), 
                        filmSelected.getDate_end().toString()
                );
                frmEditFilm.setVisible(true);
                frmAdminDashboard.setVisible(false);
            } else if (source.equals(frmEditFilm.getEditButton())) {
                try {
                    editFilm();
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (source.equals(frmAdminDashboard.getButtonDelete())) {
                int i = frmAdminDashboard.getTabelFilm().getSelectedRow();
                System.out.println("i: " + i);
                ModelFilm filmSelected = list.get(i);
                frmDeleteFilm.setConfirm(filmSelected.getId_film(), filmSelected.getTitle());
                frmDeleteFilm.setVisible(true);
                frmAdminDashboard.setVisible(false);
            } else if (source.equals(frmDeleteFilm.getButtonYes())) {
                deleteFilm();
            }
        }        
    }
    
    public class MousePressed extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent me) {
            // compiled code
            Object source = me.getSource();
            if (source.equals(frmAdminDashboard.getTabelFilm())) {
                System.out.println("aaaaa");
                frmAdminDashboard.getButtonEdit().setEnabled(true);
                frmAdminDashboard.getButtonDelete().setEnabled(true);
                loadListSchedule();
            } else if (source.equals(frmAdminDashboard.getTabAdmin())) {                              
                int i = frmAdminDashboard.getTabelFilm().getSelectedRow();                
                int tabIdx = frmAdminDashboard.getTabAdmin().getSelectedIndex();                    
                System.out.println("anjay masuk " + i+ tabIdx);
                if(tabIdx == 1 && i < 0) {
                    frmAdminDashboard.getButtonAdd().setEnabled(false);        
                } else {
                    frmAdminDashboard.getButtonAdd().setEnabled(true);
                }
            }
        } 
    }
}
