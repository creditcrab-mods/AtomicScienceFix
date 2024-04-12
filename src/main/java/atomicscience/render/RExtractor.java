package atomicscience.render;

import atomicscience.jiqi.TChemicalExtractor;
import atomicscience.muoxing.MExtractor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RExtractor extends TileEntitySpecialRenderer {
    public static final MExtractor MODEL = new MExtractor();
    public static final String TEXTURE = "extractor.png";

    @Override
    public void
    renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        TChemicalExtractor te = (TChemicalExtractor) tileEntity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        this.bindTexture(
            new ResourceLocation("atomicscience", "textures/models/extractor.png")
        );
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        switch (te.getDirection(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord)) {
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

        if (te.smeltingTicks > 0) {
            te.rotation = (float) ((double) te.rotation + (double) f * 0.3D);
        }

        MODEL.render(te.rotation, 0.0625F);
        GL11.glPopMatrix();
    }
}
