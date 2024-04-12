package atomicscience.render;

import atomicscience.fenlie.TCentrifuge;
import atomicscience.muoxing.MCentrifuge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RCentrifuge extends TileEntitySpecialRenderer {
    public static final MCentrifuge MODEL = new MCentrifuge();
    public static final String TEXTURE = "centrifuge.png";

    public void
    renderAModelAt(TCentrifuge tileEntity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        this.bindTexture(
            new ResourceLocation("atomicscience", "textures/models/centrifuge.png")
        );
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        int facingDirection = tileEntity.getBlockMetadata();
        switch (facingDirection) {
            case 2:
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
                break;
            case 4:
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                break;
            case 5:
                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        }

        if (tileEntity.smeltingTicks > 0) {
            tileEntity.xuanZhuan
                = (float) ((double) tileEntity.xuanZhuan + (double) f * 0.3D);
        }

        MODEL.render(tileEntity.xuanZhuan, 0.0625F);
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(
        TileEntity tileEntity, double var2, double var4, double var6, float var8
    ) {
        this.renderAModelAt((TCentrifuge) tileEntity, var2, var4, var6, var8);
    }
}
