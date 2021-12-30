/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.List;
import Model.*;
import View.AdminDashboard;
import View.addFilm;
import View.addSchedule;
import View.addStaff;
import View.deleteFilm;
import View.editFilm;
import View.editStaff;
import View.editSchedule;
import View.deleteSchedule;
import View.deleteStaff;
import dbhelper.DAOAdmin;
import dbhelper.DAOStaff;
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
    private List<ModelStaff> listStaff;
    private List<ModelTransaction> listTransaction;
    
    private ModelStaff staff;
    private ModelFilm film;
    
    private AdminDashboard frmAdminDashboard;
    private addFilm frmAddFilm;
    private editFilm frmEditFilm;
    private deleteFilm frmDeleteFilm;
    
    private addSchedule frmAddSchedule;   
    private editSchedule frmEditSchedule;
    private deleteSchedule frmDeleteSchedule;
    
    private addStaff frmAddStaff;   
    private editStaff frmEditStaff;   
    private deleteStaff frmDeleteStaff;   
    
    private DAOAdmin daoadmin;
    private DAOStaff daostaff;    
    
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
        frmEditSchedule = new editSchedule();
        this.frmEditSchedule.actionListener(new ControllerAdmin.ButtonListener());
        frmDeleteSchedule = new deleteSchedule();
        this.frmDeleteSchedule.actionListener(new ControllerAdmin.ButtonListener());
        
        frmAddStaff = new addStaff();
        this.frmAddStaff.actionListener(new ControllerAdmin.ButtonListener());
        frmEditStaff = new editStaff();
        this.frmEditStaff.actionListener(new ControllerAdmin.ButtonListener());
        frmDeleteStaff = new deleteStaff();
        this.frmDeleteStaff.actionListener(new ControllerAdmin.ButtonListener());
        
        daoadmin = new DAOAdmin();
        daostaff = new DAOStaff();
        this.frmAdminDashboard.setVisible(true);
        this.frmAdminDashboard.setTitle(staff.getName());
        loadList();  
        loadListStaff();
        loadListTransaction();
    }
    public final void loadList(){
        DefaultTableModel tbl = (DefaultTableModel) frmAdminDashboard.getTabelFilm().getModel();        
        
        tbl.setRowCount(0);        
        list = daoadmin.getAllFilm();
        
        list.forEach((data) -> {            
            tbl.insertRow(tbl.getRowCount(), new Object[]{data.getId_film(), data.getTitle(), data.getGenre(), data.getDate_start(), data.getDate_end()});
        });
    }
    
    public final void loadListStaff(){
        DefaultTableModel tbl = (DefaultTableModel) frmAdminDashboard.getTabelStaff().getModel();        
        
        tbl.setRowCount(0);        
        listStaff = daostaff.getAllStaff();
        
        listStaff.forEach((data) -> {            
            tbl.insertRow(tbl.getRowCount(), new Object[]{data.getId_staff(), data.getName(), data.getUsername(), data.getPhone(), data.getRole()});
        });
    }
    
    public final void loadListTransaction(){
        DefaultTableModel tbl = (DefaultTableModel) frmAdminDashboard.getTabelTransaction().getModel();        
        
        tbl.setRowCount(0);        
        listTransaction = daoadmin.getAllTransaction();
        
        listTransaction.forEach((data) -> {            
            tbl.insertRow(tbl.getRowCount(), new Object[]{data.getId_transaction(), data.getSchedule_id(), data.getSeat(), data.getTotal_price(), data.getDate_buy()});
        });
    }
    
    public final void loadListSchedule(){
        DefaultTableModel tblSchedule = (DefaultTableModel) frmAdminDashboard.getTabelSchedule().getModel();
        int i = frmAdminDashboard.getTabelFilm().getSelectedRow();        
        tblSchedule.setRowCount(0);
        if(i > -1) {            
            ModelFilm filmSelected = list.get(i);            

            listSchedule = daoadmin.getAllSchedule(filmSelected.getId_film());            
        } else {
            listSchedule = daoadmin.getAllSchedule(0);
        }
        listSchedule.forEach((data) -> {            
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
        
        daoadmin.insertSchedule(scheduleNew);
        loadListSchedule();
        frmAddSchedule.setVisible(false);
        frmAdminDashboard.setVisible(true);
    }
    
    public final void insertStaff() throws ParseException{
        ModelStaff staffNew = new ModelStaff();
        staffNew.setName(frmAddStaff.getName());
        staffNew.setUsername(frmAddStaff.getUsername());
        staffNew.setPassword(frmAddStaff.getPassword());
        staffNew.setPhone(frmAddStaff.getPhone());
        staffNew.setRole(frmAddStaff.getRole());        
        
        daostaff.insertStaff(staffNew);
        loadListStaff();
        frmAddStaff.setVisible(false);
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
                
        daoadmin.updateFilm(filmNew);
        loadList();
        frmEditFilm.setVisible(false);
        frmAdminDashboard.setVisible(true);
    }
    
    public final void editSchedule() throws ParseException {
        ModelSchedule scheduleNew = new ModelSchedule();
        scheduleNew.setId_schedule(frmEditSchedule.getID());
        scheduleNew.setTheater(frmEditSchedule.getTheater());
        scheduleNew.setPrice(frmEditSchedule.getPrice());
        scheduleNew.setTime(frmEditSchedule.getTime());
                
        daoadmin.updateSchedule(scheduleNew);
        loadListSchedule();
        frmEditSchedule.setVisible(false);
        frmAdminDashboard.setVisible(true);
    }
    
    public final void editStaff() throws ParseException {
        ModelStaff staffEdit = new ModelStaff();
        staffEdit.setId_staff(frmEditStaff.getID());
        staffEdit.setName(frmEditStaff.getName());
        staffEdit.setUsername(frmEditStaff.getUsername());
        staffEdit.setPassword(frmEditStaff.getPassword());
        staffEdit.setPhone(frmEditStaff.getPhone());
        staffEdit.setRole(frmEditStaff.getRole());
                
        daostaff.updateStaff(staffEdit);
        loadListStaff();
        frmEditStaff.setVisible(false);
        frmAdminDashboard.setVisible(true);
    }
    
    public final void deleteFilm() {
        int id = frmDeleteFilm.getID();
        daoadmin.deleteFilm(id);
        loadList();
        frmDeleteFilm.setVisible(false);
        frmAdminDashboard.setVisible(true);
    }
    
    public final void deleteSchedule() {
        int id = frmDeleteSchedule.getID();
        daoadmin.deleteSchedule(id);
        loadListSchedule();
        frmDeleteSchedule.setVisible(false);
        frmAdminDashboard.setVisible(true);
    }
    
    public final void deleteStaff() {
        int id = frmDeleteStaff.getID();
        daostaff.deleteStaff(id);
        loadListStaff();
        frmDeleteStaff.setVisible(false);
        frmAdminDashboard.setVisible(true);
    }
    
    public final void logout(){        
        new ControllerLogin();
        frmAdminDashboard.dispose();
    }
    
    public class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source.equals(frmAdminDashboard.getButtonAdd())) {
                int tabIdx = frmAdminDashboard.getTabAdmin().getSelectedIndex();
                if(tabIdx == 0) {
                    frmAddFilm.setTitle(staff.getName());
                    frmAddFilm.setVisible(true);
                } else if (tabIdx == 1) {
                    frmAddSchedule.setTitle(staff.getName());
                    frmAddSchedule.setVisible(true);                    
                } else if (tabIdx == 2) {
                    frmAddStaff.setTitle(staff.getName());
                    frmAddStaff.setVisible(true);                    
                }
                frmAdminDashboard.setVisible(false);
            } else if (source.equals(frmAdminDashboard.getButtonRefresh())) {
                loadList();
                frmAdminDashboard.getTabelFilm().clearSelection();
                loadListSchedule();
                loadListStaff();
                loadListTransaction();
            } else if (source.equals(frmAdminDashboard.getButtonLogOut())) {
                logout();                
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
            } else if (source.equals(frmAddStaff.getAddButton())) {
                try {
                    insertStaff();
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (source.equals(frmAdminDashboard.getButtonEdit())) {
                int i = frmAdminDashboard.getTabelFilm().getSelectedRow();
                int j = frmAdminDashboard.getTabelSchedule().getSelectedRow();                
                int tabIdx = frmAdminDashboard.getTabAdmin().getSelectedIndex();
                if (tabIdx == 1 && j > -1) {
                    ModelSchedule scheduleSelected = listSchedule.get(j);                    
                    frmEditSchedule.setTitle(staff.getName());
                    frmEditSchedule.setDataSchedule(
                            scheduleSelected.getId_schedule(),
                            scheduleSelected.getFilm_id(),
                            scheduleSelected.getPrice(),
                            scheduleSelected.getTheater(),
                            scheduleSelected.getTime()
                    );
                    frmEditSchedule.setVisible(true);
                    frmAdminDashboard.setVisible(false);
                } else if (tabIdx == 2) {
                    int idx = frmAdminDashboard.getTabelStaff().getSelectedRow();
                    ModelStaff staffSelected = listStaff.get(idx);
                    
                    frmEditStaff.setTitle(staff.getName());
                    frmEditStaff.setDataStaff(
                            staffSelected.getId_staff(),
                            staffSelected.getName(), 
                            staffSelected.getUsername(),
                            staffSelected.getPassword(),
                            staffSelected.getPhone(),
                            staffSelected.getRole());
                    
                    frmEditStaff.setVisible(true);
                    frmAdminDashboard.setVisible(false);
                } else {
                    ModelFilm filmSelected = list.get(i);
                    
                    frmEditFilm.setTitle(staff.getName());
                    frmEditFilm.setDataFilm(
                            filmSelected.getId_film(), 
                            filmSelected.getTitle(), 
                            filmSelected.getGenre(), 
                            filmSelected.getDate_start().toString(), 
                            filmSelected.getDate_end().toString()
                    );
                    frmEditFilm.setVisible(true);
                    frmAdminDashboard.setVisible(false);
                }                
                
            } else if (source.equals(frmEditFilm.getEditButton())) {
                try {
                    editFilm();
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (source.equals(frmEditSchedule.getEditButton())) {
                try {
                    editSchedule();
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (source.equals(frmEditStaff.getEditButton())) {
                try {
                    editStaff();
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (source.equals(frmAdminDashboard.getButtonDelete())) {                
                int i = frmAdminDashboard.getTabelFilm().getSelectedRow();
                int j = frmAdminDashboard.getTabelSchedule().getSelectedRow();
                
                int tabIdx = frmAdminDashboard.getTabAdmin().getSelectedIndex();
                if (tabIdx == 1 && j > -1) {
                    ModelSchedule scheduleSelected = listSchedule.get(j);
                    frmDeleteSchedule.setConfirm(scheduleSelected.getId_schedule(), scheduleSelected.getFilm_id(), scheduleSelected.getTime());
                    frmDeleteSchedule.setVisible(true);
                    frmAdminDashboard.setVisible(false);
                } else if (tabIdx == 2) {                    
                    int idx = frmAdminDashboard.getTabelStaff().getSelectedRow();
                    ModelStaff staffSelected = listStaff.get(idx);
                    frmDeleteStaff.setConfirm(staffSelected.getId_staff(), staffSelected.getName(), staffSelected.getRole());
                    frmDeleteStaff.setVisible(true);
                    frmAdminDashboard.setVisible(false);
                } else {                    
                    ModelFilm filmSelected = list.get(i);
                    frmDeleteFilm.setConfirm(filmSelected.getId_film(), filmSelected.getTitle());
                    frmDeleteFilm.setVisible(true);
                    frmAdminDashboard.setVisible(false);
                }                                
            } else if (source.equals(frmDeleteFilm.getButtonYes())) {
                deleteFilm();
            } else if (source.equals(frmDeleteSchedule.getButtonYes())) {
                deleteSchedule();
            } else if (source.equals(frmDeleteStaff.getButtonYes())) {
                deleteStaff();
            } else if (source.equals(frmDeleteFilm.getButtonNo()) || source.equals(frmDeleteSchedule.getButtonNo())
                    || source.equals(frmDeleteStaff.getButtonNo())) {
                frmDeleteFilm.setVisible(false);
                frmDeleteSchedule.setVisible(false);
                frmDeleteStaff.setVisible(false);
                frmAdminDashboard.setVisible(true);
            } else if (source.equals(frmAddFilm.getCancelButton()) || source.equals(frmEditFilm.getCancelButton()) 
                    || source.equals(frmAddSchedule.getCancelButton()) || source.equals(frmEditSchedule.getCancelButton())) {
                frmAddFilm.setVisible(false);
                frmEditFilm.setVisible(false);
                frmAddSchedule.setVisible(false);
                frmEditSchedule.setVisible(false);
                frmAdminDashboard.setVisible(true);
            }
        }        
    }
    
    public class MousePressed extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent me) {
            // compiled code
            Object source = me.getSource();
            if (source.equals(frmAdminDashboard.getTabelFilm()) || source.equals(frmAdminDashboard.getTabelStaff())) {                
                frmAdminDashboard.getButtonEdit().setEnabled(true);
                frmAdminDashboard.getButtonDelete().setEnabled(true);
                loadListSchedule();                
            } else if (source.equals(frmAdminDashboard.getTabAdmin())) {                              
                int i = frmAdminDashboard.getTabelFilm().getSelectedRow();                
                int tabIdx = frmAdminDashboard.getTabAdmin().getSelectedIndex();                    
                
                if(tabIdx == 1 && i < 0 || tabIdx == 3) {
                    frmAdminDashboard.getButtonAdd().setEnabled(false);        
                    frmAdminDashboard.getButtonEdit().setEnabled(false);        
                    frmAdminDashboard.getButtonDelete().setEnabled(false);        
                } else {
                    frmAdminDashboard.getButtonAdd().setEnabled(true);
                }
            }
        } 
    }
}
