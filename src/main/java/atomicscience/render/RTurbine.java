package atomicscience.render;

import atomicscience.jiqi.TTurbine;
import atomicscience.muoxing.MLargeTurbine;
import atomicscience.muoxing.MTurbine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RTurbine extends TileEntitySpecialRenderer {
    public static final MTurbine MODEL = new MTurbine();
    public static final MLargeTurbine DA_MODEL = new MLargeTurbine();
    public static final String TEXTURE = "turbine.png";
    public static final String DA_TEXTURE = "large_turbine.png";

    public void
    renderAModelAt(TTurbine tileEntity, double x, double y, double z, float f) {
        if (!tileEntity.hasMaster()) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            if (tileEntity.isMultiblock) {
                GL11.glScalef(0.9F, 1.0F, 1.0F);
                this.bindTexture(new ResourceLocation(
                    "atomicscience", "textures/models/large_turbine.png"
                ));
                DA_MODEL.render(tileEntity.rotation, 0.0625F);
            } else {
                GL11.glScalef(1.0F, 1.1F, 1.0F);
                this.bindTexture(
                    new ResourceLocation("atomicscience", "textures/models/turbine.png")
                );
                MODEL.render(
                    (float) Math.toRadians((double) tileEntity.rotation), 0.0625F
                );
            }

            GL11.glPopMatrix();
        }
    }

    @Override
    public void renderTileEntityAt(
        TileEntity tileEntity, double var2, double var4, double var6, float var8
    ) {
        this.renderAModelAt((TTurbine) tileEntity, var2, var4, var6, var8);
    }
}
