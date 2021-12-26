/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.List;
import Model.ModelFilm;
import Model.ModelStaff;
import View.AdminDashboard;
import dbhelper.DAOAdmin;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author user
 */
public class ControllerAdmin {
    private List<ModelFilm> list;
    private ModelStaff staff;
    private ModelFilm film;
    private AdminDashboard frmAdminDashboard;
    private DAOAdmin daoadmin;
    
    public ControllerAdmin(ModelStaff staff) {        
        this.staff = staff;
        daoadmin = new DAOAdmin();
        frmAdminDashboard = new AdminDashboard();
        this.frmAdminDashboard.setVisible(true);
        loadList();                
    }
    public final void loadList(){
        DefaultTableModel tbl = (DefaultTableModel) frmAdminDashboard.getTabelFilm().getModel();
        list = daoadmin.getAllFilm();
        list.forEach((data) -> {
            System.out.println(data.getTitle());
            tbl.insertRow(tbl.getRowCount(), new Object[]{data.getId_film(), data.getTitle(), data.getGenre(), data.getDate_start(), data.getDate_end()});
        });            
    }
}
