package atomicscience.render;

import atomicscience.fenlie.TNuclearBoiler;
import atomicscience.muoxing.MNuclearBoiler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RNuclearBoiler extends TileEntitySpecialRenderer {
    public static final MNuclearBoiler MODEL = new MNuclearBoiler();
    public static final String TEXTURE = "expansion.png";

    public void
    renderAModelAt(TNuclearBoiler tileEntity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        this.bindTexture(
            new ResourceLocation("atomicscience", "textures/models/expansion.png")
        );
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        switch (tileEntity.getDirection(
            tileEntity.getWorldObj(),
            tileEntity.xCoord,
            tileEntity.yCoord,
            tileEntity.zCoord
        )) {
            case NORTH:
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                break;
            case SOUTH:
                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                break;
            case WEST:
                GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
                break;
            case EAST:
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            default:
                break;
        }

        if (tileEntity.smeltingTicks > 0) {
            tileEntity.rotation
                = (float) ((double) tileEntity.rotation + (double) f * 0.3D);
        }

        MODEL.render(tileEntity.rotation, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(
        TileEntity tileEntity, double var2, double var4, double var6, float var8
    ) {
        this.renderAModelAt((TNuclearBoiler) tileEntity, var2, var4, var6, var8);
    }
}
