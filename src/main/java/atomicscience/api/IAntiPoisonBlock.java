package atomicscience.api;

import atomicscience.api.poison.Poison;
import net.minecraft.world.World;

public interface IAntiPoisonBlock {

   boolean isPoisonPrevention(World var1, int var2, int var3, int var4, Poison var5);
}
