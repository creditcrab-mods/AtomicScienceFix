package atomicscience.fanwusu;

import atomicscience.AtomicScience;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class CAccelerator extends Container {
    private TAccelerator tileEntity;

    public CAccelerator(InventoryPlayer par1InventoryPlayer, TAccelerator tileEntity) {
        this.tileEntity = tileEntity;
        this.addSlotToContainer(new Slot(tileEntity, 0, 132, 24));
        this.addSlotToContainer(new Slot(tileEntity, 1, 132, 51));
        this.addSlotToContainer(
            new SlotFurnace(par1InventoryPlayer.player, tileEntity, 2, 152, 51)
        );

        int var3;
        for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(
                    par1InventoryPlayer,
                    var4 + var3 * 9 + 9,
                    8 + var4 * 18,
                    84 + var3 * 18
                ));
            }
        }

        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(
                new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142)
            );
        }

        this.tileEntity.players.add(par1InventoryPlayer.player);
        tileEntity.openInventory();
    }

    public void onContainerClosed(EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        this.tileEntity.players.remove(entityplayer);
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.tileEntity.isUseableByPlayer(par1EntityPlayer);
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1) {
        ItemStack var2 = null;
        Slot var3 = (Slot) super.inventorySlots.get(par1);
        if (var3 != null && var3.getHasStack()) {
            ItemStack itemStack = var3.getStack();
            var2 = itemStack.copy();
            if (par1 > 2) {
                if (itemStack.getItem() == AtomicScience.itCell) {
                    if (!this.mergeItemStack(itemStack, 1, 2, false)) {
                        return null;
                    }
                } else if (!this.mergeItemStack(itemStack, 0, 1, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemStack, 3, 39, false)) {
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
