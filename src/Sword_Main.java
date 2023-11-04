import javax.swing.*;

public class Sword_Main {	
	static JFrame frame;
	static SwordMainPanel mainPanel;
	static SwordEnforcePanel enforcePanel;
	static SwordBattlePanel battlePanel;
	
	
	// Called by SwordMainPanel
	public static void mainEnd() {
		// Set Enforce Panel
	}
	
	// Called by SwordEnforcePanel
	public static void enforceEnd(int atk, int critical) {
		//  Set Battle Panel
	}
	
	// Called by SwordBattlePanel
	public static void battleEnd(boolean isPlayerWin) {
		/*
			if (isPlayerWin) { Set EnforcePanel }
			else { Set Game Over Panel }
		*/
	}
	
	public void gameLose() {}
	public void gameWin() {}
	
	public static void main(String[] args) {
		frame = new JFrame ("Sword99");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new SwordMainPanel();
		enforcePanel = new SwordEnforcePanel();
		battlePanel = new SwordBattlePanel();
		
		/*
			frame.getContentPane().removeAll();
			frame.getContentPane().add(mainPanel);
		 */
		
		frame.pack();
		frame.setVisible(true);
		
		return;
	}
}
