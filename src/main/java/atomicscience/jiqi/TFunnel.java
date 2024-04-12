package atomicscience.jiqi;

import atomicscience.AtomicScience;
import atomicscience.api.ISteamReceptor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import universalelectricity.core.block.IConductor;
import universalelectricity.prefab.tile.TileEntityAdvanced;

public class TFunnel extends TileEntityAdvanced implements ISteamReceptor, IFluidHandler {
    public IConductor connectedWire = null;
    public final FluidTank gasTank;

    public TFunnel() {
        this.gasTank = new FluidTank(AtomicScience.FLUID_STEAM, 0, 20000);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote && super.ticks % 20L == 0L) {
            TileEntity tileEntity
                = this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
            if (tileEntity instanceof ISteamReceptor) {
                FluidStack drainStack
                    = this.gasTank.drain(AtomicScience.STEAM_RATIO, true);
                if (drainStack != null) {
                    int nengLiang = drainStack.amount;
                    ((ISteamReceptor) tileEntity)
                        .onReceiveSteam(nengLiang - nengLiang / 4);
                }
            }

            if (tileEntity instanceof IFluidHandler) {
                IFluidHandler fHandler = ((IFluidHandler) tileEntity);

                int transferred
                    = fHandler.fill(ForgeDirection.DOWN, this.gasTank.getFluid(), true);

                if (transferred != 0)
                    this.gasTank.drain(transferred, true);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagCompound gasCompound = nbt.getCompoundTag("gas");
        this.gasTank.setFluid(FluidStack.loadFluidStackFromNBT(gasCompound));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (this.gasTank.getFluid() != null) {
            NBTTagCompound compound = new NBTTagCompound();
            this.gasTank.getFluid().writeToNBT(compound);
            nbt.setTag("gas", compound);
        }
    }

    @Override
    public void onReceiveSteam(int amount) {
        this.gasTank.fill(
            new FluidStack(AtomicScience.FLUID_STEAM, amount * AtomicScience.STEAM_RATIO),
            true
        );
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return this.gasTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canDrain(ForgeDirection arg0, Fluid arg1) {
        return arg1 == AtomicScience.FLUID_STEAM && this.gasTank.getFluidAmount() > 0;
    }

    @Override
    public boolean canFill(ForgeDirection arg0, Fluid arg1) {
        return false;
    }

    @Override
    public FluidStack drain(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
        return arg1.getFluid() == AtomicScience.FLUID_STEAM
            ? this.gasTank.drain(arg1.amount, arg2)
            : null;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
        return new FluidTankInfo[] { new FluidTankInfo(this.gasTank) };
    }
}
