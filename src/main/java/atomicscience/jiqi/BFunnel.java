package atomicscience.jiqi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BFunnel extends BBase {
    private IIcon iconTop;

    public BFunnel() {
        super("funnel");
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return side != 1 && side != 0 ? this.blockIcon : this.iconTop;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);
        this.iconTop = iconRegister.registerIcon(
            this.getUnlocalizedName().replace("tile.", "") + "_top"
        );
        this.blockIcon
            = iconRegister.registerIcon(this.getUnlocalizedName().replace("tile.", ""));
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int meta) {
        return new TFunnel();
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
