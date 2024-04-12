package atomicscience.jiqi;

import java.util.List;

import atomicscience.api.ITemperature;
import atomicscience.wujian.ItElectricAS;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.vector.Vector3;

public class ItThermometer extends ItElectricAS {
    public static final int JOULES_CONSUMPTION = 1000;

    public ItThermometer(int texture) {
        super("thermometer");
        this.setTextureName("atomicscience:thermometer");
    }

    @Override
    public void addInformation(
        ItemStack itemStack, EntityPlayer player, List par3List, boolean par4
    ) {
        super.addInformation(itemStack, player, par3List, par4);
        Vector3 coord = this.getSavedCoord(itemStack);
        TileEntity tileEntity = coord.getTileEntity(player.worldObj);
        if (tileEntity instanceof ITemperature) {
            par3List.add("Tracking Thermal Device:");
            par3List.add(
                "X: " + coord.intX() + ", Y: " + coord.intY() + ", Z: " + coord.intZ()
            );
            par3List.add(
                Math.round(((ITemperature) tileEntity).getTemperature()) + " Celsius"
            );
        } else {
            par3List.add("§4Invalid Thermal Device.");
        }
    }

    public void setSavedCoords(ItemStack itemStack, Vector3 position) {
        if (itemStack.stackTagCompound == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        itemStack.stackTagCompound.setInteger("x", position.intX());
        itemStack.stackTagCompound.setInteger("y", position.intY());
        itemStack.stackTagCompound.setInteger("z", position.intZ());
    }

    public Vector3 getSavedCoord(ItemStack par1ItemStack) {
        return par1ItemStack.stackTagCompound == null
            ? new Vector3()
            : new Vector3(
                (double) par1ItemStack.stackTagCompound.getInteger("x"),
                (double) par1ItemStack.stackTagCompound.getInteger("y"),
                (double) par1ItemStack.stackTagCompound.getInteger("z")
            );
    }

    @Override
    public boolean onItemUse(
        ItemStack itemStack,
        EntityPlayer par2EntityPlayer,
        World par3World,
        int x,
        int y,
        int z,
        int par7,
        float par8,
        float par9,
        float par10
    ) {
        if (!par3World.isRemote) {
            if (this.getJoules(itemStack) > 0.0D) {
                TileEntity tileEntity = par3World.getTileEntity(x, y, z);
                if (tileEntity instanceof ITemperature) {
                    if (par2EntityPlayer.isSneaking()) {
                        this.setSavedCoords(itemStack, new Vector3(tileEntity));
                        par2EntityPlayer.addChatComponentMessage(
                            new ChatComponentText("Tracking Thermal Device.")
                        );
                    } else {
                        par2EntityPlayer.addChatComponentMessage(new ChatComponentText(
                            "Heat: "
                            + Math.round(((ITemperature) tileEntity).getTemperature())
                            + " C"
                        ));
                    }

                    this.onProvide(
                        ElectricityPack.getFromWatts(1000.0D, this.getVoltage(itemStack)),
                        itemStack
                    );
                }
            } else {
                par2EntityPlayer.addChatComponentMessage(
                    new ChatComponentText("No Electricity!")
                );
            }
        }

        return false;
    }

    @Override
    public double getMaxJoules(ItemStack itemStack) {
        return 50000.0D;
    }

    @Override
    public double getVoltage(ItemStack itemStack) {
        return 20.0D;
    }
}
