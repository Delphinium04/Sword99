import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SwordBattlePanel extends JPanel {
	private ActionListener switchPanelListener;

	private int playerAtk; // 받아오는 공격력
	private int playerCrit; // 받아온 크리티컬

	private int playerHealth; // 플레이어 체력
	private int monsterHealth; // 몬스터 체력
	private int bossHealth; // 보스체력
	private int round; // 라운드
	private Image battleBackground; // 배경화면 이미지
	private Image resultImage; // 결과 이미지를 그릴 때 사용할 이미지
	private String resultImageName; // 결과 이미지 파일의 이름을 저장하는 필드 추가

	private ImageIcon playerImage; // 플레이어 이미지
	private ImageIcon monsterImage; // 적 이미지
	private ImageIcon heartImage; // 하트 이미지

	private ImageIcon winImage;
	private ImageIcon loseImage;
	private ImageIcon critImage;
	private ImageIcon attackImage;
	private ImageIcon missImage;

	private JLabel resultLabel;
	private JLabel player;
	private JLabel monster;
	private JLabel heart;

	// Called by SwordEnforcePanel
	public void launch(int atk, int crit) {
		playerAtk = atk;
		playerCrit = crit;

	}

	public SwordBattlePanel(Image img, ActionListener switchPanelListener) {
		setLayout(null);
		battleBackground = new ImageIcon("./Image/battle/battleBackground.png").getImage();

		this.switchPanelListener = switchPanelListener;

		// 플레이어와 몬스터의 속성 초기화
		playerHealth = 3;
		monsterHealth = 3;
		bossHealth = 5;
		round = 1;

		playerAtk = 0;
		playerCrit = 0;

		// 플레이어, 몬스터, 하트 이미지 초기화
		// https://stackoverflow.com/a/32885963
		playerImage = imageIconRescaler(new ImageIcon("./image/battle/player.png"), 4);
		heartImage = imageIconRescaler(new ImageIcon("./image/battle/heart.png"), 4);
		monsterImage = imageIconRescaler(new ImageIcon("./image/battle/monster.png"), 4);

		winImage = new ImageIcon("./image/battle/winIcon.jpg");
		loseImage = new ImageIcon("./image/battle/loseIcon.jpg");
		attackImage = new ImageIcon("./image/battle/attack.jpg");
		critImage = new ImageIcon("./image/battle/crit.jpg");
		missImage = new ImageIcon("./image/battle/miss.jpg");

		player = new JLabel();
		player.setIcon(playerImage);
		player.setBounds(300, 200, playerImage.getIconWidth(), playerImage.getIconHeight());
		add(player);
		monster = new JLabel();
		monster.setIcon(monsterImage);
		monster.setBounds(800, 200, monsterImage.getIconWidth(), monsterImage.getIconHeight());
		add(monster);

		heart = new JLabel();
		heart.setIcon(heartImage);
		heart.setBounds(500, 200, heartImage.getIconWidth(), heartImage.getIconHeight());
		add(heart);

		resultLabel = new JLabel();
		resultLabel.setIcon(null);
		resultLabel.setBounds(600, 300, 200, 200);
		add(resultLabel);

		// Start 버튼 초기화
		ImageIcon battleButtonIcon = new ImageIcon("./image/battle/battlebutton.jpg");
		JButton battleButton = new JButton(battleButtonIcon);
		battleButton.setBorderPainted(false);
		battleButton.setBounds(500, 500, battleButtonIcon.getIconWidth(), battleButtonIcon.getIconHeight());

		// ActionListener를 사용하여 startRound() 메서드를 호출
		battleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startRound();
			}
		});

		add(battleButton); // 패널에 버튼 추가
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(battleBackground, 0, 0, getWidth(), getHeight(), this);
	}

	private ImageIcon imageIconRescaler(ImageIcon img, int multiplier) {
		return new ImageIcon(img.getImage().getScaledInstance(img.getIconWidth() / multiplier,
				img.getIconHeight() / multiplier, Image.SCALE_DEFAULT));
	}

	private void hideHearts(int prevPlayerHealth, int prevMonsterHealth) {
		// 플레이어의 생명력이 줄어들면 일반 하트 이미지를 안보이도록 설정

		// 몬스터의 생명력이 줄어들면 일반 하트 이미지를 안보이도록 설정

	}

	// 라운드 별 몬스터의 공격력 범위 설정 ex) 10~29

	private void startRound() {
		int prevPlayerHealth = playerHealth;
		int prevMonsterHealth = monsterHealth;
		// 라운드에 따라 몬스터 공격력 설정
		int minMonsterAttack = 10 + (round - 1); // 게임 난이도에 맞게 조절
		int maxMonsterAttack = 29 + (round - 1); // 게임 난이도에 맞게 조절
		int monsterAttack = getRandomNumber(minMonsterAttack, maxMonsterAttack);

		// 플레이어의 크리티컬 히트 확인
		// Math.random에서 0이상 1미만 받고 < 으로 크리티컬 비교, 크리티컬 확률이 100%(1)이면 무조건 발동하는 원리
		boolean playerCriticalHit = Math.random() < getPlayerCriticalProbability(); // getPlayerCriticalProbability()
																					// 메서드 구현 필요

		// 몬스터와 플레이어의 공격력 비교
		if (getPlayerAttack() > monsterAttack) {
			// 플레이어의 공격력이 높은 경우
			if (playerCriticalHit) { // 크리티컬 true일 경우
				monsterHealth -= 2; // 크리티컬 히트 시 데미지 두 배
				showResultImage(critImage);
				showResultImage(attackImage);
			} else {
				monsterHealth -= 1;
				showResultImage(attackImage);
			}
		} else if (getPlayerAttack() <= monsterAttack) {
			// 몬스터의 공격력이 높거나 같은 경우
			playerHealth -= 1;
			showResultImage(missImage);
		}
		hideHearts(prevPlayerHealth, prevMonsterHealth);

		// 게임 상태 확인
		if (playerHealth <= 0 || monsterHealth <= 0) {
			endGame(); // 게임 종료 호출
		}

		// 게임 상태에 따라 UI 업데이트 또는 다른 동작 수행
	}

	private void showResultImage(ImageIcon icon) {
		// 이미지 표시
		resultLabel.setIcon(icon);

		// 일정 시간 후 이미지 초기화
		// https://blog.naver.com/PostView.nhn?blogId=highkrs&logNo=220283709171
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				resultLabel.setIcon(null);
			}
		};
		timer.schedule(timerTask, 2000);
	}

	private void endGame() {
		JDialog dialog = new JDialog();
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				switchPanelListener.actionPerformed(null);
				Sword_Main.enforcePanel.launch();

			}
		});
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		if (playerHealth <= 0) {
			// 플레이어 패배, 패배 이미지 표시
			ImageIcon loseIcon = new ImageIcon("./image/battle/loseIcon.jpg");
			JLabel loseLabel = new JLabel(loseIcon);
			dialog.getContentPane().add(loseLabel);
		} else {
			if (round < 5) {
				ImageIcon winIcon = new ImageIcon("./image/battle/winIcon.jpg");
				JLabel winLabel = new JLabel(winIcon);
				dialog.getContentPane().add(winLabel);
				// 몬스터 격파, 마지막 라운드가 아닌 경우
				round++;
				startRound();
			} else {
				// 마지막 라운드 클리어, 플레이어 승리
				ImageIcon winIcon = new ImageIcon("./image/battle/winIcon.jpg");
				JLabel winLabel = new JLabel(winIcon);
				dialog.getContentPane().add(winLabel);
				// 게임 승리
			}
		}
		// 다이얼로그 크기 설정, 원하는 이미지 크기에 맞게 조절
		dialog.setSize(new Dimension(1280, 720));
		// 다이얼로그를 화면 가운데에 표시
		dialog.setLocationRelativeTo(null);
		// 다이얼로그를 모달로 설정하여 현재 다이얼로그가 닫히기 전까지 다른 창과 상호작용 불가능하도록 함
		dialog.setModal(true);
		// 다이얼로그를 보이게 함
		dialog.setVisible(true);

		// 게임 종료에 대한 동작 수행, 예를 들어 강화 패널로 돌아가기 등
	}

	private int getRandomNumber(int min, int max) {
		// 위에서 몬스터 공격력 최소 최대값 받아서 계산
		Random random = new Random();
		// 최대 공격력에서 최소공격력 사이 랜덤값 반환
		return random.nextInt(max - min + 1) + min;
	}

	private int getPlayerAttack() {
		// 검 강화에 기반한 플레이어 공격력 로직 구현
		return 0; // 상속받기
	}

	private double getPlayerCriticalProbability() {
		// 검 강화에 기반한 플레이어 크리티컬 확률 로직 구현
		return 0.1; // 상속받기
	}
}

// 승리시 승리문구 출력 후 라운드 상승 후 강화패널로 복귀
// 패배시 패배문구 출력 후 강화패널로 복귀
// 보스전 클리어시 게임승리 문구 출력 후 종료
