package atomicscience.wujian;

import atomicscience.api.IFissileMaterial;
import atomicscience.api.ITemperature;
import atomicscience.fenlie.ItRadioactive;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItBreederFuel extends ItRadioactive implements IFissileMaterial {
    public static final int FU_LAN = 40000;

    public ItBreederFuel() {
        super("rodBreederFuel");
        this.setMaxDamage('\u9c40');
        this.setMaxStackSize(1);
        this.setNoRepair();
        this.setTextureName("atomicscience:rodBreederFuel");
    }

    public int onFissile(ITemperature reactor) {
        TileEntity tileEntity = (TileEntity) reactor;
        World worldObj = tileEntity.getWorldObj();
        reactor.setTemperature((float
        ) ((double) reactor.getTemperature() + 1.5D
           + (double) worldObj.rand.nextFloat() * 0.5D));
        return 0;
    }
}
