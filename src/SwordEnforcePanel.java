import java.math.*;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SwordEnforcePanel extends JPanel {
	final String IMAGEPATH = "./image/enforce/";
	final String BGIMAGEPATH = "./image/enforce/EnforceBG.png";
	final int DEFAULT_STR_STAT = 0;
	final int DEFAULT_CRIT_STAT = 0;

	int str;
	int crit;

	Space[][] board;
	Dice[] dices;
	Dice selectedDice = null;

	Image backgroundImage;
	Icon[] spaceImages = new ImageIcon[2];
	Icon[] diceImages = new ImageIcon[6];

	class Space extends JButton implements ActionListener {
		int number = 0;

		public Space(int value, int x, int y, int w, int h) {
			super();
			set(value);
			setBounds(x, y, w, h);

			// https://stackoverflow.com/questions/2713480/is-it-possible-to-put-text-on-top-of-a-image-in-a-button
			setHorizontalTextPosition(SwingConstants.CENTER);
		}

		int get() {
			return number;
		}

		void set(int arg) {
			number = arg;
			setText(String.valueOf(number));
			setFont(new Font("Arial", Font.BOLD, 30));
		}

		void resetShape() {
			setIcon(spaceImages[0]);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.print("Space Selected ");
			if (selectedDice != null) {
				System.out.print("With Dice");
				setSpaceNumber(this);
				setIcon(spaceImages[1]);
			}
			System.out.println();
		}
	}

	class Dice extends JButton implements ActionListener {
		int number = 0;

		public Dice(int value, int x, int y, int w, int h) {
			super();
			set(value);
			setBounds(x, y, w, h);
			setText(null);
			setBorder(new LineBorder(Color.ORANGE, 10));
		}

		int get() {
			return number;
		}

		void set(int arg) {
			number = arg;
		}

		void resetShape() {
			setIcon(diceImages[get() - 1]);
			// https://stackoverflow.com/questions/33954698/jbutton-change-default-border
			setBorder(new LineBorder(Color.ORANGE, 10));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedDice = this;
			setBorder(new LineBorder(Color.RED, 10));
			System.out.println("Dice Selected");
		}
	}

	SwordEnforcePanel(Image img, ActionListener switchPanelListener) {
		setLayout(null);

		spaceImages[0] = new ImageIcon(IMAGEPATH + "space_unselected.png");
		spaceImages[1] = new ImageIcon(IMAGEPATH + "space_selected.png");
		for (int i = 0; i < 6; i++) {
			diceImages[i] = new ImageIcon(IMAGEPATH + "Dice" + String.valueOf(i + 1) + ".jpg");
		}
		backgroundImage = new ImageIcon(BGIMAGEPATH).getImage();

		str = DEFAULT_STR_STAT;
		crit = DEFAULT_CRIT_STAT;
		board = new Space[3][3];
		dices = new Dice[3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Space s = new Space(0, 250 + i * 150, 150 + j * 150, 100, 100);
				s.addActionListener(s);
				s.setIcon(spaceImages[0]);
				add(s);
				board[i][j] = s;
			}
			dices[i] = new Dice(0, 750 + i * 150, 250, 100, 100);
			dices[i].addActionListener(dices[i]);
			dices[i].setIcon(diceImages[0]);
			add(dices[i]);
		}

		JButton resetBtn = new JButton("Reset");
		resetBtn.setBounds(800, 400, 120, 40);
		resetBtn.addActionListener(e -> {
			reset();
		});
		add(resetBtn);

		JButton continueBtn = new JButton("Continue");
		continueBtn.setBounds(950, 400, 120, 40);
		continueBtn.addActionListener(e -> {
			saveStat();
			Sword_Main.battlePanel.launch(str, crit);
			switchPanelListener.actionPerformed(e);
		});
		add(continueBtn);

		JLabel guide1 = new JLabel("+5");
		guide1.setIcon(spaceImages[0]);
		guide1.setBounds(100, 150, 100, 100);
		guide1.setHorizontalTextPosition(SwingConstants.CENTER);
		guide1.setFont(new Font("Arial", Font.BOLD, 25));
		add(guide1);

		JLabel guide2 = new JLabel("*2");
		guide2.setIcon(spaceImages[0]);
		guide2.setBounds(100, 300, 100, 100);
		guide2.setHorizontalTextPosition(SwingConstants.CENTER);
		guide2.setFont(new Font("Arial", Font.BOLD, 25));
		add(guide2);
		
		JLabel guide3 = new JLabel("CRIT*3");
		guide3.setIcon(spaceImages[0]);
		guide3.setBounds(100, 450, 100, 100);
		guide3.setHorizontalTextPosition(SwingConstants.CENTER);
		guide3.setFont(new Font("Arial", Font.BOLD, 25));
		add(guide3);

		launch();
	}

	// https://stackoverflow.com/questions/22162398/how-to-set-a-background-picture-in-jpanel
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);
	}

	public void launch() {
		selectedDice = null;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j].resetShape();
			}
			dices[i].setEnabled(true);
			dices[i].set((int) (Math.random() * 6) + 1);
			dices[i].resetShape();
		}
	}

	void reset() {
		/*
		 * for (int i = 0; i < 3; i++) { dices[i].setEnabled(true);
		 * dices[i].set(b_diceValue[i]); dices[i].resetShape(); for (int j = 0; j < 3;
		 * j++) { board[i][j].set(b_boardValue[i][j]); board[i][j].resetShape(); } }
		 */
	}

	// Called at space actionPerformed
	void setSpaceNumber(Space targetSpace) {
		targetSpace.set(selectedDice.get());
		selectedDice.resetShape();
		selectedDice.setEnabled(false);
		selectedDice = null;
	}

	void saveStat() {
		str = DEFAULT_STR_STAT;
		crit = DEFAULT_CRIT_STAT;

		for (int i = 0; i < 3; i++) {
			if (board[0][i].get() == 0)
				continue;
			str += board[0][i].get() + 5;
		}
		for (int i = 0; i < 3; i++) {
			str += board[1][i].get() * 2;
		}
		for (int i = 0; i < 3; i++) {
			crit += board[2][i].get() * 3;
		}
	}
	
	public int getStr() {
		return str;
	}
	public int getCrit() {
		return crit;
	}

}