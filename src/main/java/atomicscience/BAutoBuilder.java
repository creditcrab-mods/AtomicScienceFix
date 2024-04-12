package atomicscience;

import atomicscience.jiqi.BBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BAutoBuilder extends BBase {
    public BAutoBuilder() {
        super("instantBuilder");
        this.textureName = "atomicscience:instantBuilder";
    }

    @Override
    public boolean onMachineActivated(
        World par1World,
        int x,
        int y,
        int z,
        EntityPlayer par5EntityPlayer,
        int side,
        float hitX,
        float hitY,
        float hitZ
    ) {
        par5EntityPlayer.openGui(AtomicScience.instance, -1, par1World, x, y, z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int meta) {
        return new TAutoBuilder();
    }
}
