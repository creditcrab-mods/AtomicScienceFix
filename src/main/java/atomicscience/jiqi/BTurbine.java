package atomicscience.jiqi;

import atomicscience.render.RH;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BTurbine extends BBase {
    public BTurbine() {
        super("turbine");
    }

    @Override
    public boolean onUseWrench(
        World world,
        int x,
        int y,
        int z,
        EntityPlayer par5EntityPlayer,
        int side,
        float hitX,
        float hitY,
        float hitZ
    ) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return tileEntity instanceof TTurbine ? ((TTurbine) tileEntity).checkMultiblock()
                                              : false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TTurbine) {
            if (((TTurbine) tileEntity).isMultiblock) {
                ((TTurbine) tileEntity).checkMultiblock();
            } else if (((TTurbine) tileEntity).masterTurbine != null) {
                TileEntity t = ((TTurbine) tileEntity).masterTurbine.getTileEntity(world);
                if (t instanceof TTurbine) {
                    ((TTurbine) t).setMaster((TTurbine) null);
                }
            }
        }

        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return RH.BLOCK_RENDER_ID;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int meta) {
        return new TTurbine();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
