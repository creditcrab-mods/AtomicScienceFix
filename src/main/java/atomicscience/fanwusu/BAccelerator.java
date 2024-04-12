package atomicscience.fanwusu;

import atomicscience.AtomicScience;
import atomicscience.CommonProxy;
import atomicscience.api.IElectromagnet;
import atomicscience.jiqi.BBaseRotatable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalelectricity.core.UniversalElectricity;

public class BAccelerator extends BBaseRotatable implements IElectromagnet {
    public BAccelerator() {
        super("accelerator", UniversalElectricity.machine);
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
        par1World.getBlockMetadata(x, y, z);
        if (!par1World.isRemote) {
            par5EntityPlayer.openGui(
                AtomicScience.instance,
                CommonProxy.GuiType.ACCELERATOR.ordinal(),
                par1World,
                x,
                y,
                z
            );
        }

        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TAccelerator();
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("atomicscience:accelerator");
    }

    @Override
    public boolean isRunning(World world, int x, int y, int z) {
        return true;
    }
}
