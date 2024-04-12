package atomicscience.api;

import atomicscience.api.poison.Poison;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IAntiPoisonArmor {

   boolean isProtectedFromPoison(ItemStack var1, EntityLivingBase var2, Poison var3);

   void onProtectFromPoison(ItemStack var1, EntityLivingBase var2, Poison var3);

   Poison.ArmorType getArmorType();
}
