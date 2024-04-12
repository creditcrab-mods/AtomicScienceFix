package atomicscience.hecheng;

import atomicscience.AtomicScience;
import atomicscience.jiqi.TChemicalExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.prefab.SlotSpecific;

public class CChemicalExtractor extends Container {
    // private static final int slotCount = 4;
    private TChemicalExtractor tileEntity;

    public CChemicalExtractor(
        InventoryPlayer par1InventoryPlayer, TChemicalExtractor tileEntity
    ) {
        this.tileEntity = tileEntity;
        this.addSlotToContainer(
            new SlotSpecific(tileEntity, 0, 65, 49, IItemElectric.class)
        );
        this.addSlotToContainer(new Slot(tileEntity, 1, 25, 50));
        this.addSlotToContainer(
            new SlotFurnace(par1InventoryPlayer.player, tileEntity, 2, 118, 25)
        );
        this.addSlotToContainer(new Slot(tileEntity, 3, 65, 25));

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

        tileEntity.openInventory();
        this.tileEntity.players.add(par1InventoryPlayer.player);
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
                if (itemStack.getItem() instanceof IItemElectric) {
                    if (!this.mergeItemStack(itemStack, 0, 1, false)) {
                        return null;
                    }
                } else if (FluidContainerRegistry.getFluidForFilledItem(itemStack) != null && FluidRegistry.WATER == FluidContainerRegistry.getFluidForFilledItem(itemStack)
                        .getFluid()) {
                    if (!this.mergeItemStack(itemStack, 1, 2, false)) {
                        return null;
                    }
                } else if (!AtomicScience.isCell(itemStack) && !AtomicScience.isUraniumOre(itemStack)) {
                    if (par1 < 31) {
                        if (!this.mergeItemStack(itemStack, 31, 40, false)) {
                            return null;
                        }
                    } else if (par1 >= 31 && par1 < 40 && !this.mergeItemStack(itemStack, 4, 30, false)) {
                        return null;
                    }
                } else if (!this.mergeItemStack(itemStack, 3, 4, false)) {
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
