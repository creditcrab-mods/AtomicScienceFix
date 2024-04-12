package atomicscience.render;

import atomicscience.api.ITemperature;
import atomicscience.jiqi.TThermometer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RThermometer extends TileEntitySpecialRenderer {
    public void
    renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8) {
        TThermometer tileEntity = (TThermometer) var1;

        for (int side = 2; side < 6; ++side) {
            GL11.glPushMatrix();
            GL11.glPolygonOffset(-10.0F, -10.0F);
            GL11.glEnable('\u8037');
            float dx = 0.0625F;
            float dz = 0.0625F;
            float displayWidth = 0.875F;
            float displayHeight = 0.875F;
            GL11.glTranslatef((float) x, (float) y, (float) z);
            switch (side) {
                case 0:
                    GL11.glTranslatef(1.0F, 1.0F, 0.0F);
                    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                case 1:
                default:
                    break;
                case 2:
                    GL11.glTranslatef(1.0F, 1.0F, 1.0F);
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                    break;
                case 3:
                    GL11.glTranslatef(0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                    break;
                case 4:
                    GL11.glTranslatef(1.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                    break;
                case 5:
                    GL11.glTranslatef(0.0F, 1.0F, 1.0F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            GL11.glTranslatef(dx + displayWidth / 2.0F, 1.0F, dz + displayHeight / 2.0F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            FontRenderer fontRenderer = this.func_147498_b();
            byte maxWidth = 1;
            String wenDu = Math.round(tileEntity.temp) + " C";
            if (tileEntity.shouldEmitRedstone()) {
                wenDu = "§4" + wenDu;
            }

            String zhuYiWenDu = "Warn: " + tileEntity.getWarningTemp();
            if (tileEntity.linkedTileCoords != null
                && tileEntity.linkedTileCoords.getTileEntity(tileEntity.getWorldObj())
                        instanceof ITemperature) {
                zhuYiWenDu = "§4" + zhuYiWenDu;
            }

            int var28 = Math.max(fontRenderer.getStringWidth(wenDu), maxWidth);
            var28 = Math.max(fontRenderer.getStringWidth(zhuYiWenDu), var28);
            var28 += 4;
            int lineHeight = fontRenderer.FONT_HEIGHT + 2;
            int requiredHeight = lineHeight * 1;
            float scaleX = displayWidth / (float) var28;
            float scaleY = displayHeight / (float) requiredHeight;
            float scale = (float) ((double) Math.min(scaleX, scaleY) * 0.8D);
            GL11.glScalef(scale, -scale, scale);
            GL11.glDepthMask(false);
            int realHeight = (int) Math.floor((double) (displayHeight / scale));
            int realWidth = (int) Math.floor((double) (displayWidth / scale));
            int offsetX = (realWidth - var28) / 2 + 2;
            int offsetY = (realHeight - requiredHeight) / 2;
            GL11.glDisable(2896);
            fontRenderer.drawString(
                wenDu,
                offsetX - realWidth / 2,
                1 + offsetY - realHeight / 2 + 0 * lineHeight,
                1
            );
            fontRenderer.drawString(
                zhuYiWenDu,
                offsetX - realWidth / 2,
                1 + offsetY - realHeight / 2 + 1 * lineHeight,
                1
            );
            GL11.glEnable(2896);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDepthMask(true);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable('\u8037');
            GL11.glPopMatrix();
        }
    }
}
