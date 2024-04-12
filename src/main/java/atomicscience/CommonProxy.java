package atomicscience;

import atomicscience.fanwusu.CAccelerator;
import atomicscience.fanwusu.TAccelerator;
import atomicscience.fenlie.CCentrifuge;
import atomicscience.fenlie.TCentrifuge;
import atomicscience.fenlie.TFissionReactor;
import atomicscience.fenlie.TNuclearBoiler;
import atomicscience.hecheng.CChemicalExtractor;
import atomicscience.hecheng.CNuclearBoiler;
import atomicscience.jiqi.CAssembler;
import atomicscience.jiqi.TChemicalExtractor;
import atomicscience.shimian.CFissionReactor;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {
    public void preInit() {}

    public void init() {}

    public int getArmorIndex(String armor) {
        return 0;
    }

    public Object
    getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public Object
    getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && ID < CommonProxy.GuiType.values().length) {
            if (tileEntity instanceof TCentrifuge) {
                return new CCentrifuge(player.inventory, (TCentrifuge) tileEntity);
            }

            if (tileEntity instanceof TChemicalExtractor) {
                return new CChemicalExtractor(
                    player.inventory, (TChemicalExtractor) tileEntity
                );
            }

            if (tileEntity instanceof TAccelerator) {
                return new CAccelerator(player.inventory, (TAccelerator) tileEntity);
            }

            if (tileEntity instanceof TAtomicAssembler) {
                return new CAssembler(player.inventory, (TAtomicAssembler) tileEntity);
            }

            if (tileEntity instanceof TNuclearBoiler) {
                return new CNuclearBoiler(player.inventory, (TNuclearBoiler) tileEntity);
            }

            if (tileEntity instanceof TFissionReactor) {
                return new CFissionReactor(player, (TFissionReactor) tileEntity);
            }
        }

        return null;
    }

    public static enum GuiType {
        CENTRIFUGE,
        CHEMICAL_EXTRACTOR,
        ACCELERATOR,
        ATOMIC_ASSEMBLER,
        HE_QI;
    }
}
