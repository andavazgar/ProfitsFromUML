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

package com.horstmann.violet.application.admin;

import javax.swing.*;
import java.awt.*;

/**
 * Login dialog
 *
 * @author Andres Vazquez
 */
public class LoginDialog extends JDialog
{
    private String dialogTitle = "Login";
    
    private String username = "Username";
    
    private String password = "Password";
    
    private JButton loginButton = new JButton();
    
    private JPanel loginPanel;
    
    /**
     * Default constructor of LoginDialog
     *
     * @param parent JFrame parent
     */
    public LoginDialog(JFrame parent) {
        super(parent);
    
        this.getContentPane().setSize(800,400);
        this.setTitle(dialogTitle);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(buildLoginPanel(), BorderLayout.CENTER);
        pack();
        setCenterLocation(parent);
    }
    
    private JPanel buildLoginPanel() {
        if (loginPanel == null) {
            loginPanel = new JPanel(new BorderLayout());
            loginPanel.setPreferredSize(new Dimension(600, 400));
        }
        return this.loginPanel;
    }
    
    private void setCenterLocation(JFrame parent) {
        setLocation((parent.getWidth() - getWidth()) / 2, (parent.getHeight() - getHeight()) / 2);
    }
}
