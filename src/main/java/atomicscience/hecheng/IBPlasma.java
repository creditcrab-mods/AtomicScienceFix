package atomicscience.hecheng;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class IBPlasma extends ItemBlock {
    public IBPlasma(Block b) {
        super(b);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
