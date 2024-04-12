package atomicscience.shimian;

import atomicscience.fanwusu.CAccelerator;
import atomicscience.fanwusu.EMatter;
import atomicscience.fanwusu.TAccelerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import universalelectricity.api.energy.UnitDisplay;
import universalelectricity.core.vector.Vector3;

public class GAccelerator extends GuiContainer {
    private TAccelerator tileEntity;
    private int containerWidth;
    private int containerHeight;

    public GAccelerator(InventoryPlayer par1InventoryPlayer, TAccelerator tileEntity) {
        super(new CAccelerator(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(
            this.tileEntity.getInventoryName(), 40, 10, 4210752
        );
        String status = "";
        Vector3 position = new Vector3(this.tileEntity);
        position.modifyPositionFromSide(this.tileEntity
                                            .getDirection(
                                                this.tileEntity.getWorldObj(),
                                                this.tileEntity.xCoord,
                                                this.tileEntity.xCoord,
                                                this.tileEntity.zCoord
                                            )
                                            .getOpposite());
        if (this.tileEntity.isDisabled()) {
            status = "Disabled";
        } else if (!EMatter.canExist(
                       this.tileEntity.getWorldObj(),
                       position,
                       this.tileEntity
                           .getDirection(
                               this.tileEntity.getWorldObj(),
                               this.tileEntity.xCoord,
                               this.tileEntity.yCoord,
                               this.tileEntity.zCoord
                           )
                           .getOpposite()
                   )) {
            status = "Failure";
        } else if (this.tileEntity.wuSu != null && this.tileEntity.suDu > 0.0F) {
            status = "Accelerating";
        } else {
            status = "Idle";
        }

        this.fontRendererObj.drawString(
            "Velocity: " + Math.round(this.tileEntity.suDu / 1.0F * 100.0F) + "%",
            8,
            27,
            4210752
        );
        this.fontRendererObj.drawString("Status: " + status, 8, 38, 4210752);
        this.fontRendererObj.drawString(
            "Used: "
                + UnitDisplay.getDisplayShort(
                    this.tileEntity.yongDianLiang, UnitDisplay.Unit.JOULES
                ),
            8,
            49,
            4210752
        );
        StringBuilder var10001 = new StringBuilder();
        this.tileEntity.getClass();
        this.fontRendererObj.drawString(
            var10001
                .append(UnitDisplay.getDisplayShort(
                    (double) (10000 * 20), UnitDisplay.Unit.WATT
                ))
                .append(" ")
                .append(UnitDisplay.getDisplayShort(
                    this.tileEntity.getVoltage(), UnitDisplay.Unit.VOLTAGE
                ))
                .toString(),
            8,
            60,
            4210752
        );
        this.fontRendererObj.drawString(
            "Antimatter: " + this.tileEntity.antimatter + " mg",
            8,
            this.ySize - 96 + 2,
            4210752
        );
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        this.mc.renderEngine.bindTexture(
            new ResourceLocation("atomicscience", "textures/gui/gui_accelerator.png")
        );
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.containerWidth = (this.width - this.xSize) / 2;
        this.containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(
            this.containerWidth, this.containerHeight, 0, 0, this.xSize, this.ySize
        );
    }
}
