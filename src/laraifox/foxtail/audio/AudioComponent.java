package laraifox.foxtail.audio;

import laraifox.foxtail.core.GameComponent;
import laraifox.foxtail.core.GameObject;

public class AudioComponent extends GameComponent {

	@Override
	public void onComponentAdded(GameObject owner) {
		super.onComponentAdded(owner);
		//		AudioEngine.addAudioComponent(this);
	}

}
