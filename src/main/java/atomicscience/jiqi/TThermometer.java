package atomicscience.jiqi;

import atomicscience.AtomicScience;
import atomicscience.api.ITemperature;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.tile.TileEntityDisableable;

public class TThermometer extends TileEntityDisableable {
    public static final int MAX_TEMP = 5000;
    private int warningTemp = 2000;
    private float prevTemp;
    public float temp = 0.0F;
    public Vector3 linkedTileCoords;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote) {
            this.prevTemp = this.temp;
            if (super.ticks % 10L == 0L) {
                this.temp = 0.0F;
                if (this.linkedTileCoords != null
                    && this.linkedTileCoords.getTileEntity(this.worldObj)
                            instanceof ITemperature) {
                    this.temp = ((ITemperature
                                 ) this.linkedTileCoords.getTileEntity(this.worldObj))
                                    .getTemperature();
                } else {
                    for (int i = 0; i < 6; ++i) {
                        Vector3 offsetPos = new Vector3(this);
                        offsetPos.modifyPositionFromSide(ForgeDirection.getOrientation(i)
                        );
                        Block fangGe = offsetPos.getBlock(this.worldObj);
                        if (fangGe instanceof ITemperature) {
                            this.temp += ((ITemperature) fangGe).getTemperature();
                        } else {
                            TileEntity tileEntity
                                = offsetPos.getTileEntity(this.worldObj);
                            if (tileEntity != null
                                && tileEntity instanceof ITemperature) {
                                this.temp += ((ITemperature) tileEntity).getTemperature();
                            }
                        }
                    }
                }

                this.worldObj.notifyBlocksOfNeighborChange(
                    this.xCoord, this.yCoord, this.zCoord, AtomicScience.bThermometer
                );
                if (this.prevTemp != this.temp) {
                    this.worldObj.markBlockForUpdate(
                        this.xCoord, this.yCoord, this.zCoord
                    );
                }
            }
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setFloat("temp", this.temp);
        nbt.setInteger("warningTemp", this.warningTemp);

        return new S35PacketUpdateTileEntity(
            this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), nbt
        );
    }

    @Override
    public void onDataPacket(NetworkManager arg0, S35PacketUpdateTileEntity arg1) {
        NBTTagCompound nbt = arg1.func_148857_g();
        this.temp = nbt.getFloat("temp");
        this.warningTemp = nbt.getInteger("warningTemp");
    }

    public boolean setLinkTile(Vector3 linkedTileCoords) {
        TileEntity thermalTile = linkedTileCoords.getTileEntity(this.worldObj);
        if (thermalTile instanceof ITemperature) {
            this.linkedTileCoords = linkedTileCoords;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.warningTemp = nbt.getInteger("warningTemp");
        this.linkedTileCoords
            = Vector3.readFromNBT(nbt.getCompoundTag("linkedTileCoords"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("warningTemp", this.warningTemp);
        if (this.linkedTileCoords != null) {
            nbt.setTag(
                "linkedTileCoords", this.linkedTileCoords.writeToNBT(new NBTTagCompound())
            );
        }
    }

    public int getWarningTemp() {
        return this.warningTemp;
    }

    public void setWarningTemp(int warningTemp) {
        this.warningTemp = warningTemp;
        if (this.warningTemp <= 0) {
            this.warningTemp = 5000;
        }

        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    public boolean shouldEmitRedstone() {
        return !this.isDisabled() && this.temp >= (float) this.getWarningTemp();
    }
}
