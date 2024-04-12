package atomicscience.render;

import atomicscience.hecheng.TFusionReactor;
import atomicscience.muoxing.MFusionReactor;
import calclavia.lib.render.RenderTaggedTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RFusionReactor extends RenderTaggedTile {
    public static final MFusionReactor MODEL = new MFusionReactor();
    public static final String TEXTURE = "fusion_reactor.png";

    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f) {
        super.renderTileEntityAt(t, x, y, z, f);
        TFusionReactor tileEntity = (TFusionReactor) t;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        this.bindTexture(
            new ResourceLocation("atomicscience", "textures/models/fusion_reactor.png")
        );
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        MODEL.render((float) Math.toRadians((double) tileEntity.rotation), 0.0625F);
        tileEntity.rotation
            = (float) ((double) tileEntity.rotation + tileEntity.wattsReceived / 500.0D);
        if (tileEntity.rotation > 360.0F) {
            tileEntity.rotation = 0.0F;
        }

        GL11.glPopMatrix();
    }
}
