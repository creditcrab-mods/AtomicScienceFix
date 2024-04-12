package atomicscience.fenlie;

import atomicscience.TabAS;
import atomicscience.wujian.ItCell;

public class ItStrangeMatter extends ItCell {
    public ItStrangeMatter() {
        super("strangeMatter");
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
        this.setCreativeTab(TabAS.INSTANCE);
    }
}
