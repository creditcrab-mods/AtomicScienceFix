package atomicscience.fenlie;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItUranium extends ItRadioactive {
    public ItUranium(int icon) {
        super("uranium");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setTextureName("atomicscience:uranium");
    }

    @Override
    public void addInformation(
        ItemStack itemStack, EntityPlayer par2EntityPlayer, List list, boolean par4
    ) {
        if (itemStack.getItemDamage() > 0) {
            list.add("Breeding Uranium");
        } else {
            list.add("Enriched Uranium");
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return this.getUnlocalizedName() + "." + itemStack.getItemDamage();
    }

    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list) {
        list.add(new ItemStack(this, 1, 0));
        list.add(new ItemStack(this, 1, 1));
    }
}
