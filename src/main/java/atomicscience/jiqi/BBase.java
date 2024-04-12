package atomicscience.jiqi;

import atomicscience.TabAS;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.prefab.block.BlockAdvanced;

public class BBase extends BlockAdvanced {
    protected String textureName;

    public BBase(String name, Material material) {
        super(material);
        this.textureName = "atomicscience:machine";
        this.setBlockName("atomicscience:" + name);
        this.setCreativeTab(TabAS.INSTANCE);
    }

    public BBase(String name) {
        this(name, UniversalElectricity.machine);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        if (this.getRenderType() == 0
            && this.textureName.equals("atomicscience:machine")) {
            super.registerBlockIcons(iconRegister);
        } else {
            this.blockIcon = iconRegister.registerIcon(this.textureName);
        }
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return this.createNewTileEntity(world, meta);
    }

    @Override
    public TileEntity createNewTileEntity(World arg0, int arg1) {
        return null;
    }
}
