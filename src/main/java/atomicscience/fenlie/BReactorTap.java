package atomicscience.fenlie;

import atomicscience.jiqi.BBaseRotatable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BReactorTap extends BBaseRotatable {
    private IIcon frontIcon;

    public BReactorTap() {
        super("reactorTap");
    }

    @Override
    public void onBlockPlacedBy(
        World world,
        int x,
        int y,
        int z,
        EntityLivingBase entityLiving,
        ItemStack itemStack
    ) {
        if (MathHelper.abs((float) entityLiving.posX - (float) x) < 2.0F
            && MathHelper.abs((float) entityLiving.posZ - (float) z) < 2.0F) {
            double d0 = entityLiving.posY + 1.82D - (double) entityLiving.yOffset;
            if (d0 - (double) y > 2.0D) {
                this.setDirection(world, x, y, z, ForgeDirection.getOrientation(1));
                return;
            }

            if ((double) y - d0 > 0.0D) {
                this.setDirection(world, x, y, z, ForgeDirection.getOrientation(0));
                return;
            }
        }

        super.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);
    }

    public IIcon getIcon(int side, int metadata) {
        return side == metadata ? this.frontIcon : this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);
        this.frontIcon = iconRegister.registerIcon(
            this.getUnlocalizedName().replace("tile.", "") + "_front"
        );
        this.blockIcon
            = iconRegister.registerIcon(this.getUnlocalizedName().replace("tile.", ""));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TReactorTap();
    }
}
