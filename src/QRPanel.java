import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class QRPanel extends JPanel {
    private Image img;
    private JButton backButton;
    private JLabel qrcode;

    public QRPanel(Image img, ActionListener switchPanelListener) {
        this.img = img;

        ImageIcon backButtonIcon = new ImageIcon("./image/backbutton.png");
        backButton = new JButton(backButtonIcon);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(backButtonIcon.getIconWidth(), backButtonIcon.getIconHeight()));
        backButton.addActionListener(e -> switchPanelListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "NextTutorialPanel")));

        setLayout(null);

       
        ImageIcon qrcodeIcon = new ImageIcon("./image/qrcode.png");
        qrcode = new JLabel(qrcodeIcon);
        qrcode.setBounds(405, 50, qrcodeIcon.getIconWidth(), qrcodeIcon.getIconHeight());

     
        add(qrcode);

        backButton.setBounds(560, 600, backButtonIcon.getIconWidth(), backButtonIcon.getIconHeight());
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        // 이미지 그리기는 필요 없음
    }
}