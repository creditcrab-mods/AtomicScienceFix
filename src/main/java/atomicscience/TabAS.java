package atomicscience;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TabAS extends CreativeTabs {
    public static final TabAS INSTANCE = new TabAS("atomicscience");

    public TabAS(String par2Str) {
        super(CreativeTabs.getNextID(), par2Str);
    }

    public Item getTabIconItem() {
        return Item.getItemFromBlock(AtomicScience.bFusionReactor);
    }
}
