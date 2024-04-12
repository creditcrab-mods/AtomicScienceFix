package atomicscience;

import atomicscience.jiqi.TInventory;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.electricity.ElectricityPack;

public class TAtomicAssembler extends TInventory {
    public final int SMELTING_TICKS = 1200;
    public final float DIAN = 10000.0F;
    public int smeltingTicks = 0;
    public float rotationYaw1;
    public float rotationYaw2;
    public float rotationYaw3;
    public EntityItem entityItem;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.isDisabled()) {
            if (!this.worldObj.isRemote) {
                if (this.canWork()) {
                    double var10000 = super.wattsReceived;
                    this.getClass();
                    if (var10000 >= 10000.0D) {
                        if (this.smeltingTicks == 0) {
                            this.getClass();
                            this.smeltingTicks = 1200;
                        }

                        if (this.smeltingTicks > 0) {
                            --this.smeltingTicks;
                            if (this.smeltingTicks < 1) {
                                this.work();
                                this.smeltingTicks = 0;
                            }
                        } else {
                            this.smeltingTicks = 0;
                        }

                        super.wattsReceived = 0.0D;
                    }
                } else {
                    this.smeltingTicks = 0;
                }

                if (super.ticks % 10L == 0L) {
                    this.worldObj.markBlockForUpdate(
                        this.xCoord, this.yCoord, this.zCoord
                    );
                }
            }

            if (this.worldObj.isRemote && this.smeltingTicks > 0) {
                if (super.ticks % 600L == 0L) {
                    this.worldObj.playSoundEffect(
                        (double) this.xCoord,
                        (double) this.yCoord,
                        (double) this.zCoord,
                        SoundManager.ASSEMBLER,
                        0.7F,
                        1.0F
                    );
                }

                this.rotationYaw1 += 3.0F;
                this.rotationYaw2 += 2.0F;
                ++this.rotationYaw3;
                ItemStack var3 = this.getStackInSlot(6);
                if (var3 != null) {
                    var3 = var3.copy();
                    var3.stackSize = 1;
                    if (this.entityItem == null) {
                        this.entityItem
                            = new EntityItem(this.worldObj, 0.0D, 0.0D, 0.0D, var3);
                    } else if (!var3.isItemEqual(this.entityItem.getEntityItem())) {
                        this.entityItem
                            = new EntityItem(this.worldObj, 0.0D, 0.0D, 0.0D, var3);
                    }

                    ++this.entityItem.age;
                } else {
                    this.entityItem = null;
                }
            }
        }
    }

    @Override
    public ElectricityPack getRequest() {
        return this.canWork()
            ? new ElectricityPack(10000.0D / this.getVoltage(), this.getVoltage())
            : new ElectricityPack();
    }

    @Override
    public void onDataPacket(NetworkManager arg0, S35PacketUpdateTileEntity arg1) {
        NBTTagCompound nbt = arg1.func_148857_g();

        this.smeltingTicks = nbt.getInteger("smeltingTicks");
        super.disabledTicks = nbt.getInteger("disabledTicks");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger("smeltingTicks", this.smeltingTicks);
        nbt.setInteger("disabledTicks", super.disabledTicks);

        return new S35PacketUpdateTileEntity(
            this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), nbt
        );
    }

    @Override
    public void openInventory() {
        if (!this.worldObj.isRemote) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    public boolean canWork() {
        if (super.containingItems[6] != null) {
            for (int i = 0; i < 6; ++i) {
                if (super.containingItems[i] == null) {
                    return false;
                }

                if (super.containingItems[i].getItem()
                    != AtomicScience.itCellStrangeMatter) {
                    return false;
                }
            }

            return super.containingItems[6].stackSize < 64;
        } else {
            return false;
        }
    }

    public void work() {
        if (this.canWork()) {
            for (int i = 0; i <= 5; ++i) {
                if (super.containingItems[i] != null) {
                    super.containingItems[i].setItemDamage(
                        super.containingItems[i].getItemDamage() + 1
                    );
                    if (super.containingItems[i].getItemDamage() >= 64) {
                        super.containingItems[i] = null;
                    }
                }
            }

            if (super.containingItems[6] != null) {
                ++super.containingItems[6].stackSize;
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.smeltingTicks = nbt.getInteger("smeltingTicks");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("smeltingTicks", this.smeltingTicks);
    }

    @Override
    public double getVoltage() {
        return UniversalElectricity.isVoltageSensitive ? 480.0D : 120.0D;
    }

    @Override
    public int getSizeInventory() {
        return 7;
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
        return slotID == 6 ? itemStack.isStackable()
                           : itemStack.getItem() == AtomicScience.itCellStrangeMatter;
    }
}
