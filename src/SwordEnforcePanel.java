import java.math.*;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class SwordEnforcePanel extends JPanel {
	final String IMAGEPATH = "./src/battle_image/";
		
	int str;
	int crit;

	Space[][] board;
	Dice[] dices;
	JButton rerollButton;

	Icon[] spaceImages = new ImageIcon[2];
	Icon[] diceImages = new ImageIcon[6];

	Dice selectedDice = null;
	JButton diceRollButton;

	class Space extends JButton implements ActionListener {
		int number = 0;

		public Space(int value, int x, int y, int w, int h) {
			super();
			set(value);
			setBounds(x, y, w, h);
		}

		int get() {
			return number;
		}

		void set(int arg) {
			number = arg;
			setText(String.valueOf(number));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.print("Space Selected ");
			if (selectedDice != null) {
				System.out.print("With Dice");
				addNumber(this);
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
		}

		int get() {
			return number;
		}

		void set(int arg) {
			number = arg;
			setText(String.valueOf(number));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedDice = this;
			System.out.println("Dice Selected");
		}
	}

	SwordEnforcePanel() {
		setLayout(null);

		str = 0;
		crit = 0;
		board = new Space[3][3];
		dices = new Dice[3];

		spaceImages[0] = new ImageIcon(IMAGEPATH + "space_unselected.png");
		spaceImages[1] = new ImageIcon(IMAGEPATH + "space_selected.png");
		
		for (int i = 0; i < 6; i++) {
			diceImages[i] = new ImageIcon(IMAGEPATH + "Dice" + String.valueOf(i+1));
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Space s =new Space(0, 150 + i * 150, 100 + j * 150, 100, 100);
				s.addActionListener(s);
				s.setIcon(spaceImages[0]);
				add(s);
				board[i][j] = s;
			}
			dices[i] = new Dice(0, 600 + i * 150, 250, 100, 100);
			dices[i].addActionListener(dices[i]);
			dices[i].setIcon(diceImages[0]);
			add(dices[i]);
		}

		diceRollButton = new JButton("ROLL!");
		diceRollButton.setBounds(700, 400, 200, 60);
		diceRollButton.addActionListener(e -> {
			launch();
		});
		add(diceRollButton);

		launch();
	}

	public void launch() {
		selectedDice = null;
		dices[0].setEnabled(true);
		dices[1].setEnabled(true);
		dices[2].setEnabled(true);

		for (int i = 0; i < 3; i++) {
			dices[i].set((int) (Math.random() * 6) + 1);
			dices[i].setIcon(diceImages[dices[i].get() - 1]);
		}
	}

	void reroll() {
		for (int i = 0; i < 3; i++) {
			dices[i].set((int) (Math.random() * 6) + 1);
		}
	}

	// Called at space actionPerformed
	void addNumber(Space targetSpace) {
		targetSpace.set(selectedDice.get());
		selectedDice.setEnabled(false);
		selectedDice = null;
	}

	void calculateStr() {
		str = 0;
		crit = 0; // default?

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
		System.out.println(str + "," + crit);
	}

}