package atomicscience.hecheng;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class IBElectromagnet extends ItemBlock {
    public IBElectromagnet(Block block) {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void
    addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        if (this.field_150939_a instanceof BElectromagnetBoiler) {
            list.add("Used for Fusion Reactors");
        } else {
            list.add("Used for Particle Accelerators");
        }
    }
}
