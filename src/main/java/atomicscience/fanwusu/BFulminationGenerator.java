package atomicscience.fanwusu;

import atomicscience.jiqi.BBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import universalelectricity.prefab.tile.TileEntityAdvanced;

public class BFulminationGenerator extends BBase {
    public BFulminationGenerator() {
        super("fulmination");
        this.setHardness(50.0F);
        this.textureName = "atomicscience:fulmination";
    }

    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ)
    {
        ((TileEntityAdvanced)world.getTileEntity(x,y,z)).onNeighborChange();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TFulminationGenerator();
    }
}
