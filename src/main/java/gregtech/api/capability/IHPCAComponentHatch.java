package gregtech.api.capability;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.mui.GTGuiTextures;

import com.cleanroommc.modularui.drawable.UITexture;

public interface IHPCAComponentHatch {

    /**
     * How much EU/t this component needs for the multi to just be idle.
     * Used in 2 ways:
     * - "Non-computational" units like HPCA Bridge, Active Cooler
     * - "Computational base cost" for units like HPCA Computation, High Computation
     */
    int getUpkeepEUt();

    /**
     * How much EU/t this component can use, if it is being utilized fully.
     * Used to scale cost for "computational" units. Power draw is a range
     * created by actual computation used vs maximum potential computation.
     */
    default int getMaxEUt() {
        return getUpkeepEUt();
    }

    /**
     * If this component can be damaged by HPCA overheat.
     */
    boolean canBeDamaged();

    /**
     * If this component is currently damaged by HPCA overheat.
     */
    default boolean isDamaged() {
        return false;
    }

    /**
     * Set this component as damaged (or undamaged).
     */
    default void setDamaged(boolean damaged) {}

    /**
     * If this component allows for bridging HPCAs to Network Switches.
     */
    boolean isBridge();

    /**
     * The icon for this component in the HPCA's UI. Should be a 13x13 px sprite.
     */
    default UITexture getComponentIcon() {
        return GTGuiTextures.HPCA_ICON_EMPTY_COMPONENT;
    }

    /**
     * The untranslated name of the tile implementing an HPCA component
     */
    default String getTileName() {
        if (this instanceof MetaTileEntity mte) {
            return mte.getMetaFullName();
        }
        return "";
    }
}
