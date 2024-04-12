package atomicscience.wujian;

import atomicscience.TabAS;
import universalelectricity.core.item.ItemElectric;

public abstract class ItElectricAS extends ItemElectric {
    public ItElectricAS(String name) {
        super();
        this.setUnlocalizedName("atomicscience:" + name);
        this.setCreativeTab(TabAS.INSTANCE);
    }
}
