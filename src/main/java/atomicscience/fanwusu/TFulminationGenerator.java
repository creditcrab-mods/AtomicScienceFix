package atomicscience.fanwusu;

import atomicscience.fanwusu.FulminationEventHandler;
import calclavia.lib.TileEntityUniversalProducer;
import universalelectricity.core.UniversalElectricity;

public class TFulminationGenerator extends TileEntityUniversalProducer {
    public final int DIAN = 5000;
    public double dian;

    public TFulminationGenerator() {
        FulminationEventHandler.INSTANCE.register(this);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.isDisabled()) {
            this.produce(Math.min(this.dian, 5000.0D));
        }

        this.dian = Math.max(this.dian - 5000.0D, 0.0D);
    }

    public void invalidate() {
        FulminationEventHandler.INSTANCE.unregister(this);
        super.initiate();
    }

    public double getVoltage() {
        return UniversalElectricity.isVoltageSensitive ? 480.0D : 120.0D;
    }
}
