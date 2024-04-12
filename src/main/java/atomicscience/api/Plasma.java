package atomicscience.api;

import net.minecraft.world.World;

public class Plasma {

   public static IPlasma blockPlasma;



   public interface IPlasma {

      void spawn(World var1, int var2, int var3, int var4, byte var5);

      boolean canPlace(World var1, int var2, int var3, int var4);
   }
}
