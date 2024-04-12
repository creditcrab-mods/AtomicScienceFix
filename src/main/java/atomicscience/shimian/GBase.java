package atomicscience.shimian;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GBase extends GuiContainer {
    public static final int METER_HEIGHT = 49;
    public static final int METER_WIDTH = 14;
    protected int kuanDu;
    protected int gaoDu;

    public GBase(Container par1Container) {
        super(par1Container);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        this.kuanDu = (this.width - this.xSize) / 2;
        this.gaoDu = (this.height - this.ySize) / 2;
        this.mc.renderEngine.bindTexture(
            new ResourceLocation("atomicscience", "textures/gui/gui_base.png")
        );
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.kuanDu, this.gaoDu, 0, 0, this.xSize, this.ySize);
    }

    protected void drawSlot(int x, int y, GBase.SlotType type) {
        this.mc.renderEngine.bindTexture(
            new ResourceLocation("atomicscience", "textures/gui/gui_components.png")
        );
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.kuanDu + x, this.gaoDu + y, 0, 0, 18, 18);
        switch (type) {
            case DIAN:
                this.drawTexturedModalRect(
                    this.kuanDu + x, this.gaoDu + y, 0, 18, 18, 18
                );
                break;
            case SHUI:
                this.drawTexturedModalRect(
                    this.kuanDu + x, this.gaoDu + y, 0, 36, 18, 18
                );
                break;
            case QI:
                this.drawTexturedModalRect(
                    this.kuanDu + x, this.gaoDu + y, 0, 54, 18, 18
                );
            default:
                break;
        }
    }

    protected void drawSlot(int x, int y) {
        this.drawSlot(x, y, GBase.SlotType.NONE);
    }

    protected void drawBar(int x, int y, float scale) {
        this.mc.renderEngine.bindTexture(
            new ResourceLocation("atomicscience", "textures/gui/gui_components.png")
        );
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.kuanDu + x, this.gaoDu + y, 18, 0, 22, 15);
        if (scale > 0.0F) {
            this.drawTexturedModalRect(
                this.kuanDu + x, this.gaoDu + y, 18, 15, 22 - (int) (scale * 22.0F), 15
            );
        }
    }

    protected void drawMeter(int x, int y, float scale, float r, float g, float b) {
        this.mc.renderEngine.bindTexture(
            new ResourceLocation("atomicscience", "textures/gui/gui_components.png")
        );
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.kuanDu + x, this.gaoDu + y, 40, 0, 14, 49);
        GL11.glColor4f(r, g, b, 1.0F);
        int actualScale = (int) (48.0F * scale);
        this.drawTexturedModalRect(
            this.kuanDu + x, this.gaoDu + y + (48 - actualScale), 40, 49, 13, actualScale
        );
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.kuanDu + x, this.gaoDu + y, 40, 98, 14, 49);
    }

    public void drawTooltip(int x, int y, String... toolTips) {
        GL11.glDisable('\u803a');
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        if (toolTips != null) {
            int var5 = 0;

            int var6;
            int var7;
            for (var6 = 0; var6 < toolTips.length; ++var6) {
                var7 = this.fontRendererObj.getStringWidth(toolTips[var6]);
                if (var7 > var5) {
                    var5 = var7;
                }
            }

            var6 = x + 12;
            var7 = y - 12;
            int var9 = 8;
            if (toolTips.length > 1) {
                var9 += 2 + (toolTips.length - 1) * 10;
            }

            if (this.guiTop + var7 + var9 + 6 > this.height) {
                var7 = this.height - var9 - this.guiTop - 6;
            }

            super.zLevel = 300.0F;
            int var10 = -267386864;
            this.drawGradientRect(
                var6 - 3, var7 - 4, var6 + var5 + 3, var7 - 3, var10, var10
            );
            this.drawGradientRect(
                var6 - 3, var7 + var9 + 3, var6 + var5 + 3, var7 + var9 + 4, var10, var10
            );
            this.drawGradientRect(
                var6 - 3, var7 - 3, var6 + var5 + 3, var7 + var9 + 3, var10, var10
            );
            this.drawGradientRect(
                var6 - 4, var7 - 3, var6 - 3, var7 + var9 + 3, var10, var10
            );
            this.drawGradientRect(
                var6 + var5 + 3, var7 - 3, var6 + var5 + 4, var7 + var9 + 3, var10, var10
            );
            int var11 = 1347420415;
            int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
            this.drawGradientRect(
                var6 - 3, var7 - 3 + 1, var6 - 3 + 1, var7 + var9 + 3 - 1, var11, var12
            );
            this.drawGradientRect(
                var6 + var5 + 2,
                var7 - 3 + 1,
                var6 + var5 + 3,
                var7 + var9 + 3 - 1,
                var11,
                var12
            );
            this.drawGradientRect(
                var6 - 3, var7 - 3, var6 + var5 + 3, var7 - 3 + 1, var11, var11
            );
            this.drawGradientRect(
                var6 - 3, var7 + var9 + 2, var6 + var5 + 3, var7 + var9 + 3, var12, var12
            );

            for (int var13 = 0; var13 < toolTips.length; ++var13) {
                String var14 = toolTips[var13];
                this.fontRendererObj.drawStringWithShadow(var14, var6, var7, -1);
                if (var13 == 0) {
                    var7 += 2;
                }

                var7 += 10;
            }

            super.zLevel = 0.0F;
            GL11.glEnable(2929);
            GL11.glEnable(2896);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable('\u803a');
        }
    }

    public static enum SlotType {
        NONE,
        DIAN,
        SHUI,
        QI;
    }
}
