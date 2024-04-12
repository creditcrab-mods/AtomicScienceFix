package atomicscience.jiqi;

import java.util.HashSet;
import java.util.Set;

import calclavia.lib.TileEntityUniversalRunnable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TInventory
    extends TileEntityUniversalRunnable implements IInventory {
    public final Set<EntityPlayer> players = new HashSet<>();
    protected ItemStack[] containingItems = new ItemStack[this.getSizeInventory()];

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this
            ? false
            : par1EntityPlayer.getDistanceSq(
                  (double) this.xCoord + 0.5D,
                  (double) this.yCoord + 0.5D,
                  (double) this.zCoord + 0.5D
              ) <= 64.0D;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.containingItems[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.containingItems[par1] != null) {
            ItemStack var3;
            if (this.containingItems[par1].stackSize <= par2) {
                var3 = this.containingItems[par1];
                this.containingItems[par1] = null;
                return var3;
            } else {
                var3 = this.containingItems[par1].splitStack(par2);
                if (this.containingItems[par1].stackSize == 0) {
                    this.containingItems[par1] = null;
                }

                return var3;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.containingItems[par1] != null) {
            ItemStack var2 = this.containingItems[par1];
            this.containingItems[par1] = null;
            return var2;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.containingItems[par1] = par2ItemStack;
        if (par2ItemStack != null
            && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public String getInventoryName() {
        return this.getBlockType().getLocalizedName();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return this.getStackInSlot(slot) == null
            ? true
            : (this.getStackInSlot(slot).stackSize + 1 <= 64
                   ? this.getStackInSlot(slot).isItemEqual(itemStack)
                   : false);
    }

    public void incrStackSize(int slot, ItemStack itemStack) {
        if (this.getStackInSlot(slot) == null) {
            this.setInventorySlotContents(slot, itemStack.copy());
        } else if (this.getStackInSlot(slot).isItemEqual(itemStack)) {
            this.getStackInSlot(slot).stackSize += itemStack.stackSize;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList var2 = nbt.getTagList("Items", 10);
        this.containingItems = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound) var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.containingItems.length) {
                this.containingItems[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.containingItems.length; ++var3) {
            if (this.containingItems[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.containingItems[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        nbt.setTag("Items", var2);
    }
}
