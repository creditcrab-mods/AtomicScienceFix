package atomicscience.render;

import atomicscience.TAtomicAssembler;
import atomicscience.muoxing.MAtomicAssembler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RAtomicAssembler extends TileEntitySpecialRenderer {
    public static final MAtomicAssembler MODEL = new MAtomicAssembler();
    public static final String TEXTURE = "atomic_assembler.png";

    //private final RenderBlocks renderBlocks = new RenderBlocks();

    public void
    renderModelAt(TAtomicAssembler tileEntity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        this.bindTexture(
            new ResourceLocation("atomicscience", "textures/models/atomic_assembler.png")
        );
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        MODEL.render(
            tileEntity.rotationYaw1,
            tileEntity.rotationYaw2,
            tileEntity.rotationYaw3,
            0.0625F
        );
        GL11.glPopMatrix();
        RenderItem renderItem = (RenderItem
        ) RenderManager.instance.getEntityClassRenderObject(EntityItem.class);
        //RenderEngine renderEngine = Minecraft.getMinecraft().renderEngine;
        GL11.glPushMatrix();
        if (tileEntity.entityItem != null) {
            renderItem.doRender(
                tileEntity.entityItem, x + 0.5D, y + 0.4D, z + 0.5D, 0.0F, 0.0F
            );
        }

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(
        TileEntity tileEntity, double var2, double var4, double var6, float var8
    ) {
        this.renderModelAt((TAtomicAssembler) tileEntity, var2, var4, var6, var8);
    }
}
