package atomicscience.hecheng;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BElectromagnetBoiler extends BElectromagnet {
    public BElectromagnetBoiler() {
        super("electromagnetBoiler");
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int meta) {
        return new TElectromagnetBoiler();
    }
}
