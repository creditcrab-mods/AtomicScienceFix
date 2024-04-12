package atomicscience.jiqi;

import atomicscience.AtomicScience;
import atomicscience.TAtomicAssembler;
import atomicscience.TileEntityFilteredSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CAssembler extends Container {
    private TAtomicAssembler tileEntity;

    public CAssembler(InventoryPlayer par1InventoryPlayer, TAtomicAssembler tileEntity) {
        this.tileEntity = tileEntity;
        this.addSlotToContainer(new TileEntityFilteredSlot(tileEntity, 0, 80, 40));
        this.addSlotToContainer(new TileEntityFilteredSlot(tileEntity, 1, 53, 56));
        this.addSlotToContainer(new TileEntityFilteredSlot(tileEntity, 2, 107, 56));
        this.addSlotToContainer(new TileEntityFilteredSlot(tileEntity, 3, 53, 88));
        this.addSlotToContainer(new TileEntityFilteredSlot(tileEntity, 4, 107, 88));
        this.addSlotToContainer(new TileEntityFilteredSlot(tileEntity, 5, 80, 103));
        this.addSlotToContainer(new TileEntityFilteredSlot(tileEntity, 6, 80, 72));

        int var3;
        for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(
                    par1InventoryPlayer,
                    var4 + var3 * 9 + 9,
                    8 + var4 * 18,
                    148 + var3 * 18
                ));
            }
        }

        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(
                new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 206)
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
            if (par1 > 6) {
                if (itemStack.getItem() == AtomicScience.itCellStrangeMatter) {
                    if (!this.mergeItemStack(itemStack, 0, 6, false)) {
                        return null;
                    }
                } else if (!this.getSlot(6).getHasStack() && itemStack.isStackable() && !this.mergeItemStack(itemStack, 6, 7, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemStack, 7, 43, false)) {
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
