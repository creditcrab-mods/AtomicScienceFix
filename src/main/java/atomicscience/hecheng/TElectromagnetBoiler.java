package atomicscience.hecheng;

import atomicscience.AtomicScience;
import atomicscience.api.ITemperature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.tile.TileEntityAdvanced;

public class TElectromagnetBoiler extends TileEntityAdvanced implements ITemperature {
    public static final int MAX_TEMP = 5000;
    private float temperature = 0.0F;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote && this.temperature > 0.0F) {
            if (this.temperature > 100.0F) {
                for (int i = 0; i < 6; ++i) {
                    Vector3 offsetPos = new Vector3(this);
                    offsetPos.modifyPositionFromSide(ForgeDirection.getOrientation(i));
                    Block blockID = this.worldObj.getBlock(
                        offsetPos.intX(), offsetPos.intY(), offsetPos.intZ()
                    );
                    if (blockID != Blocks.water && blockID != Blocks.flowing_water) {
                        if (blockID == Blocks.grass) {
                            offsetPos.setBlock(
                                this.worldObj, AtomicScience.blockRadioactive
                            );
                        }
                    } else if (super.ticks % (long) ((int) Math.max(40.0F - this.temperature / 5000.0F * 40.0F, 2.0F)) == 0L) {
                        AtomicScience.boilWater(this.worldObj, offsetPos, 2, 20);
                    }
                }
            }

            this.temperature = Math.max(Math.min(this.temperature - 0.8F, 5000.0F), 0.0F);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.temperature = nbt.getFloat("temperature");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setFloat("temperature", this.temperature);
    }

    @Override
    public float getTemperature() {
        return this.temperature;
    }

    @Override
    public void setTemperature(float celsius) {
        this.temperature = celsius;
    }
}
