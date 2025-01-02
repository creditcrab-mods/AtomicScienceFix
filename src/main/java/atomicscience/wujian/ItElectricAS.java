package atomicscience.wujian;

import atomicscience.TabAS;
import universalelectricity.api.item.ItemRF;
import universalelectricity.core.item.ItemElectric;

public abstract class ItElectricAS extends ItemRF {
    public ItElectricAS(String name) {
        super();
        this.setUnlocalizedName("atomicscience:" + name);
        this.setCreativeTab(TabAS.INSTANCE);
    }
}
