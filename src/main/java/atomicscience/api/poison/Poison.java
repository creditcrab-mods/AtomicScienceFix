package atomicscience.api.poison;

import atomicscience.api.IAntiPoisonArmor;
import java.util.EnumSet;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import universalelectricity.core.vector.Vector3;

public abstract class Poison {

    public static Poison[] list = new Poison[32];
    protected String name;
    protected EnumSet<ArmorType> armorRequired;

    public Poison(String name, int id) {
        this.armorRequired = EnumSet.range(ArmorType.HELM, ArmorType.BOOTS);
        this.name = name;
        if (list == null) {
            list = new Poison[32];
        }

        list[0] = this;
    }

    public String getName() {
        return this.name;
    }

    public EnumSet<ArmorType> getArmorRequired() {
        return this.armorRequired;
    }

    public void poisonEntity(Vector3 emitPosition, EntityLivingBase entity,
            int amplifier) {
        EnumSet<ArmorType> armorWorn = EnumSet.of(ArmorType.UNKNOWN);
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) entity;

            for (int i = 0; i < entityPlayer.inventory.armorInventory.length; ++i) {
                if (entityPlayer.inventory.armorInventory[i] != null &&
                        entityPlayer.inventory.armorInventory[i].getItem() instanceof IAntiPoisonArmor &&
                        ((IAntiPoisonArmor) entityPlayer.inventory.armorInventory[i]
                                .getItem())
                                .isProtectedFromPoison(entityPlayer.inventory.armorInventory[i],
                                        entity, this)) {
                    ((IAntiPoisonArmor) entityPlayer.inventory.armorInventory[i].getItem())
                            .onProtectFromPoison(entityPlayer.inventory.armorInventory[i],
                                    entity, this);
                    armorWorn.add(
                            ((IAntiPoisonArmor) entityPlayer.inventory.armorInventory[i]
                                    .getItem())
                                    .getArmorType());
                }
            }
        }

        if (!armorWorn.containsAll(this.armorRequired)) {
            this.doPoisonEntity(emitPosition, entity, armorWorn, amplifier);
        }
    }

    public void poisonEntity(Vector3 emitPosition, EntityLivingBase entity) {
        this.poisonEntity(emitPosition, entity, 0);
    }

    protected abstract void doPoisonEntity(Vector3 var1, EntityLivingBase var2,
                                           EnumSet<ArmorType> var3, int var4);

    public static enum ArmorType {
        HELM,
        BODY,
        LEGGINGS,
        BOOTS,
        UNKNOWN;
    }
}
