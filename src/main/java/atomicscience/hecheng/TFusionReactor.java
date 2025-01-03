package atomicscience.hecheng;

import java.util.HashMap;

import atomicscience.AtomicScience;
import atomicscience.api.IElectromagnet;
import calclavia.lib.TileEntityUniversalRunnable;
import calclavia.lib.render.ITagRender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import universalelectricity.core.block.IElectricityStorage;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.tile.TileEntityRFUser;

public class TFusionReactor extends TileEntityRFUser
    implements IFluidHandler, ITagRender {
    public static final int YAO_DAO = 1000;
    public static final int RF_COST = 8000;
    public final FluidTank deuteriumTank
        = new FluidTank(AtomicScience.FLUID_DEUTERIUM, 0, 12800);
    public final FluidTank tritiumTank
        = new FluidTank(AtomicScience.FLUID_TRITIUM, 0, 12800);
    public float rotation = 0.0F;

    public TFusionReactor() {
        super(8000, Integer.MAX_VALUE, 8000);
    }


    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote) {
            if (!this.isDisabled() && super.energyStorage.getEnergyStored() >= RF_COST && this.hasFuel()
                && super.ticks % 20L == 0L) {
                for (int i = 2; i < 6; ++i) {
                    Vector3 diDian = new Vector3(this);
                    diDian.modifyPositionFromSide(ForgeDirection.getOrientation(i), 1.0D);
                    if (diDian.getBlock(worldObj) instanceof IElectromagnet) {
                        diDian.modifyPositionFromSide(
                            ForgeDirection.getOrientation(i), 1.0D
                        );
                        AtomicScience.bPlasma.spawn(
                            this.worldObj,
                            diDian.intX(),
                            diDian.intY(),
                            diDian.intZ(),
                            (byte) 7
                        );
                    }
                }

                this.energyStorage.extractEnergy(RF_COST,false);
                if (this.worldObj.rand.nextInt(10) == 0) {
                    deuteriumTank.drain(200, true);
                    if (AtomicScience.REQUIRE_TRITIUM) {
                        tritiumTank.drain(200, true);
                    }
                }
            }

            if (super.ticks % 80L == 0L) {
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        energyStorage.writeToNBT(nbt);
        nbt.setInteger("deuterium", this.deuteriumTank.getFluidAmount());
        nbt.setInteger("tritium", this.tritiumTank.getFluidAmount());

        return new S35PacketUpdateTileEntity(
            this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), nbt
        );
    }

    @Override
    public void onDataPacket(NetworkManager arg0, S35PacketUpdateTileEntity arg1) {
        NBTTagCompound nbt = arg1.func_148857_g();
        energyStorage.readFromNBT(nbt);
        this.deuteriumTank.setFluid(
            new FluidStack(AtomicScience.FLUID_DEUTERIUM, nbt.getInteger("deuterium"))
        );
        this.tritiumTank.setFluid(
            new FluidStack(AtomicScience.FLUID_TRITIUM, nbt.getInteger("tritium"))
        );
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.deuteriumTank.setFluid(
            new FluidStack(AtomicScience.FLUID_DEUTERIUM, nbt.getInteger("deuterium"))
        );
        this.tritiumTank.setFluid(
            new FluidStack(AtomicScience.FLUID_TRITIUM, nbt.getInteger("tritium"))
        );
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("deuterium", this.deuteriumTank.getFluidAmount());
        nbt.setInteger("tritium", this.tritiumTank.getFluidAmount());
    }


    @Override
    public float addInformation(HashMap map, EntityPlayer player) {
        if (this.deuteriumTank.getFluidAmount() > 0) {
            map.put(
                "Deuterium: " + this.deuteriumTank.getFluidAmount() + " mB",
                Integer.valueOf(16777215)
            );
        } else {
            map.put("No Deuterium", Integer.valueOf(16777215));
        }
        if (this.tritiumTank.getFluidAmount() > 0) {
            map.put(
                "Tritium: " + this.tritiumTank.getFluidAmount() + " mB",
                Integer.valueOf(16777215)
            );
        } else if (AtomicScience.REQUIRE_TRITIUM) {
            map.put("No Tritium", Integer.valueOf(16777215));
        }

        return 1.0F;
    }

    public boolean hasFuel() {
        return deuteriumTank.getFluidAmount() >= 200
            && (tritiumTank.getFluidAmount() >= 200 || !AtomicScience.REQUIRE_TRITIUM);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource.getFluid() == AtomicScience.FLUID_DEUTERIUM) {
            return deuteriumTank.fill(resource, doFill);
        } else if (resource.getFluid() == AtomicScience.FLUID_TRITIUM) {
            return tritiumTank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return (fluid == AtomicScience.FLUID_DEUTERIUM
                && deuteriumTank.getFluidAmount() < deuteriumTank.getCapacity())
            || (fluid == AtomicScience.FLUID_TRITIUM
                && tritiumTank.getFluidAmount() < tritiumTank.getCapacity());
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { new FluidTankInfo(this.deuteriumTank),
                                     new FluidTankInfo(this.tritiumTank) };
    }
}
