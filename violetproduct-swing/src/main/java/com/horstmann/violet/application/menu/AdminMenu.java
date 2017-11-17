/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.application.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.admin.LoginDialog;
import com.horstmann.violet.application.admin.NewUserDialog;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;

/**
 * Admin menu on the editor frame
 *
 * @author Andres Vazquez
 */
@ResourceBundleBean(resourceReference = MenuFactory.class)
public class AdminMenu extends JMenu {
    
    /**
     * Default constructor
     *
     * @param mainFrame
     */
    @ResourceBundleBean(key = "admin")
    public AdminMenu(MainFrame mainFrame) {
        ResourceBundleInjector.getInjector().inject(this);
        this.mainFrame = mainFrame;
        createMenu();
    }
    
    /**
     * Initialize the menu
     */
    private void createMenu() {
        LoginMenu();
        NewUserMenu();
        
        this.add(this.adminLogin);
        this.add(this.adminNewUser);
    }
    
    /**
     * Login menu entry
     */
    public void LoginMenu() {
        this.adminLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                LoginDialog dialog = new LoginDialog(mainFrame);
                dialog.setVisible(true);
            }
        });
    }
    
    /**
     * New User menu entry
     */
    private void NewUserMenu() {
        this.adminNewUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                NewUserDialog dialog = new NewUserDialog(mainFrame);
                dialog.setVisible(true);
            }
        });
    }
    
    /**
     * Application main frame
     */
    private MainFrame mainFrame;
    
    @ResourceBundleBean(key = "admin.login")
    private JMenuItem adminLogin;
    
    @ResourceBundleBean(key = "admin.new_user")
    private JMenuItem adminNewUser;
}
