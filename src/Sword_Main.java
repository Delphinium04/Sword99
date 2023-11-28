import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
//1280_720

public class Sword_Main {
	public static SwordMainPanel mainPanel;
	public static SwordBattlePanel battlePanel;
	public static SwordEnforcePanel enforcePanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sword99");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            ImageIcon mainUIIcon = new ImageIcon("./image/mainUI.jpg");
            ImageIcon gamePanelIcon = new ImageIcon("./image/forest.jpg");
            ImageIcon enhancementPanelIcon = new ImageIcon("./image/forest.jpg");
            ImageIcon tutorialPanelIcon = new ImageIcon("./image/tutorial.png");
            ImageIcon nextTutorialPanelIcon = new ImageIcon("./image/nexttutorial.png");
            ImageIcon qrPanelIcon = new ImageIcon("./image/whiteback.png");

            CardLayout cardLayout = new CardLayout();
            JPanel cardPanel = new JPanel(cardLayout);

            mainPanel = new SwordMainPanel(mainUIIcon.getImage(), e -> cardLayout.show(cardPanel, "EnforcePanel"), e -> cardLayout.show(cardPanel, "TutorialPanel"),e -> cardLayout.show(cardPanel, "QrPanel") );
            cardPanel.add(mainPanel, "MainPanel");

            battlePanel = new SwordBattlePanel(gamePanelIcon.getImage(), e -> cardLayout.show(cardPanel, "EnforcePanel"));
            cardPanel.add(battlePanel, "BattlePanel");

            enforcePanel = new SwordEnforcePanel(enhancementPanelIcon.getImage(), e -> cardLayout.show(cardPanel, "BattlePanel"));
            cardPanel.add(enforcePanel, "EnforcePanel");

            SwordTutorialPanel tutorialPanel = new SwordTutorialPanel(tutorialPanelIcon.getImage(), e -> cardLayout.show(cardPanel, "NextTutorialPanel")); 
            cardPanel.add(tutorialPanel, "TutorialPanel");

            NextTutorialPanel nextTutorialPanel = new NextTutorialPanel(nextTutorialPanelIcon.getImage(), e -> cardLayout.show(cardPanel, "MainPanel")); 
            cardPanel.add(nextTutorialPanel, "NextTutorialPanel"); 
            
            QRPanel qrPanel = new QRPanel(qrPanelIcon.getImage(), e -> cardLayout.show(cardPanel, "MainPanel")); 
            cardPanel.add(qrPanel, "QrPanel"); 

            frame.setSize(mainUIIcon.getIconWidth(), mainUIIcon.getIconHeight());
            cardLayout.show(cardPanel, "MainPanel");

            frame.add(cardPanel);
            frame.setPreferredSize(new Dimension(1280, 720));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
