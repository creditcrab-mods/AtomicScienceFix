package atomicscience.hecheng;

import atomicscience.AtomicScience;
import atomicscience.fenlie.TNuclearBoiler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.prefab.SlotSpecific;

public class CNuclearBoiler extends Container {
    // private static final int slotCount = 4;
    private TNuclearBoiler tileEntity;

    public CNuclearBoiler(
        InventoryPlayer par1InventoryPlayer, TNuclearBoiler tileEntity
    ) {
        this.tileEntity = tileEntity;
        this.addSlotToContainer(
            new SlotSpecific(tileEntity, 0, 56, 26, IItemElectric.class)
        );
        this.addSlotToContainer(new Slot(tileEntity, 1, 25, 50));
        this.addSlotToContainer(new Slot(tileEntity, 2, 136, 50));
        this.addSlotToContainer(new SlotSpecific(
            tileEntity,
            3,
            81,
            26,
            new ItemStack[] { new ItemStack(AtomicScience.itYellowcake),
                              new ItemStack(AtomicScience.bUraniumOre) }
        ));

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
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotID) {
        ItemStack var2 = null;
        Slot slot = (Slot) super.inventorySlots.get(slotID);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            var2 = itemStack.copy();
            if (slotID >= 4) {
                if (itemStack.getItem() instanceof IItemElectric) {
                    if (!this.mergeItemStack(itemStack, 0, 1, false)) {
                        return null;
                    }
                } else if (FluidContainerRegistry.isContainer(itemStack) && FluidContainerRegistry.getFluidForFilledItem(itemStack) != null &&
                   FluidRegistry.WATER ==
                       FluidContainerRegistry.getFluidForFilledItem(itemStack)
                           .getFluid()) {
                    if (!this.mergeItemStack(itemStack, 1, 2, false)) {
                        return null;
                    }
                } else if (this.getSlot(3).isItemValid(itemStack)) {
                    if (!this.mergeItemStack(itemStack, 3, 4, false)) {
                        return null;
                    }
                } else if (slotID < 31) {
                    if (!this.mergeItemStack(itemStack, 31, 40, false)) {
                        return null;
                    }
                } else if (slotID >= 31 && slotID < 40 && !this.mergeItemStack(itemStack, 4, 30, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemStack, 4, 40, false)) {
                return null;
            }

            if (itemStack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemStack.stackSize == var2.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemStack);
        }

        return var2;
    }
}
