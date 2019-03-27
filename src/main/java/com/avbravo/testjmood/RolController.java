/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.testjmood;

import com.avbravo.jmoordb.configuration.JmoordbConfiguration;
import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.configuration.JmoordbControllerEnvironment;
import com.avbravo.jmoordb.interfaces.IController;
import com.avbravo.jmoordb.services.RevisionHistoryServices;
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.jmoordbutils.printer.Printer;
import com.avbravo.storeejb.entity.Rol;
import com.avbravo.storeejb.entity.Usuario;
import com.avbravo.storeejb.producer.AutoincrementableStoreejbRepository;
import com.avbravo.storeejb.producer.RevisionHistoryStoreejbRepository;
import com.avbravo.storeejb.repository.RolRepository;
import com.avbravo.storeejb.repository.UsuarioRepository;
import com.avbravo.storeejb.services.RolServices;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

/**
 *
 * @author avbravo
 */
@Named(value = "rolController")
@ViewScoped
public class RolController implements Serializable, IController {

    private static final long serialVersionUID = 1L;
    private Boolean writable = false;
    Integer page = 1;
    Integer rowPage = 25;

    List<Integer> pages = new ArrayList<>();

    //Entity
    Rol rol = new Rol();
    Rol rolSelected;
    Rol rolSearch = new Rol();
    //List
    List<Rol> rolList = new ArrayList<>();

    //Repository
    @Inject
    AutoincrementableStoreejbRepository autoincrementableRepository;
    @Inject
    RolRepository rolRepository;

    @Inject
    RolServices rolServices;

    @Inject
    Printer printer;

    public List<Integer> getPages() {

        return rolRepository.listOfPage(rowPage);
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public Boolean getWritable() {
        return writable;
    }

    public void setWritable(Boolean writable) {
        this.writable = writable;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRowPage() {
        return rowPage;
    }

    public void setRowPage(Integer rowPage) {
        this.rowPage = rowPage;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Rol getRolSelected() {
        return rolSelected;
    }

    public void setRolSelected(Rol rolSelected) {
        this.rolSelected = rolSelected;
    }

    public Rol getRolSearch() {
        return rolSearch;
    }

    public void setRolSearch(Rol rolSearch) {
        this.rolSearch = rolSearch;
    }

    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    public RolRepository getRolRepository() {
        return rolRepository;
    }

    public void setRolRepository(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public RolServices getRolServices() {
        return rolServices;
    }

    public void setRolServices(RolServices rolServices) {
        this.rolServices = rolServices;
    }

    /**
     * Creates a new instance of RolController
     */
    public RolController() {
    }

    @PostConstruct
    public void init() {
        try {

           
            /*
            configurar el ambiente del controller
             */
            HashMap parameters = new HashMap();
            Usuario _userLogged = (Usuario) JmoordbContext.get("_userLogged");
            parameters.put("P_EMPRESA", _userLogged.getEmpresa().getDescripcion());

            JmoordbControllerEnvironment jce = new JmoordbControllerEnvironment.Builder()
                    .withController(this)
                    .withRepository(rolRepository)
                    .withEntity(rol)
                    .withService(rolServices)
                    .withNameFieldOfPage("page")
                    .withNameFieldOfRowPage("rowPage")
                    .withSearchbysecondarykey(false)
                    .withPathReportDetail("/resources/reportes/rol/details.jasper")
                    .withPathReportAll("/resources/reportes/rol/all.jasper")
                    .withparameters(parameters)
                    .build();

            start();

        } catch (Exception e) {
            JsfUtil.errorDialog(nameOfMethod(), e.getLocalizedMessage());
        }
    }// </editor-fold>

    @Override
    public String preRenderView(String action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void move(Integer page) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset() {
        RequestContext.getCurrentInstance().reset(":form:content");
        prepareNew();
    }

}
