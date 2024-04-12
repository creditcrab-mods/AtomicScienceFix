package atomicscience.shimian;

import atomicscience.fenlie.TFissionReactor;
import atomicscience.wujian.ItFissileFuel;
import calclavia.lib.gui.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import universalelectricity.prefab.SlotSpecific;

public class CFissionReactor extends ContainerBase {
    public CFissionReactor(EntityPlayer player, TFissionReactor tileEntity) {
        super(tileEntity);
        this.addPlayerInventory(player);
        this.addSlotToContainer(
            new SlotSpecific(tileEntity, 0, 79, 21, new Class[] { ItFissileFuel.class })
        );
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1) {
        ItemStack var2 = null;
        Slot var3 = (Slot) super.inventorySlots.get(par1);
        if (var3 != null && var3.getHasStack()) {
            ItemStack itemStack = var3.getStack();
            var2 = itemStack.copy();
            if (par1 >= super.slotCount) {
                if (this.getSlot(0).isItemValid(itemStack)) {
                    if (!this.mergeItemStack(itemStack, 0, 1, false)) {
                        return null;
                    }
                } else if (par1 < 27 + super.slotCount) {
                    if (!this.mergeItemStack(
                            itemStack, 27 + super.slotCount, 36 + super.slotCount, false
                        )) {
                        return null;
                    }
                } else if (par1 >= 27 + super.slotCount && par1 < 36 + super.slotCount && !this.mergeItemStack(itemStack, 4, 30, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(
                           itemStack, super.slotCount, 36 + super.slotCount, false
                       )) {
                return null;
            }

            if (itemStack.stackSize == 0) {
                var3.putStack((ItemStack) null);
            } else {
                var3.onSlotChanged();
            }

            if (itemStack.stackSize == var2.stackSize) {
                return null;
            }

            var3.onPickupFromSlot(par1EntityPlayer, itemStack);
        }

        return var2;
    }
}
