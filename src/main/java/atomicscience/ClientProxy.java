package atomicscience;

import atomicscience.fanwusu.EMatter;
import atomicscience.fanwusu.TAccelerator;
import atomicscience.fenlie.TCentrifuge;
import atomicscience.fenlie.TFissionReactor;
import atomicscience.fenlie.TNuclearBoiler;
import atomicscience.hecheng.TFusionReactor;
import atomicscience.jiqi.TChemicalExtractor;
import atomicscience.jiqi.TThermometer;
import atomicscience.jiqi.TTurbine;
import atomicscience.render.RAtomicAssembler;
import atomicscience.render.RCentrifuge;
import atomicscience.render.RExtractor;
import atomicscience.render.RFusionReactor;
import atomicscience.render.RH;
import atomicscience.render.RMatter;
import atomicscience.render.RNuclearBoiler;
import atomicscience.render.RReactorCell;
import atomicscience.render.RThermometer;
import atomicscience.render.RTurbine;
import atomicscience.shimian.GAccelerator;
import atomicscience.shimian.GAtomicAssembler;
import atomicscience.shimian.GAutoBuilder;
import atomicscience.shimian.GCentrifuge;
import atomicscience.shimian.GChemicalExtractor;
import atomicscience.shimian.GFissionReactor;
import atomicscience.shimian.GNuclearBoiler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {
    public void preInit() {
        RenderingRegistry.registerBlockHandler(new RH());
    }

    public int getArmorIndex(String armor) {
        return RenderingRegistry.addNewArmourRendererPrefix(armor);
    }

    @Override
    public void init() {
        super.init();
        ClientRegistry.bindTileEntitySpecialRenderer(
            TCentrifuge.class, new RCentrifuge()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TFusionReactor.class, new RFusionReactor()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TNuclearBoiler.class, new RNuclearBoiler()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(TTurbine.class, new RTurbine());
        ClientRegistry.bindTileEntitySpecialRenderer(
            TThermometer.class, new RThermometer()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TChemicalExtractor.class, new RExtractor()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TAtomicAssembler.class, new RAtomicAssembler()
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
            TFissionReactor.class, new RReactorCell()
        );
        RenderingRegistry.registerEntityRenderingHandler(EMatter.class, new RMatter());
    }

    @Override
    public Object
    getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && ID < CommonProxy.GuiType.values().length) {
            if (tileEntity instanceof TAutoBuilder) {
                return new GAutoBuilder(tileEntity);
            }

            if (tileEntity instanceof TCentrifuge) {
                return new GCentrifuge(player.inventory, (TCentrifuge) tileEntity);
            }

            if (tileEntity instanceof TChemicalExtractor) {
                return new GChemicalExtractor(
                    player.inventory, (TChemicalExtractor) tileEntity
                );
            }

            if (tileEntity instanceof TAccelerator) {
                return new GAccelerator(player.inventory, (TAccelerator) tileEntity);
            }

            if (tileEntity instanceof TAtomicAssembler) {
                return new GAtomicAssembler(
                    player.inventory, (TAtomicAssembler) tileEntity
                );
            }

            if (tileEntity instanceof TNuclearBoiler) {
                return new GNuclearBoiler(player.inventory, (TNuclearBoiler) tileEntity);
            }

            if (tileEntity instanceof TFissionReactor) {
                return new GFissionReactor(
                    player.inventory, (TFissionReactor) tileEntity
                );
            }
        }

        return null;
    }
}
