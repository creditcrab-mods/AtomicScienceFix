package atomicscience.shimian;

import atomicscience.TAtomicAssembler;
import atomicscience.jiqi.CAssembler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GAtomicAssembler extends GuiContainer {
    private TAtomicAssembler tileEntity;
    private int containerWidth;
    private int containerHeight;

    public GAtomicAssembler(
        InventoryPlayer par1InventoryPlayer, TAtomicAssembler tileEntity
    ) {
        super(new CAssembler(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.ySize = 230;
    }

    public void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(
            this.tileEntity.getInventoryName(),
            65 - this.tileEntity.getInventoryName().length(),
            6,
            4210752
        );
        String displayText = "";
        if (this.tileEntity.isDisabled()) {
            displayText = "Disabled!";
        } else if (this.tileEntity.smeltingTicks > 0) {
            StringBuilder var10000 = (new StringBuilder()).append("Process: ");
            float var10002 = (float) this.tileEntity.smeltingTicks;
            this.tileEntity.getClass();
            displayText = var10000.append((int) (100.0F - var10002 / 1200.0F * 100.0F))
                              .append("%")
                              .toString();
        } else if (this.tileEntity.canWork()) {
            displayText = "Ready";
        } else {
            displayText = "Idle";
        }

        this.fontRendererObj.drawString(displayText, 100, this.ySize - 108 + 2, 4210752);
        this.fontRendererObj.drawString(
            "Voltage: " + (int) this.tileEntity.getVoltage(),
            100,
            this.ySize - 96 + 2,
            4210752
        );
        this.fontRendererObj.drawString(
            StatCollector.translateToLocal("container.inventory"),
            8,
            this.ySize - 96 + 2,
            4210752
        );
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        this.mc.renderEngine.bindTexture(
            new ResourceLocation("atomicscience", "textures/gui/gui_atomic_assembler.png")
        );
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.containerWidth = (this.width - this.xSize) / 2;
        this.containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(
            this.containerWidth, this.containerHeight, 0, 0, this.xSize, this.ySize
        );
    }
}
