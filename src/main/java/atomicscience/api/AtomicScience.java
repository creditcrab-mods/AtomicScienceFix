package atomicscience.api;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class AtomicScience {

   public static final String MAJOR_VERSION = "0";
   public static final String MINOR_VERSION = "6";
   public static final String REVISION_VERSION = "2";
   public static final String BUILD_VERSION = "117";
   public static final String VERSION = "0.6.2";
   public static final String NAME = "Atomic Science";
   public static final Configuration CONFIGURATION = new Configuration(new File(Loader.instance().getConfigDir(), "AtomicScience.cfg"));
}
