package cam72cam.immersiverailroading.model.part;

import cam72cam.immersiverailroading.entity.EntityMoveableRollingStock;
import cam72cam.immersiverailroading.library.ValveGearType;
import cam72cam.immersiverailroading.model.ComponentRenderer;
import cam72cam.immersiverailroading.model.components.ComponentProvider;

import java.util.List;

public interface ValveGear {
    void render(double distance, float throttle, ComponentRenderer draw);

    void effects(EntityMoveableRollingStock stock, float throttle);

    static ValveGear get(List<Wheel> wheels, ValveGearType type, ComponentProvider provider, String pos, float angleOffset) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case WALSCHAERTS:
            case TRI_WALSCHAERTS:
            case MALLET_WALSCHAERTS:
            case GARRAT:
                return WalschaertsValveGear.get(wheels, provider, pos, angleOffset);
            case STEPHENSON:
            case T1:
                return StephensonValveGear.get(wheels, provider, pos, angleOffset);
            case SHAY:
            case CLIMAX:
            case HIDDEN:
            default:
                return null;
        }
    }

    boolean isEndStroke(EntityMoveableRollingStock stock, float throttle);
}