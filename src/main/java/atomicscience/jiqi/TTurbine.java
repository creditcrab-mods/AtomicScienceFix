package atomicscience.jiqi;

import java.util.ArrayList;
import java.util.Iterator;

import atomicscience.AtomicScience;
import atomicscience.SoundManager;
import atomicscience.api.ISteamReceptor;
import calclavia.lib.TileEntityUniversalProducer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import universalelectricity.core.vector.Vector3;

public class TTurbine
    extends TileEntityUniversalProducer implements ISteamReceptor, IFluidHandler {
    public float rotation = 0.0F;
    public float speed = 0.0F;
    public boolean isMultiblock = false;
    public Vector3 masterTurbine = null;
    public static final int BAN_JING = 1;
    // private static final double MAX_XUAN_ZHUAN = 50.0D;
    // private static final float ZHUAN_MAN = 0.5F;
    public final FluidTank tank;

    public TTurbine() {
        this.tank = new FluidTank(AtomicScience.FLUID_STEAM, 0, 20000);
    }

    @Override
    public boolean canConnect(ForgeDirection direction) {
        return this.masterTurbine != null ? false : direction == ForgeDirection.UP;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (this.tank.getFluid() != null
            && this.tank.getFluidAmount() > AtomicScience.STEAM_RATIO
            && !this.isMultiblock) {
            this.onReceiveSteam(1);
            this.tank.drain(AtomicScience.STEAM_RATIO, true);
        } else if (this.tank.getFluid() != null && this.tank.getFluidAmount() > AtomicScience.STEAM_RATIO * 9 && this.isMultiblock) {
            this.onReceiveSteam(9);
            this.tank.drain(AtomicScience.STEAM_RATIO * 9, true);
        }

        if (this.masterTurbine != null) {
            TileEntity tileEntity = this.masterTurbine.getTileEntity(this.worldObj);
            if (tileEntity == null) {
                this.masterTurbine = null;
            } else if (!tileEntity.isInvalid() && tileEntity instanceof TTurbine) {
                if (!((TTurbine) tileEntity).isMultiblock) {
                    this.masterTurbine = null;
                }
            } else {
                this.masterTurbine = null;
            }
        } else {
            if (this.speed > 0.0F && !this.isDisabled()) {
                if (super.ticks % 18L == 0L) {
                    if (this.isMultiblock) {
                        this.worldObj.playSoundEffect(
                            (double) this.xCoord,
                            (double) this.yCoord,
                            (double) this.zCoord,
                            SoundManager.TURBINE,
                            0.6F,
                            (float
                            ) (0.699999988079071D + 0.2D * ((double) this.speed / 450.0D))
                        );
                    } else {
                        this.worldObj.playSoundEffect(
                            (double) this.xCoord,
                            (double) this.yCoord,
                            (double) this.zCoord,
                            SoundManager.TURBINE,
                            0.15F,
                            (float
                            ) (0.699999988079071D + 0.2D * ((double) this.speed / 50.0D))
                        );
                    }
                }

                // If turbine doesn't spin, check packet handler
                // Original packet handler added to rotation on packet received.
                // - LordMZTE
                if (this.isMultiblock) {
                    this.rotation += this.speed / 18.0F / 3.0F;
                } else {
                    this.rotation += this.speed / 3.0F;
                }

                if (!this.worldObj.isRemote && super.ticks % 3L == 0L) {
                    this.worldObj.markBlockForUpdate(
                        this.xCoord, this.yCoord, this.zCoord
                    );
                }

                if (this.rotation > 360.0F) {
                    this.rotation = 0.0F;
                }
            }

            this.produce((double) (this.speed * AtomicScience.WOLUN_MULTIPLIER_OUTPUT));
            if (this.isMultiblock) {
                this.speed = (float
                ) Math.max(Math.min((double) (this.speed - 4.5F), 450.0D), 0.0D);
            } else {
                this.speed = (float
                ) Math.max(Math.min((double) (this.speed - 0.5F), 50.0D), 0.0D);
            }
        }
    }

    public void onReceiveSteam(int amount) {
        if (this.masterTurbine != null) {
            TileEntity tileEntity = this.masterTurbine.getTileEntity(this.worldObj);
            if (tileEntity != null && tileEntity instanceof TTurbine) {
                ((TTurbine) tileEntity).onReceiveSteam(amount);
            }
        } else if (this.isMultiblock) {
            this.speed = (float) Math.min((double) (this.speed + (float) amount), 450.0D);
        } else {
            this.speed = (float) Math.min((double) (this.speed + (float) amount), 50.0D);
        }
    }

    @Override
    public void onDataPacket(NetworkManager arg0, S35PacketUpdateTileEntity arg1) {
        NBTTagCompound nbt = arg1.func_148857_g();
        this.isMultiblock = nbt.getBoolean("isMultiblock");
        if (nbt.getTag("masterTurbine") != null)
            this.masterTurbine = Vector3.readFromNBT(nbt.getCompoundTag("masterTurbine"));
        this.speed = nbt.getFloat("speed");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("isMultiblock", this.isMultiblock);
        if (this.masterTurbine != null)
            nbt.setTag(
                "masterTurbine", this.masterTurbine.writeToNBT(new NBTTagCompound())
            );
        nbt.setFloat("speed", this.speed);

        return new S35PacketUpdateTileEntity(
            this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), nbt
        );
    }

    public void setMaster(TTurbine newMasterTurbine) {
        if (newMasterTurbine != null) {
            this.masterTurbine = new Vector3(newMasterTurbine);
        } else {
            this.masterTurbine = null;
            this.isMultiblock = false;
        }

        this.worldObj.notifyBlocksOfNeighborChange(
            this.xCoord, this.yCoord, this.zCoord, this.getBlockType()
        );
        if (!this.worldObj.isRemote) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    public boolean hasMaster() {
        return this.masterTurbine != null;
    }

    public boolean checkMultiblock() {
        if (!this.worldObj.isRemote) {
            ArrayList<TTurbine> xiaoWoLun = new ArrayList<>();
            ArrayList<TTurbine> lianJie = new ArrayList<>();

            for (int i$ = -1; i$ <= 1; ++i$) {
                for (int woLun = -1; woLun <= 1; ++woLun) {
                    TileEntity tileEntity = this.worldObj.getTileEntity(
                        this.xCoord + i$, this.yCoord, this.zCoord + woLun
                    );
                    if (tileEntity != null && tileEntity instanceof TTurbine) {
                        if (!((TTurbine) tileEntity).hasMaster()
                            && !((TTurbine) tileEntity).isMultiblock) {
                            xiaoWoLun.add((TTurbine) tileEntity);
                        }

                        lianJie.add((TTurbine) tileEntity);
                    }
                }
            }

            Iterator var6;
            TTurbine var7;
            if (this.isMultiblock) {
                var6 = lianJie.iterator();

                while (var6.hasNext()) {
                    var7 = (TTurbine) var6.next();
                    if (var7 != this) {
                        var7.setMaster((TTurbine) null);
                    }
                }

                this.isMultiblock = false;
            } else if (xiaoWoLun.size() >= 9) {
                var6 = xiaoWoLun.iterator();

                while (var6.hasNext()) {
                    var7 = (TTurbine) var6.next();
                    if (var7 != this) {
                        var7.setMaster(this);
                    }
                }

                this.masterTurbine = null;
                this.isMultiblock = true;
            }

            if (!this.worldObj.isRemote) {
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
        }

        return true;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.speed = nbt.getFloat("speed");
        this.isMultiblock = nbt.getBoolean("isMultiblock");
        if (nbt.hasKey("masterTurbine")) {
            this.masterTurbine = Vector3.readFromNBT(nbt.getCompoundTag("masterTurbine"));
        } else {
            this.masterTurbine = null;
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setFloat("speed", this.speed);
        nbt.setBoolean("isMultiblock", this.isMultiblock);
        if (this.masterTurbine != null) {
            nbt.setTag(
                "masterTurbine", this.masterTurbine.writeToNBT(new NBTTagCompound())
            );
        }
    }

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return this.masterTurbine == null ? this.tank.fill(resource, doFill) : 0;
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    @Override
    public boolean canDrain(ForgeDirection arg0, Fluid arg1) {
        return false;
    }

    @Override
    public boolean canFill(ForgeDirection arg0, Fluid arg1) {
        return arg1 == AtomicScience.FLUID_STEAM
            && this.tank.getFluidAmount() < this.tank.getCapacity()
            && this.masterTurbine == null;
    }

    @Override
    public FluidStack drain(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
        return null;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
        return new FluidTankInfo[] { new FluidTankInfo(this.tank) };
    }
}
