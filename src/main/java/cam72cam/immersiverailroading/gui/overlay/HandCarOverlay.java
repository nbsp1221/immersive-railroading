package cam72cam.immersiverailroading.gui.overlay;

import cam72cam.immersiverailroading.entity.HandCar;
import cam72cam.immersiverailroading.library.GuiText;
import cam72cam.mod.MinecraftClient;
import cam72cam.mod.entity.Entity;

public class HandCarOverlay extends ControlsOverlay {
	public void draw() {
		Entity riding = MinecraftClient.getPlayer().getRiding();
		if (riding == null) {
			return;
		}
		if (!(riding instanceof HandCar)) {
			return;
		}
		HandCar loco = (HandCar) riding;
		drawBackground(loco);
		addSpace(10);

		if (loco.getDefinition().hasIndependentBrake()) {
			drawScalar(GuiText.LABEL_BRAKE.toString(), loco.getIndependentBrake() * 10, 0, 10);
		}
		drawScalar(GuiText.LABEL_BRAKE.toString(), loco.getTrainBrake()*10, 0, 10);
		drawScalar(GuiText.LABEL_THROTTLE.toString(), loco.getThrottle()*10, -10, 10);

		drawSpeedDisplay(loco, 0);
	}
}
