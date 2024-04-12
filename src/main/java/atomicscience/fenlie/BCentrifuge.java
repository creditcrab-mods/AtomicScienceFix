package atomicscience.fenlie;

import atomicscience.AtomicScience;
import atomicscience.CommonProxy;
import atomicscience.jiqi.BBaseRotatable;
import atomicscience.render.RH;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalelectricity.core.UniversalElectricity;

public class BCentrifuge extends BBaseRotatable {
    public BCentrifuge() {
        super("centrifuge", UniversalElectricity.machine);
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
                CommonProxy.GuiType.CENTRIFUGE.ordinal(),
                par1World,
                x,
                y,
                z
            );
            return true;
        } else {
            return true;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TCentrifuge();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return RH.BLOCK_RENDER_ID;
    }
}
