package atomicscience.fenlie;

import atomicscience.AtomicScience;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.prefab.SlotSpecific;

public class CCentrifuge extends Container {
    private TCentrifuge tileEntity;

    public CCentrifuge(InventoryPlayer par1InventoryPlayer, TCentrifuge tileEntity) {
        this.tileEntity = tileEntity;
        this.addSlotToContainer(
            new SlotSpecific(tileEntity, 0, 131, 26, IItemElectric.class)
        );
        this.addSlotToContainer(new Slot(tileEntity, 1, 25, 50));
        this.addSlotToContainer(
            new SlotFurnace(par1InventoryPlayer.player, tileEntity, 2, 81, 26)
        );
        this.addSlotToContainer(
            new SlotFurnace(par1InventoryPlayer.player, tileEntity, 3, 101, 26)
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

    @Override
    public void onContainerClosed(EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        this.tileEntity.players.remove(entityplayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.tileEntity.isUseableByPlayer(par1EntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1) {
        ItemStack var2 = null;
        Slot var3 = (Slot) super.inventorySlots.get(par1);
        if (var3 != null && var3.getHasStack()) {
            ItemStack itemStack = var3.getStack();
            var2 = itemStack.copy();
            if (par1 >= 4) {
                if (this.getSlot(0).isItemValid(itemStack)) {
                    if (!this.mergeItemStack(itemStack, 0, 1, false)) {
                        return null;
                    }
                } else if (AtomicScience.isUraniumOre(itemStack)) {
                    if (!this.mergeItemStack(itemStack, 1, 2, false)) {
                        return null;
                    }
                } else if (AtomicScience.isCell(itemStack)) {
                    if (!this.mergeItemStack(itemStack, 3, 4, false)) {
                        return null;
                    }
                } else if (par1 < 31) {
                    if (!this.mergeItemStack(itemStack, 31, 40, false)) {
                        return null;
                    }
                } else if (par1 >= 31 && par1 < 40 && !this.mergeItemStack(itemStack, 4, 30, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemStack, 4, 40, false)) {
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
