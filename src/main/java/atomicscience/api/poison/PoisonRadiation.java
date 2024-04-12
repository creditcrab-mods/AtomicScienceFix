package atomicscience.api.poison;

import atomicscience.api.IAntiPoisonBlock;
import java.util.EnumSet;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.CustomDamageSource;
import universalelectricity.prefab.potion.CustomPotionEffect;

public class PoisonRadiation extends Poison {

    public static final Poison INSTANCE = new PoisonRadiation("radiation", 0);
    public static final CustomDamageSource damageSource = (CustomDamageSource) (new CustomDamageSource("radiation"))
            .setDamageBypassesArmor();
    public static boolean disabled = false;

    public PoisonRadiation(String name, int id) {
        super(name, id);
    }

    @Override
    protected void doPoisonEntity(Vector3 emitPosition, EntityLivingBase entity,
            EnumSet<ArmorType> armorWorn,
            int amplifier) {
        if (!disabled) {
            if (emitPosition == null) {
                entity.addPotionEffect(
                        new CustomPotionEffect(PotionRadiation.INSTANCE.getId(),
                                300 * (amplifier + 1), amplifier, null));
                return;
            }

            if (this.getAntiRadioactiveCount(entity.worldObj, emitPosition,
                    new Vector3(entity)) <= amplifier) {
                entity.addPotionEffect(
                        new CustomPotionEffect(PotionRadiation.INSTANCE.getId(),
                                400 * (amplifier + 1), amplifier, null));
            }
        }
    }

    public int getAntiRadioactiveCount(World world, Vector3 startingPosition,
            Vector3 endingPosition) {
        Vector3 delta = Vector3.subtract(endingPosition, startingPosition).normalize();
        Vector3 targetPosition = startingPosition.clone();
        double totalDistance = startingPosition.distanceTo(endingPosition);
        int count = 0;
        if (totalDistance > 1.0D) {
            for (; targetPosition.distanceTo(endingPosition) <= totalDistance; targetPosition.add(delta)) {
                Block block = targetPosition.getBlock(world);
                if (block != Blocks.air && block instanceof IAntiPoisonBlock &&
                        ((IAntiPoisonBlock) block)
                                .isPoisonPrevention(world, targetPosition.intX(),
                                        targetPosition.intY(),
                                        targetPosition.intZ(), this)) {
                    ++count;
                }
            }
        }

        return count;
    }
}
