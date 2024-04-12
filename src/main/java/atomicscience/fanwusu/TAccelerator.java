package atomicscience.fanwusu;

import atomicscience.AtomicScience;
import atomicscience.SoundManager;
import atomicscience.jiqi.TInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.implement.IRotatable;

public class TAccelerator extends TInventory implements IRotatable, ISidedInventory {
    public final int DIAN = 10000;
    public double yongDianLiang = 0.0D;
    public int antimatter;
    public EMatter wuSu;
    public static final float SU_DU = 1.0F;
    public float suDu;

    @Override
    public boolean canConnect(ForgeDirection direction) {
        return true;
    }

    @Override
    public ElectricityPack getRequest() {
        return this.getStackInSlot(0) != null
                && (this.worldObj.isBlockIndirectlyGettingPowered(
                        this.xCoord, this.yCoord, this.zCoord
                    )
                    || this.worldObj.getBlockPowerInput(
                           this.xCoord, this.yCoord, this.zCoord
                       ) > 0)
            ? new ElectricityPack(10000.0D / this.getVoltage(), this.getVoltage())
            : new ElectricityPack();
    }

    @Override
    public void onReceive(ElectricityPack electricityPack) {
        super.onReceive(electricityPack);
        this.yongDianLiang += electricityPack.getWatts();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote) {
            this.suDu = 0.0F;
            if (this.wuSu != null) {
                this.suDu = (float) this.wuSu.getSuDu();
            }

            if (AtomicScience.isCell(this.getStackInSlot(1))
                && this.getStackInSlot(1).stackSize > 0 && this.antimatter >= 125) {
                if (this.getStackInSlot(2) != null) {
                    if (this.getStackInSlot(2).getItem()
                        == AtomicScience.itCellAntimatter) {
                        ItemStack i$ = this.getStackInSlot(2).copy();
                        if (i$.stackSize < i$.getMaxStackSize()) {
                            this.decrStackSize(1, 1);
                            this.antimatter -= 125;
                            ++i$.stackSize;
                            this.setInventorySlotContents(2, i$);
                        }
                    }
                } else {
                    this.antimatter -= 125;
                    this.decrStackSize(1, 1);
                    this.setInventorySlotContents(
                        2, new ItemStack(AtomicScience.itCellAntimatter)
                    );
                }
            }

            if (!this.isDisabled()) {
                if (this.worldObj.isBlockIndirectlyGettingPowered(
                        this.xCoord, this.yCoord, this.zCoord
                    )) {
                    double var10000 = super.wattsReceived;
                    this.getClass();
                    if (var10000 >= 10000.0D) {
                        if (this.wuSu == null) {
                            if (this.getStackInSlot(0) != null
                                && super.ticks % 20L == 0L) {
                                Vector3 i$1 = new Vector3(this);
                                i$1.modifyPositionFromSide(this.getDirection(
                                                                   this.worldObj,
                                                                   this.xCoord,
                                                                   this.yCoord,
                                                                   this.zCoord
                                )
                                                               .getOpposite());
                                i$1.add(0.5D);
                                if (EMatter.canExist(
                                        this.worldObj,
                                        i$1,
                                        this.getDirection(
                                                this.worldObj,
                                                this.xCoord,
                                                this.yCoord,
                                                this.zCoord
                                        )
                                            .getOpposite()
                                    )) {
                                    this.yongDianLiang = 0.0D;
                                    this.wuSu = new EMatter(
                                        this.worldObj,
                                        i$1,
                                        new Vector3(this),
                                        this.getDirection(
                                                this.worldObj,
                                                this.xCoord,
                                                this.yCoord,
                                                this.zCoord
                                        )
                                            .getOpposite()
                                    );
                                    this.worldObj.spawnEntityInWorld(this.wuSu);
                                    this.decrStackSize(0, 1);
                                }
                            }
                        } else if (this.wuSu.isDead) {
                            this.wuSu = null;
                        } else if (this.suDu > 1.0F) {
                            this.worldObj.playSoundEffect(
                                (double) this.xCoord,
                                (double) this.yCoord,
                                (double) this.zCoord,
                                SoundManager.ANTIMATTER,
                                2.0F,
                                1.0F - this.worldObj.rand.nextFloat() * 0.3F
                            );
                            this.antimatter += 5 + this.worldObj.rand.nextInt(5);
                            this.yongDianLiang = 0.0D;
                            this.wuSu.setDead();
                            this.wuSu = null;
                        }

                        double var10001 = super.wattsReceived;
                        this.getClass();
                        super.wattsReceived = Math.max(var10001 - 10000.0D / 10.0D, 0.0D);
                    } else {
                        if (this.wuSu != null) {
                            this.wuSu.setDead();
                        }

                        this.wuSu = null;
                    }
                } else {
                    if (this.wuSu != null) {
                        this.wuSu.setDead();
                    }

                    this.wuSu = null;
                }
            }

            if (super.ticks % 5L == 0L) {
                worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
        }
    }

    @Override
    public void onDataPacket(NetworkManager nm, S35PacketUpdateTileEntity packet) {
        if (!this.worldObj.isRemote)
            return;

        NBTTagCompound nbt = packet.func_148857_g();
        super.disabledTicks = nbt.getInteger("disabledTicks");
        this.suDu = nbt.getFloat("suDu");
        this.yongDianLiang = nbt.getDouble("yongDianLiang");
        this.antimatter = nbt.getInteger("antimatter");
    }

    // public void handlePacketData(INetworkManager network, int packetType,
    // Packet250CustomPayload packet,
    // EntityPlayer player,
    // ByteArrayDataInput dataStream) {
    // try {
    // super.disabledTicks = dataStream.readInt();
    // this.suDu = dataStream.readFloat();
    // this.yongDianLiang = dataStream.readDouble();
    // this.fanWuSu = dataStream.readInt();
    // } catch (Exception var7) {
    // var7.printStackTrace();
    // }
    // }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("disabledTicks", super.disabledTicks);
        nbt.setFloat("suDu", this.suDu);
        nbt.setDouble("yongDianLiang", this.yongDianLiang);
        nbt.setInteger("antimatter", this.antimatter);

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
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        super.wattsReceived = nbt.getDouble("wattsReceived");
        this.yongDianLiang = nbt.getDouble("yongDianLiang");
        this.antimatter = nbt.getInteger("antimatter");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setDouble("wattsReceived", super.wattsReceived);
        nbt.setDouble("yongDianLiang", this.yongDianLiang);
        nbt.setInteger("antimatter", this.antimatter);
    }

    @Override
    public int getSizeInventory() {
        return 3;
    }

    @Override
    public double getVoltage() {
        return UniversalElectricity.isVoltageSensitive ? 480.0D : 120.0D;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 0, 1, 2 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack itemStack, int j) {
        return this.isItemValidForSlot(slotID, itemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, int j) {
        return slotID == 2;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        switch (i) {
            case 0:
                return true;
            case 1:
                return AtomicScience.isCell(itemStack);
            case 2:
                return itemStack.getItem() instanceof ItAntimatterCell;
            default:
                return false;
        }
    }
}
