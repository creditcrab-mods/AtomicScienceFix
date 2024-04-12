package atomicscience.fenlie;

import atomicscience.jiqi.BBase;
import net.minecraft.block.material.Material;

public class BControlRod extends BBase {
    public BControlRod() {
        super("controlRod", Material.iron);
        this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
        this.textureName = "minecraft:iron_block";
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
    public boolean hasTileEntity(int metadata) {
        return false;
    }
}
