package atomicscience.fenlie;

import atomicscience.AtomicScience;
import atomicscience.jiqi.TInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.item.ElectricItemHelper;
import universalelectricity.prefab.implement.IRotatable;

public class TNuclearBoiler
    extends TInventory implements ISidedInventory, IFluidHandler, IRotatable {
    public final int SHI_JIAN = 300;
    public final float DIAN = 800.0F;
    public int smeltingTicks = 0;
    public float rotation = 0.0F;
    public final FluidTank waterTank;
    public final FluidTank gasTank;

    public TNuclearBoiler() {
        this.waterTank = new FluidTank(FluidRegistry.WATER, 0, 5000);
        this.gasTank = new FluidTank(AtomicScience.FLUID_URANIUM_HEXAFLOURIDE, 0, 5000);
        // TODO: WTF
        // this.waterTank.setTankPressure(-10);
        // this.gasTank.setTankPressure(10);
    }

    @Override
    public ElectricityPack getRequest() {
        return this.canWork()
            ? new ElectricityPack(800.0D / this.getVoltage(), this.getVoltage())
            : new ElectricityPack();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote && !this.isDisabled()) {
            if (super.containingItems[1] != null
                && FluidContainerRegistry.isFilledContainer(super.containingItems[1])) {
                if (FluidContainerRegistry.getFluidForFilledItem(super.containingItems[1])
                            .getFluid()
                        == FluidRegistry.WATER
                    && this.fill(
                           ForgeDirection.UNKNOWN,
                           FluidContainerRegistry.getFluidForFilledItem(
                               super.containingItems[1]
                           ),
                           false
                       ) > 0) {
                    ItemStack player
                        = super.containingItems[1].getItem().getContainerItem(
                            super.containingItems[1]
                        );
                    this.waterTank.fill(
                        FluidContainerRegistry.getFluidForFilledItem(
                            super.containingItems[1]
                        ),
                        true
                    );
                    if (player == null && super.containingItems[1].stackSize > 1) {
                        --super.containingItems[1].stackSize;
                    } else {
                        super.containingItems[1] = player;
                    }
                }
            }

            if (this.canWork()) {
                super.wattsReceived += ElectricItemHelper.dechargeItem(
                    super.containingItems[0], 800.0D, this.getVoltage()
                );
                double var10000 = super.wattsReceived;
                this.getClass();
                if (var10000 >= (double) (800.0F / 2.0F)) {
                    if (this.smeltingTicks == 0) {
                        this.smeltingTicks = 300;
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
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
        }
    }

    @Override
    public void onDataPacket(NetworkManager arg0, S35PacketUpdateTileEntity arg1) {
        NBTTagCompound nbt = arg1.func_148857_g();
        this.smeltingTicks = nbt.getInteger("smeltingTicks");
        this.waterTank.setFluid(
            new FluidStack(FluidRegistry.WATER, nbt.getInteger("waterAmount"))
        );
        this.gasTank.setFluid(new FluidStack(
            AtomicScience.FLUID_URANIUM_HEXAFLOURIDE,
            nbt.getInteger("uraniumHexaflourideAmount")
        ));
        super.disabledTicks = nbt.getInteger("disabledTicks");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("smeltingTicks", this.smeltingTicks);
        nbt.setInteger("waterAmount", this.waterTank.getFluidAmount());
        nbt.setInteger("uraniumHexaflourideAmount", this.gasTank.getFluidAmount());
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
        return !this.isDisabled() && this.waterTank.getFluid() != null
            && this.waterTank.getFluid().getFluid() != null
            && this.waterTank.getFluidAmount() >= 1000 && super.containingItems[3] != null
            && (AtomicScience.itYellowcake == super.containingItems[3].getItem()
                || AtomicScience.isUraniumOre(super.containingItems[3]))
            && this.gasTank.getFluidAmount() < this.gasTank.getCapacity();
    }

    public void work() {
        if (this.canWork()) {
            this.waterTank.drain(1000, true);
            // FluidStack liquid = AtomicScience.FLUID_URANIUM_HEXAFLOURIDE.copy();
            // liquid.amount = AtomicScience.URANIUM_HEXAFLOURIDE_RATIO * 2;
            this.gasTank.fill(
                new FluidStack(
                    AtomicScience.FLUID_URANIUM_HEXAFLOURIDE,
                    AtomicScience.URANIUM_HEXAFLOURIDE_RATIO * 2
                ),
                true
            );
            this.decrStackSize(3, 1);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.smeltingTicks = nbt.getInteger("smeltingTicks");
        NBTTagCompound waterCompound = nbt.getCompoundTag("water");
        this.waterTank.setFluid(FluidStack.loadFluidStackFromNBT(waterCompound));
        NBTTagCompound gasCompound = nbt.getCompoundTag("gas");
        this.gasTank.setFluid(FluidStack.loadFluidStackFromNBT(gasCompound));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("smeltingTicks", this.smeltingTicks);
        NBTTagCompound compound;
        if (this.waterTank.getFluid() != null) {
            compound = new NBTTagCompound();
            this.waterTank.getFluid().writeToNBT(compound);
            nbt.setTag("water", compound);
        }

        if (this.gasTank.getFluid() != null) {
            compound = new NBTTagCompound();
            this.gasTank.getFluid().writeToNBT(compound);
            nbt.setTag("gas", compound);
        }
    }

    @Override
    public boolean canFill(ForgeDirection arg0, Fluid arg1) {
        if (arg1 == FluidRegistry.WATER) {
            return this.waterTank.getFluidAmount() < this.waterTank.getCapacity();
        } else if (arg1 == AtomicScience.FLUID_URANIUM_HEXAFLOURIDE) {
            return this.gasTank.getFluidAmount() < this.waterTank.getCapacity();
        }

        return false;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return FluidRegistry.WATER == resource.getFluid()
            ? this.waterTank.fill(resource, doFill)
            : (AtomicScience.FLUID_URANIUM_HEXAFLOURIDE == resource.getFluid()
                   ? this.gasTank.fill(resource, doFill)
                   : 0);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return this.gasTank.drain(maxDrain, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
        return arg1.getFluid() == AtomicScience.FLUID_URANIUM_HEXAFLOURIDE
            ? this.gasTank.drain(arg1.amount, true)
            : null;
    }

    @Override
    public boolean canDrain(ForgeDirection arg0, Fluid arg1) {
        return arg1 == AtomicScience.FLUID_URANIUM_HEXAFLOURIDE
            && this.gasTank.getFluidAmount() > 0;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
        return new FluidTankInfo[] { new FluidTankInfo(this.waterTank),
                                     new FluidTankInfo(this.gasTank) };
    }

    @Override
    public int getSizeInventory() {
        return 4;
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
        return slotID == 1
            ? AtomicScience.isCellWater(itemStack)
            : (slotID == 3 ? itemStack.getItem() == AtomicScience.itYellowcake : false);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 0 ? new int[] { 2 } : new int[] { 1, 3 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack itemStack, int side) {
        return this.isItemValidForSlot(slotID, itemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, int j) {
        return slotID == 2;
    }
}
