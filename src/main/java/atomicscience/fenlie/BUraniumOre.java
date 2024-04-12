package atomicscience.fenlie;

import java.util.Random;

import atomicscience.AtomicScience;
import atomicscience.TabAS;
import atomicscience.api.BlockRadioactive;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BUraniumOre extends BlockRadioactive {
    public BUraniumOre() {
        super(Material.rock);
        this.setBlockName("atomicscience:oreUranium");
        this.setStepSound(Block.soundTypeStone);
        this.setCreativeTab(TabAS.INSTANCE);
        this.setHardness(2.0F);
        super.isRandomlyRadioactive = AtomicScience.ALLOW_RADIOACTIVE_ORES;
        super.canWalkPoison = AtomicScience.ALLOW_RADIOACTIVE_ORES;
        super.canSpread = false;
        super.radius = 1.0F;
        super.amplifier = 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random par5Random) {
        if (AtomicScience.ALLOW_RADIOACTIVE_ORES) {
            super.randomDisplayTick(world, x, y, z, par5Random);
        }
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return this.blockIcon;
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("atomicscience:oreUranium");
    }

    @Override
    public int quantityDropped(Random rand) {
        return 1;
    }
}
