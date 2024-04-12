package atomicscience.wujian;

import atomicscience.TabAS;
import net.minecraft.item.Item;

public class ItAS extends Item {
    public ItAS(String name) {
        super();
        this.setUnlocalizedName("atomicscience:" + name);
        this.setCreativeTab(TabAS.INSTANCE);
    }
}
