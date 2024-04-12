package atomicscience.render;

import atomicscience.AtomicScience;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RH implements ISimpleBlockRenderingHandler {
    public static final int BLOCK_RENDER_ID
        = RenderingRegistry.getNextAvailableRenderId();

    public void
    renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.1F, 0.0F);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        if (block == AtomicScience.bTurbine) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(
                new ResourceLocation("atomicscience", "textures/models/turbine.png")
            );
            RTurbine.MODEL.render(0.0F, 0.0625F);
        } else if (block == AtomicScience.bCentrifuge) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(
                new ResourceLocation("atomicscience", "textures/models/centrifuge.png")
            );
            RCentrifuge.MODEL.render(0.0F, 0.0625F);
        } else if (block == AtomicScience.bChemicalExtractor) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(
                new ResourceLocation("atomicscience", "textures/models/extractor.png")
            );
            RExtractor.MODEL.render(0.0F, 0.0625F);
        } else if (block == AtomicScience.bNuclearBoiler) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(
                new ResourceLocation("atomicscience", "textures/models/expansion.png")
            );
            RNuclearBoiler.MODEL.render(0.0F, 0.0625F);
        } else if (block == AtomicScience.bFusionReactor) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(
                new ResourceLocation(
                    "atomicscience", "textures/models/fusion_reactor.png"
                )
            );
            RFusionReactor.MODEL.render(0.0F, 0.0625F);
        } else if (block == AtomicScience.bAtomicAssembler) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(
                new ResourceLocation(
                    "atomicscience", "textures/models/atomic_assembler.png"
                )
            );
            RAtomicAssembler.MODEL.render(0.0F, 0.0F, 0.0F, 0.0625F);
        } else if (block == AtomicScience.bFissionReactor) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(
                new ResourceLocation(
                    "atomicscience", "textures/models/reactorCell_top.png"
                )
            );
            RReactorCell.MODEL_TOP.render(0.0625F);
        }

        GL11.glPopMatrix();
    }

    public boolean shouldRender3DInInventory(int meta) {
        return true;
    }

    public int getRenderId() {
        return BLOCK_RENDER_ID;
    }

    public boolean renderWorldBlock(
        IBlockAccess world,
        int x,
        int y,
        int z,
        Block block,
        int modelId,
        RenderBlocks renderer
    ) {
        return false;
    }
}
