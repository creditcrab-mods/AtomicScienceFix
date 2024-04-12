package atomicscience.fanwusu;

import atomicscience.jiqi.BBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BFulminationGenerator extends BBase {
    public BFulminationGenerator() {
        super("fulmination");
        this.setHardness(50.0F);
        this.textureName = "atomicscience:fulmination";
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TFulminationGenerator();
    }
}
