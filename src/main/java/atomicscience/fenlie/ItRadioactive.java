package atomicscience.fenlie;

import atomicscience.api.poison.PoisonRadiation;
import atomicscience.wujian.ItAS;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

public class ItRadioactive extends ItAS {
    public ItRadioactive(String name) {
        super(name);
    }

    public void onUpdate(
        ItemStack par1ItemStack, World par2World, Entity entity, int par4, boolean par5
    ) {
        if (entity instanceof EntityLivingBase) {
            PoisonRadiation.INSTANCE.poisonEntity(
                new Vector3(entity), (EntityLivingBase) entity, 1
            );
        }
    }
}
