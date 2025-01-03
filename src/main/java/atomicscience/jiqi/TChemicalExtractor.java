package atomicscience.jiqi;

import atomicscience.AtomicScience;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyStorage;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
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
import universalelectricity.core.item.RFItemHelper;
import universalelectricity.prefab.implement.IRotatable;

public class TChemicalExtractor
    extends TInventory implements ISidedInventory, IFluidHandler, IRotatable {
    public final int SMELTING_TICKS = 280;
    public static final int DIAN = 200;
    public int smeltingTicks = 0;
    public float rotation = 0.0F;
    public final FluidTank waterTank;
    private int playersUsing;

    public TChemicalExtractor() {
        super(DIAN,Integer.MAX_VALUE,Integer.MAX_VALUE);
        this.waterTank = new FluidTank(FluidRegistry.WATER, 0, 5000);
        this.playersUsing = 0;
    }


    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote && !this.isDisabled()) {
            if (super.containingItems[1] != null
                && FluidContainerRegistry.isFilledContainer(super.containingItems[1])) {
                FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(
                    super.containingItems[1]
                );
                if (liquid.getFluid() == FluidRegistry.WATER
                    && this.fill(ForgeDirection.UNKNOWN, liquid, false) > 0) {
                    ItemStack resultingContainer
                        = super.containingItems[1].getItem().getContainerItem(
                            super.containingItems[1]
                        );
                    if (resultingContainer == null
                        && super.containingItems[1].stackSize > 1) {
                        --super.containingItems[1].stackSize;
                    } else {
                        super.containingItems[1] = resultingContainer;
                    }

                    this.waterTank.fill(liquid, true);
                }
            }

            if (this.canWork()) {
                var toExtract = energyStorage.receiveEnergy(DIAN,true);
                energyStorage.receiveEnergy(RFItemHelper.extractEnergyFromContainer(containingItems[0],toExtract,false),false);
                int energy = energyStorage.getEnergyStored();
                //this.getClass();
                if (energy >= (DIAN)) {
                    if (this.smeltingTicks == 0) {
                        this.smeltingTicks = 280;
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

                    energyStorage.setEnergyStored(0);
                }
            } else {
                this.smeltingTicks = 0;
            }

            if (super.ticks % 10L == 0L && this.playersUsing > 0) {
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
        super.disabledTicks = nbt.getInteger("disabledTicks");
        energyStorage.readFromNBT(nbt);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("smeltingTicks", this.smeltingTicks);
        nbt.setInteger("waterAmount", this.waterTank.getFluidAmount());
        nbt.setInteger("disabledTicks", super.disabledTicks);
        energyStorage.writeToNBT(nbt);

        return new S35PacketUpdateTileEntity(
            this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), nbt
        );
    }

    @Override
    public void openInventory() {
        if (!this.worldObj.isRemote) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }

        ++this.playersUsing;
    }

    @Override
    public void closeInventory() {
        --this.playersUsing;
    }

    public boolean canWork() {
        if (!this.isDisabled() && this.waterTank.getFluid() != null
            && this.waterTank.getFluidAmount() >= 1000) {
            if (AtomicScience.isUraniumOre(super.containingItems[3])
                && this.isItemValidForSlot(
                    2, new ItemStack(AtomicScience.itYellowcake)
                )) {
                return true;
            }

            if (AtomicScience.isCell(super.containingItems[3])
                && this.isItemValidForSlot(
                    2, new ItemStack(AtomicScience.itCellDeuterium)
                )) {
                return true;
            }
        }

        return false;
    }

    public void work() {
        if (this.canWork()) {
            this.waterTank.drain(1000, true);
            if (AtomicScience.isUraniumOre(super.containingItems[3])) {
                this.incrStackSize(2, new ItemStack(AtomicScience.itYellowcake, 3));
            } else {
                this.incrStackSize(2, new ItemStack(AtomicScience.itCellDeuterium));
            }

            this.decrStackSize(3, 1);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.smeltingTicks = nbt.getInteger("shiJian");
        NBTTagCompound compound = nbt.getCompoundTag("water");
        this.waterTank.setFluid(FluidStack.loadFluidStackFromNBT(compound));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("shiJian", this.smeltingTicks);
        if (this.waterTank.getFluid() != null) {
            NBTTagCompound compound = new NBTTagCompound();
            this.waterTank.getFluid().writeToNBT(compound);
            nbt.setTag("water", compound);
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return FluidRegistry.WATER == resource.getFluid()
            ? this.waterTank.fill(resource, doFill)
            : 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
        return new FluidTankInfo[] { new FluidTankInfo(this.waterTank) };
    }

    @Override
    public int getSizeInventory() {
        return 4;
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
        if(slotID == 0 && this.getStackInSlot(0) == null){
            return itemStack.getItem() instanceof IEnergyContainerItem;
        }
        if (slotID == 2) {
            if (this.getStackInSlot(2) == null)
                return true;

            if (!this.getStackInSlot(2).isItemEqual(itemStack))
                return false;

            return this.getStackInSlot(2).stackSize + itemStack.stackSize
                <= itemStack.getMaxStackSize();
        }

        return slotID == 1 ? AtomicScience.isCellWater(itemStack)
                           : (slotID != 3 ? false
                                          : AtomicScience.isCell(itemStack)
                                      || AtomicScience.isUraniumOre(itemStack));
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 1, 2, 3 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack itemStack, int side) {
        return this.isItemValidForSlot(slotID, itemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, int side) {
        return slotID == 2;
    }

    @Override
    public boolean canDrain(ForgeDirection arg0, Fluid arg1) {
        return false;
    }

    @Override
    public boolean canFill(ForgeDirection arg0, Fluid arg1) {
        return arg1 == FluidRegistry.WATER
            && this.waterTank.getFluidAmount() < this.waterTank.getCapacity();
    }

    @Override
    public FluidStack drain(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
        return null;
    }

}
