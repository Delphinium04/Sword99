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

class SwordMainPanel extends JPanel {
    private Image img;
    private JButton startButton;
    private JButton tutorialButton;
    private JButton qrButton;

    public SwordMainPanel(Image img, ActionListener switchToEnforcePanel, ActionListener switchToTutorialPanel, ActionListener switchToQrPanel) {
        this.img = img;

      
        ImageIcon startButtonIcon = new ImageIcon("./image/startbutton.jpg");
        startButton = new JButton(startButtonIcon);
        startButton.setBorderPainted(false);
        startButton.setPreferredSize(new Dimension(startButtonIcon.getIconWidth(), startButtonIcon.getIconHeight()));
        startButton.addActionListener(switchToEnforcePanel);

       
        ImageIcon tutorialButtonIcon = new ImageIcon("./image/tutorialbutton.png");
        tutorialButton = new JButton(tutorialButtonIcon);
        tutorialButton.setBorderPainted(false);
        tutorialButton.setPreferredSize(new Dimension(tutorialButtonIcon.getIconWidth(), tutorialButtonIcon.getIconHeight()));
        tutorialButton.addActionListener(switchToTutorialPanel);
        
        ImageIcon qrButtonIcon = new ImageIcon("./image/qrbutton.png");
        qrButton = new JButton(qrButtonIcon);
        qrButton.setBorderPainted(false);
        qrButton.setPreferredSize(new Dimension(qrButtonIcon.getIconWidth(), qrButtonIcon.getIconHeight()));
        qrButton.addActionListener(switchToQrPanel);

     
        setLayout(null);

        
        startButton.setBounds(440, 350, startButtonIcon.getIconWidth(), startButtonIcon.getIconHeight());
        tutorialButton.setBounds(460, 550, tutorialButtonIcon.getIconWidth(), tutorialButtonIcon.getIconHeight());
        qrButton.setBounds(660, 550, tutorialButtonIcon.getIconWidth(), tutorialButtonIcon.getIconHeight());

        
        add(startButton);
        add(tutorialButton);
        add(qrButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);     
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}