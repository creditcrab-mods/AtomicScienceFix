package atomicscience;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class TileEntityFilteredSlot extends Slot {
    public TileEntityFilteredSlot(IInventory arg0, int arg1, int arg2, int arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    @Override
    public boolean isItemValid(ItemStack arg0) {
        return this.inventory.isItemValidForSlot(this.getSlotIndex(), arg0);
    }
}
