/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.testjmood;

import com.avbravo.jmoordb.configuration.JmoordbConfiguration;
import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.services.RevisionHistoryServices;
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.storeejb.entity.Usuario;
import com.avbravo.storeejb.producer.RevisionHistoryStoreejbRepository;
import com.avbravo.storeejb.repository.UsuarioRepository;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Optional;
import javax.inject.Inject;

/**
 *
 * @author avbravo
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {
String username;
String password;
    @Inject
    UsuarioRepository usuarioRepository;
    @Inject
    RevisionHistoryStoreejbRepository revisionHistoryStoreejbRepository;
    @Inject
    RevisionHistoryServices revisionHistoryServices;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
    
    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }

    public String doLogin() {
        try {
           

            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            Optional<Usuario> optional = usuarioRepository.findById(usuario);
            if (!optional.isPresent()) {
                JsfUtil.errorDialog("init", "Usuario no encontrado");
            } else {
                usuario = optional.get();
                 JmoordbConfiguration jmc = new JmoordbConfiguration.Builder()
                    .withSpanish(true)
                    .withRepositoryRevisionHistory(revisionHistoryStoreejbRepository)
                    .withRevisionHistoryServices(revisionHistoryServices)
                    .withRevisionSave(true)
                    .withUsername(username)
                    .build();
                
                
            }
            JmoordbContext.put("_userLogged", usuario);
        } catch (Exception e) {
            JsfUtil.errorDialog("doLogin() ", e.getLocalizedMessage());
        }
        return "";
    }
}
