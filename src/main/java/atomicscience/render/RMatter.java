package atomicscience.render;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RMatter extends Render {
    @Override
    public void
    doRender(Entity entity, double x, double y, double z, float var8, float var9) {
        Tessellator tessellator = Tessellator.instance;

        float par2;
        for (par2 = (float) entity.ticksExisted; par2 > 200.0F; par2 -= 100.0F) {
            ;
        }

        RenderHelper.disableStandardItemLighting();
        float var41 = (5.0F + par2) / 200.0F;
        float var51 = 0.0F;
        if (var41 > 0.8F) {
            var51 = (var41 - 0.8F) / 0.2F;
        }

        Random rand = new Random(432L);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.15F, (float) y + 0.15F, (float) z + 0.15F);
        GL11.glScalef(0.15F, 0.15F, 0.15F);
        GL11.glDisable(3553);
        GL11.glShadeModel(7425);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glDisable(3008);
        GL11.glEnable(2884);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -1.0F, -2.0F);

        for (int i1 = 0; (float) i1 < (var41 + var41 * var41) / 2.0F * 60.0F; ++i1) {
            GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(rand.nextFloat() * 360.0F + var41 * 90.0F, 0.0F, 0.0F, 1.0F);
            tessellator.startDrawing(6);
            float var81 = rand.nextFloat() * 20.0F + 5.0F + var51 * 10.0F;
            float var91 = rand.nextFloat() * 2.0F + 1.0F + var51 * 2.0F;
            tessellator.setColorRGBA_I(16777215, (int) (255.0F * (1.0F - var51)));
            tessellator.addVertex(0.0D, 0.0D, 0.0D);
            tessellator.setColorRGBA_I(0, 0);
            tessellator.addVertex(
                -0.866D * (double) var91, (double) var81, (double) (-0.5F * var91)
            );
            tessellator.addVertex(
                0.866D * (double) var91, (double) var81, (double) (-0.5F * var91)
            );
            tessellator.addVertex(0.0D, (double) var81, (double) (1.0F * var91));
            tessellator.addVertex(
                -0.866D * (double) var91, (double) var81, (double) (-0.5F * var91)
            );
            tessellator.draw();
        }

        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glDisable(2884);
        GL11.glDisable(3042);
        GL11.glShadeModel(7424);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
    }

    // TODO: NPE?
    @Override
    protected ResourceLocation getEntityTexture(Entity arg0) {
        return null;
    }
}
