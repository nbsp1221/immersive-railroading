package cam72cam.immersiverailroading.model.part;

import cam72cam.immersiverailroading.entity.EntityMoveableRollingStock;
import cam72cam.immersiverailroading.library.ModelComponentType;
import cam72cam.immersiverailroading.model.ModelState;
import cam72cam.immersiverailroading.model.animation.Animatrix;
import cam72cam.immersiverailroading.model.components.ComponentProvider;
import cam72cam.immersiverailroading.model.components.ModelComponent;
import cam72cam.mod.resource.Identifier;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CustomValveGear implements ValveGear {
    private final WheelSet wheels;
    private final List<ModelComponent> components;

    private final Animatrix animation;

    public static CustomValveGear get(Identifier custom, WheelSet wheels, ComponentProvider provider, ModelState state, ModelComponentType.ModelPosition pos) {
        List<ModelComponent> components = new ArrayList<>();

        components.add(provider.parse(ModelComponentType.MAIN_ROD_SIDE, pos));
        components.add(provider.parse(ModelComponentType.SIDE_ROD_SIDE, pos));
        components.add(provider.parse(ModelComponentType.PISTON_ROD_SIDE, pos));
        components.add(provider.parse(ModelComponentType.CYLINDER_SIDE, pos));
        components.add(provider.parse(ModelComponentType.UNION_LINK_SIDE, pos));
        components.add(provider.parse(ModelComponentType.COMBINATION_LEVER_SIDE, pos));
        components.add(provider.parse(ModelComponentType.ECCENTRIC_CRANK_SIDE, pos));
        components.add(provider.parse(ModelComponentType.ECCENTRIC_ROD_SIDE, pos));
        components.add(provider.parse(ModelComponentType.EXPANSION_LINK_SIDE, pos));
        components.add(provider.parse(ModelComponentType.RADIUS_BAR_SIDE, pos));
        components.add(provider.parse(ModelComponentType.VALVE_STEM_SIDE, pos));
        components.add(provider.parse(ModelComponentType.REVERSING_ARM_SIDE, pos));
        components.add(provider.parse(ModelComponentType.LIFTING_LINK_SIDE, pos));
        components.add(provider.parse(ModelComponentType.REACH_ROD_SIDE, pos));
        components.addAll(provider.parseAll(ModelComponentType.VALVE_PART_SIDE_ID, pos));

        components = components.stream().filter(Objects::nonNull).collect(Collectors.toList());

        return !components.isEmpty() ? new CustomValveGear(state, custom, wheels, components) : null;
    }

    public CustomValveGear(ModelState state, Identifier custom, WheelSet wheels, List<ModelComponent> components) {
        this.wheels = wheels;
        this.components = components;

        try {
            animation = new Animatrix(custom.getResourceStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        state.push(settings -> settings.add((ModelState.GroupAnimator) (stock, group) ->
                animation.groups().contains(group) ? animation.getMatrix(group, angle(stock.distanceTraveled) / 360) : null)
        ).include(components);
    }

    @Override
    public void effects(EntityMoveableRollingStock stock, float throttle) {

    }

    @Override
    public boolean isEndStroke(EntityMoveableRollingStock stock, float throttle) {
        return false;
    }

    @Override
    public float angle(double distance) {
        return wheels.angle(distance);
    }
}
