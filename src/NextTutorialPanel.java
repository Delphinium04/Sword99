import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
class NextTutorialPanel extends JPanel {
    private Image img;
    private JButton backButton; 

    public NextTutorialPanel(Image img, ActionListener switchPanelListener) {
        this.img = img;

        
        ImageIcon backButtonIcon = new ImageIcon("./image/backbutton.png");
        backButton = new JButton(backButtonIcon);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(backButtonIcon.getIconWidth(), backButtonIcon.getIconHeight()));
        backButton.addActionListener(e -> switchPanelListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "MainPanel"))); 


        setLayout(null);


        backButton.setBounds(560, 600, backButtonIcon.getIconWidth(), backButtonIcon.getIconHeight());


        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}