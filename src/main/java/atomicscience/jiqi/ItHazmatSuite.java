package atomicscience.jiqi;

import atomicscience.TabAS;
import atomicscience.api.IAntiPoisonArmor;
import atomicscience.api.poison.Poison;
import atomicscience.api.poison.PoisonRadiation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItHazmatSuite extends ItemArmor implements IAntiPoisonArmor {
    public ItHazmatSuite(ArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
        super(par2EnumArmorMaterial, par3, par4);
        this.setCreativeTab(TabAS.INSTANCE);
        this.setMaxDamage(200000);
        switch (par4) {
            case 0:
                this.setTextureName("atomicscience:hazmatMask");
                break;
            case 1:
                this.setTextureName("atomicscience:hazmatBody");
                break;
            case 2:
                this.setTextureName("atomicscience:hazmatLeggings");
                break;
            case 3:
                this.setTextureName("atomicscience:hazmatBoots");
                break;

            default:
                break;
        }
    }

    @Override
    public String
    getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
        return "atomicscience:textures/models/hazmat.png";
    }

    @Override
    public boolean isProtectedFromPoison(
        ItemStack itemStack, EntityLivingBase entityLiving, Poison type
    ) {
        return type == PoisonRadiation.INSTANCE
            || type.getName().equalsIgnoreCase("chemical")
            || type.getName().equalsIgnoreCase("contagious");
    }

    @Override
    public void
    onProtectFromPoison(ItemStack itemStack, EntityLivingBase entityLiving, Poison type) {
        itemStack.damageItem(1, entityLiving);
    }

    @Override
    public Poison.ArmorType getArmorType() {
        return Poison.ArmorType.values()[super.armorType];
    }
}
