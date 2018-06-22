package python;

import java.util.logging.Level;
import java.util.logging.Logger;

import informationcontainer.AIContainer;
import loader.ResourceLoader;
import manager.InputManager;
import setting.FlagSetting;
import setting.LaunchSetting;

/**
 * Python側で設定した使用キャラクターやAI名といった, ゲームの起動情報を扱うクラス.
 */
public class PyGame {

	/**
	 * The character's data of both characters.<br>
	 * Index 0 is P1, index 1 is P2.
	 */
	private String[] characterNames;

	/**
	 * The both AIs' names.<br>
	 * Index 0 is P1, index 1 is P2.
	 */
	private String[] aiNames;

	/**
	 * The number of repeat count of this game.
	 */
	private int num;

	/**
	 * ゲームが終了したことをPython側に知らせるためのオブジェクト.
	 */
	public Object end;

	/**
	 * 引数で指定されたデータでPyGameの初期化を行うクラスコンストラクタ．
	 *
	 * @param manager
	 *            ゲームの作成やリプレイのロードといった処理を管理するマネージャー
	 * @param c1
	 *            P1's character name
	 * @param c2
	 *            P2's character name
	 * @param name1
	 *            P1's AI name
	 * @param name2
	 *            P2's AI name
	 * @param num
	 *            the number of repeat count of this game
	 */
	public PyGame(PyManager manager, String c1, String c2, String name1, String name2, int num) {
		this.characterNames = new String[2];
		this.aiNames = new String[2];
		this.characterNames[0] = c1;
		this.characterNames[1] = c2;
		this.aiNames[0] = name1;
		this.aiNames[1] = name2;
		this.num = num;

		this.end = new Object();

		// 起動情報を本体にセットする
		LaunchSetting.deviceTypes[0] = InputManager.DEVICE_TYPE_AI;
		LaunchSetting.deviceTypes[1] = InputManager.DEVICE_TYPE_AI;
		LaunchSetting.characterNames[0] = c1;
		LaunchSetting.characterNames[1] = c2;
		LaunchSetting.aiNames[0] = name1;
		LaunchSetting.aiNames[1] = name2;
		LaunchSetting.repeatNumber = num;

		if (LaunchSetting.repeatNumber > 1) {
			FlagSetting.automationFlag = true;
		}
	}

	/**
	 * 引数で指定したプレイヤーのキャラクターのデータを返す．
	 *
	 * @param playerNumber
	 *            プレイヤー番号(true: P1; false: P2)
	 * @return 指定したプレイヤーのキャラクターのデータ
	 */
	public String getCharacterName(boolean playerNumber) {
		return playerNumber ? this.characterNames[0] : this.characterNames[1];
	}

	/**
	 * 引数で指定したプレイヤーのAI名を返す．
	 *
	 * @param playerNumber
	 *            プレイヤー番号(true: P1; false: P2)
	 * @return 指定したプレイヤーのAI名
	 */
	public String getAIName(boolean playerNumber) {
		return playerNumber ? this.aiNames[0] : this.aiNames[1];
	}

	/**
	 * ゲームの繰り返し回数を取得する．
	 *
	 * @return ゲームの繰り返し回数
	 */
	public int getRepeatCount() {
		return this.num;
	}

	/**
	 * The PM AI name.<br>
	 */
	private String aiName;

	/**
	 * 引数で指定されたデータでPyGameの初期化を行うクラスコンストラクタ．
	 *
	 * @param manager
	 *            ゲームの作成やリプレイのロードといった処理を管理するマネージャー
	 * @param c1
	 *            P1's character name
	 * @param c2
	 *            P2's character name
	 * @param name1
	 *            PM AI name
	 * @param num
	 *            the number of repeat count of this game
	 */
	public PyGame(PyManager manager, String c1, String c2, String name1, int num) {
		this.characterNames = new String[2];
		this.characterNames[0] = c1;
		this.characterNames[1] = c2;
		this.aiName = name1;
		this.num = num;

		this.end = new Object();

		// 起動情報を本体にセットする
		LaunchSetting.deviceTypes[0] = InputManager.DEVICE_TYPE_PMAI;
		LaunchSetting.deviceTypes[1] = InputManager.DEVICE_TYPE_PMAI;
		LaunchSetting.characterNames[0] = c1;
		LaunchSetting.characterNames[1] = c2;
		LaunchSetting.pmaiName = name1;
		LaunchSetting.repeatNumber = num;

		if (LaunchSetting.repeatNumber > 1) {
			FlagSetting.automationFlag = true;
		}
		FlagSetting.pmMode = true;
	}
	
	/**
	 * 引数で指定されたデータでPyGameの初期化を行うクラスコンストラクタ．
	 *
	 * @param manager
	 *            ゲームの作成やリプレイのロードといった処理を管理するマネージャー
	 * @param c1
	 *            P1's character name
	 * @param c2
	 *            P2's character name
	 * @param name1
	 *            PM AI name
	 * @param num
	 *            the number of repeat count of this game
	 */
	public PyGame(PyManager manager, String c1, String c2, int num) {
		this.characterNames = new String[2];
		this.characterNames[0] = c1;
		this.characterNames[1] = c2;
		this.num = num;

		this.end = new Object();

		// 起動情報を本体にセットする
		LaunchSetting.deviceTypes[0] = InputManager.DEVICE_TYPE_AI;
		LaunchSetting.deviceTypes[1] = InputManager.DEVICE_TYPE_AI;
		LaunchSetting.characterNames[0] = c1;
		LaunchSetting.characterNames[1] = c2;
		LaunchSetting.repeatNumber = num;

		if (LaunchSetting.repeatNumber > 1) {
			FlagSetting.automationFlag = true;
		}
		FlagSetting.allCombinationFlag = true;
		
		AIContainer.allAINameList = ResourceLoader.getInstance().loadFileNames("./data/ai", ".jar");
		AIContainer.allAINameList.addAll(InputManager.getInstance().getRegisteredAI());
		if (AIContainer.allAINameList.size() < 2) {
			Logger.getAnonymousLogger().log(Level.INFO, "Cannot launch FightingICE with Round-robin mode.");
		}else{
			LaunchSetting.aiNames[0] = AIContainer.allAINameList.get(AIContainer.p1Index);
			LaunchSetting.aiNames[1] = AIContainer.allAINameList.get(AIContainer.p2Index);
		}
	}
	
}
