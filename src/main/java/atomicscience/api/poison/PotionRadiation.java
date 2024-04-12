package atomicscience.api.poison;

import atomicscience.api.AtomicScience;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import universalelectricity.prefab.potion.CustomPotion;

public class PotionRadiation extends CustomPotion {
    public static PotionRadiation INSTANCE;

    public PotionRadiation(int id, boolean isBadEffect, int color, String name) {
        super( id, isBadEffect, color, name);
        this.setIconIndex(6, 0);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if ((double) entity.worldObj.rand.nextFloat() > 0.9D - (double) amplifier * 0.08D) {
            entity.attackEntityFrom(PoisonRadiation.damageSource, 1);
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer) entity)
                        .addExhaustion(0.01F * (float) (amplifier + 1));
            }
        }
    }

    public boolean isReady(int duration, int amplifier) {
        return duration % 10 == 0;
    }
}
