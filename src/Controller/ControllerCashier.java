/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import View.*;
import dbhelper.DAOAdmin;
import dbhelper.DAOKasir;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author user
 */
public class ControllerCashier {
    private List<ModelFilm> listFilm;
    private List<ModelSchedule> listSchedule;
    private List<String> listSeatNoAv;
    private ModelStaff staff;
    private ModelFilm filmSel;
    private ModelSchedule scheduleSel;
    private ModelTransaction transaction;    
    private CashierDashboard frmCashierDashboard;
    private ChooseSeat frmChooseSeat;
    private ConfirmTransaction frmConfirmTransaction;
    private SuccessTransaction frmSuccessTransaction;    
    private DAOKasir daokasir;
    private DAOAdmin daoadmin;
    private ControllerLogin cLogin;
    
    public ControllerCashier(ModelStaff staff) {
        this.staff = staff;        
        transaction = new ModelTransaction();
        frmCashierDashboard = new CashierDashboard();             
        this.frmCashierDashboard.mouseAdapter(new MousePressed());   
        this.frmCashierDashboard.actionListener(new ButtonListener());
        this.frmCashierDashboard.getButtonChoose().setEnabled(false);
        
        frmChooseSeat = new ChooseSeat();
        this.frmChooseSeat.actionListener(new ButtonListener());
        
        frmConfirmTransaction = new ConfirmTransaction();
        this.frmConfirmTransaction.actionListener(new ButtonListener());
        
        frmSuccessTransaction = new SuccessTransaction();
        this.frmSuccessTransaction.actionListener(new ButtonListener());
        
        daokasir = new DAOKasir();
        daoadmin = new DAOAdmin();        
        this.frmCashierDashboard.setVisible(true);
        this.frmCashierDashboard.setTitle(staff.getName());
        loadListMovieShowing();        
    }
    
    public final void loadListMovieShowing(){
        Date utilDate = new Date();
        java.sql.Date dateNow = new java.sql.Date(utilDate.getTime());
        transaction.setDate_buy(dateNow);
        listFilm = daokasir.getNowShowing(dateNow);
        DefaultListModel dataList = new DefaultListModel();
        listFilm.forEach((p) -> {            
            dataList.addElement(p.getTitle());
        });     
        
        this.frmCashierDashboard.getListFilm().setModel(dataList);   
    }
        
    public final void loadListSchedule(int idFilm){                
        listSchedule = daoadmin.getAllSchedule(idFilm);
        DefaultListModel dataList = new DefaultListModel();
        listSchedule.forEach((p) -> {            
            dataList.addElement(p.getTime());
        });     
        
        this.frmCashierDashboard.getListSchedule().setModel(dataList);   
    }

    public final void loadListSeat(){   
        List<String> listSeat = new ArrayList<>();        
        listSeatNoAv = new ArrayList<>();        
        listSeat = daokasir.getSeatAvailable(scheduleSel.getId_schedule(), transaction.getDate_buy());
        
        DefaultListModel dataList = new DefaultListModel();
        listSeat.forEach((p) -> {            
            String[] arr = p.split("\\,", -1);
            for (String arr1 : arr) {                
                listSeatNoAv.add(arr1);
            }
        });
        
        listSeatNoAv.forEach((p) -> {
            dataList.addElement(p);
        });
        this.frmChooseSeat.getListNotAvSeat().setModel(dataList);                
    }   
    
    public final void loadConfirmTransaction(){        
        frmConfirmTransaction.setConfirm(
                filmSel.getTitle(), 
                transaction.getDate_buy(), 
                scheduleSel.getTime(), 
                scheduleSel.getTheater(), 
                transaction.getTotal_price()
        );
    }
    
    public final void loadSuccessTransaction(){        
        frmSuccessTransaction.setTiket(
                1, 
                filmSel.getTitle(), 
                transaction.getDate_buy(), 
                scheduleSel.getTime(), 
                scheduleSel.getTheater(), 
                transaction.getSeat(), 
                scheduleSel.getPrice(), 
                transaction.getTotal_price()
        );
    }
    public final void insertDataTransaction(){    
        if(frmChooseSeat.getSeat().equals("")) {
            frmChooseSeat.displayMsg("Seat belum dipilih");
        } else {            
            String[] arr = frmChooseSeat.getSeat().split("\\,", -1);
            Boolean isThere = false;
            for (String arr1 : arr) {
                if(listSeatNoAv.contains(arr1)) {
                    isThere = true;
                    break;
                }
            }
            if (isThere) {
                frmChooseSeat.displayMsg("Seat tidak tersedia, Silakan pilih seat lainnya!");
            } else {
                transaction.setSeat(frmChooseSeat.getSeat());                
                frmChooseSeat.setClear();
                int totalPrice = scheduleSel.getPrice() * arr.length;
                transaction.setTotal_price(totalPrice);
                loadConfirmTransaction();
                frmConfirmTransaction.setVisible(true);                
                frmChooseSeat.setVisible(false);
            }            
        }        
    }
    
    public final void insertTransaction(){        
        daokasir.insertTransaction(transaction);   
        frmCashierDashboard.getListSchedule().clearSelection();
        frmCashierDashboard.getListFilm().clearSelection();
    }
    
    public final void goToHomePage(){        
        frmCashierDashboard.setVisible(true);
        DefaultListModel dataList = new DefaultListModel();
        frmCashierDashboard.getListFilm().clearSelection();
        frmCashierDashboard.getListSchedule().setModel(dataList);
    }
    
    public final void logout(){        
        new ControllerLogin();
        frmCashierDashboard.dispose();
    }
    
    public class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source.equals(frmCashierDashboard.getButtonChoose())) {
                int selected = frmCashierDashboard.getListSchedule().getSelectedIndex();
                scheduleSel = listSchedule.get(selected);
                transaction.setSchedule_id(scheduleSel.getId_schedule());
                loadListSeat();
                frmChooseSeat.setTitle(staff.getName());
                frmCashierDashboard.setVisible(false);
                frmChooseSeat.setVisible(true);
            } else if (source.equals(frmChooseSeat.getButtonChoose())) {
                insertDataTransaction();
            } else if (source.equals(frmConfirmTransaction.getButtonBuy())) {
                loadSuccessTransaction();
                insertTransaction();
                frmSuccessTransaction.setVisible(true);                
                frmConfirmTransaction.setVisible(false);
            } else if (source.equals(frmSuccessTransaction.getButtonCetak())) {
                frmSuccessTransaction.displayPopup("Success Mencetak");
            } else if (source.equals(frmSuccessTransaction.getButtonHome())) {
                frmSuccessTransaction.setVisible(false);                
                goToHomePage();
            } else if (source.equals(frmConfirmTransaction.getButtonCancel()) || source.equals(frmChooseSeat.getButtonCancel())) {
                frmConfirmTransaction.setVisible(false);    
                frmChooseSeat.setVisible(false);    
                frmCashierDashboard.getButtonChoose().setEnabled(false);
                goToHomePage();
            } else if (source.equals(frmCashierDashboard.getButtonLogOut())) {                
                logout();
            }
        }
    }
    
    public class MousePressed extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent me) {
            // compiled code
            Object source = me.getSource();
            if (source.equals(frmCashierDashboard.getListFilm())) {
                int i = frmCashierDashboard.getListFilm().getSelectedIndex();
                filmSel = listFilm.get(i);  
                
                loadListSchedule(filmSel.getId_film());
            } else if (source.equals(frmCashierDashboard.getListSchedule())) {                
                frmCashierDashboard.getButtonChoose().setEnabled(true);
            }
        } 
    }
    
}
