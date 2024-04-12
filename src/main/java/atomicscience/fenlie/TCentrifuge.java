package atomicscience.fenlie;

import atomicscience.AtomicScience;
import atomicscience.jiqi.TInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.item.ElectricItemHelper;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;
import universalelectricity.prefab.implement.IRotatable;

public class TCentrifuge
    extends TInventory implements ISidedInventory, IFluidHandler, IRotatable {
    public static final int SHI_JIAN = 2400;
    public static final float DIAN = 500.0F;
    public int smeltingTicks = 0;
    public float xuanZhuan = 0.0F;
    public final FluidTank gasTank;

    public TCentrifuge() {
        this.gasTank = new FluidTank(AtomicScience.FLUID_URANIUM_HEXAFLOURIDE, 0, 5000);
    }

    @Override
    public ElectricityPack getRequest() {
        return this.canWork()
            ? new ElectricityPack(500.0D / this.getVoltage(), this.getVoltage())
            : new ElectricityPack();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote && !this.isDisabled()) {
            if (super.ticks % 20L == 0L) {
                for (int i$ = 0; i$ < 6; ++i$) {
                    ForgeDirection direction = ForgeDirection.getOrientation(i$);
                    TileEntity tileEntity = VectorHelper.getTileEntityFromSide(
                        this.worldObj, new Vector3(this), direction
                    );
                    if (tileEntity instanceof IFluidHandler
                        && tileEntity.getClass() != this.getClass()) {
                        int requiredLiquid
                            = this.gasTank.getCapacity() - this.gasTank.getFluidAmount();
                        FluidStack drained
                            = ((IFluidHandler) tileEntity)
                                  .drain(
                                      direction.getOpposite(),
                                      new FluidStack(
                                          AtomicScience.FLUID_URANIUM_HEXAFLOURIDE,
                                          requiredLiquid
                                      ),
                                      true
                                  );
                        this.gasTank.fill(drained, true);
                    }
                }
            }

            if (this.canWork()) {
                super.wattsReceived += ElectricItemHelper.dechargeItem(
                    super.containingItems[0], 500.0D, this.getVoltage()
                );
                if (super.wattsReceived >= 500.0D) {
                    if (this.smeltingTicks == 0) {
                        this.smeltingTicks = 2400;
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
    public void onDataPacket(NetworkManager nm, S35PacketUpdateTileEntity packet) {
        NBTTagCompound nbt = packet.func_148857_g();
        this.smeltingTicks = nbt.getInteger("shiJian");
        this.gasTank.setFluid(new FluidStack(
            AtomicScience.FLUID_URANIUM_HEXAFLOURIDE, nbt.getInteger("fluidAmount")
        ));
        super.disabledTicks = nbt.getInteger("disabledTicks");
    }

    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger("shiJian", this.smeltingTicks);
        nbt.setInteger("fluidAmount", this.gasTank.getFluidAmount());
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

    @Override
    public void closeInventory() {}

    public boolean canWork() {
        return !this.isDisabled() && this.gasTank.getFluidAmount() != 0
            && this.gasTank.getFluidAmount() >= AtomicScience.URANIUM_HEXAFLOURIDE_RATIO;
    }

    public void work() {
        if (this.canWork()) {
            this.gasTank.drain(AtomicScience.URANIUM_HEXAFLOURIDE_RATIO, true);
            if ((double) this.worldObj.rand.nextFloat() > 0.7D) {
                this.incrStackSize(2, new ItemStack(AtomicScience.itUranium));
            } else {
                this.incrStackSize(3, new ItemStack(AtomicScience.itUranium, 1, 1));
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.smeltingTicks = nbt.getInteger("smeltingTicks");
        NBTTagCompound gasNbt = nbt.getCompoundTag("gas");
        this.gasTank.setFluid(FluidStack.loadFluidStackFromNBT(gasNbt));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("smeltingTicks", this.smeltingTicks);
        NBTTagCompound gasNbt = new NBTTagCompound();
        if (this.gasTank.getFluid() != null)
            this.gasTank.getFluid().writeToNBT(gasNbt);
        nbt.setTag("gas", gasNbt);
    }

    @Override
    public int getSizeInventory() {
        return 4;
    }

    @Override
    public boolean canFill(ForgeDirection arg0, Fluid fluid) {
        return AtomicScience.FLUID_URANIUM_HEXAFLOURIDE == fluid
            && this.gasTank.getCapacity() != this.gasTank.getFluidAmount();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return AtomicScience.FLUID_URANIUM_HEXAFLOURIDE == resource.getFluid()
            ? this.gasTank.fill(resource, doFill)
            : 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
        return null;
    }

    @Override
    public boolean canDrain(ForgeDirection arg0, Fluid arg1) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
        return new FluidTankInfo[] { new FluidTankInfo(this.gasTank) };
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 0 ? new int[] { 2, 3 } : new int[] { 0, 1 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack itemStack, int side) {
        return slotID == 1 && this.isItemValidForSlot(slotID, itemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, int j) {
        return slotID == 2 || slotID == 3;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        switch (i) {
            case 0:
                return itemStack.getItem() instanceof IItemElectric;
            case 1:
                return true;
            case 2:
                return itemStack.getItem() == AtomicScience.itUranium;
            case 3:
                return itemStack.getItem() == AtomicScience.itUranium;
            default:
                return false;
        }
    }
}
