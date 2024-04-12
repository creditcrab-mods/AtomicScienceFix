package atomicscience.hecheng;

import atomicscience.api.IElectromagnet;
import atomicscience.jiqi.BBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BElectromagnetGlass extends BBase implements IElectromagnet {
    public BElectromagnetGlass() {
        super("electromagnetGlass", Material.glass);
        this.textureName = "atomicscience:electromagnetGlass";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(
        IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5
    ) {
        Block i1 = par1IBlockAccess.getBlock(par2, par3, par4);
        return i1 == this
            ? false
            : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }

    @Override
    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public boolean isRunning(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return false;
    }
}
