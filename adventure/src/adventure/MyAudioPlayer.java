package adventure;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MyAudioPlayer {
	Map<String, AudioInputStream> streamMap = new HashMap<String, AudioInputStream>();

	public MyAudioPlayer() {
		try {
			streamMap.put("opening",
					AudioSystem.getAudioInputStream(getClass().getResource("/res/sound/background-low.wav")));
			streamMap.put("bullet", AudioSystem.getAudioInputStream(getClass().getResource("/res/sound/bullet.wav")));
			streamMap.put("hero-die",
					AudioSystem.getAudioInputStream(getClass().getResource("/res/sound/hero-die.wav")));
			streamMap.put("villen-die",
					AudioSystem.getAudioInputStream(getClass().getResource("/res/sound/villen-die.wav")));
			streamMap.put("hero-jump",
					AudioSystem.getAudioInputStream(getClass().getResource("/res/sound/hero-jump.wav")));
			streamMap.put("collect-coin",
					AudioSystem.getAudioInputStream(getClass().getResource("/res/sound/collect-coin.wav")));
			streamMap.put("win-game",
					AudioSystem.getAudioInputStream(getClass().getResource("/res/sound/win-game.wav")));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// add sound to the game
	public void play(String fileName, boolean loop) {
		try {
			AudioInputStream s = streamMap.get(fileName);
			Clip audioClip;
			audioClip = AudioSystem.getClip();
			audioClip.open(s);
			if (loop) {
				audioClip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				audioClip.start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
