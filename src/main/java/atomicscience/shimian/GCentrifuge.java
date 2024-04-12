package atomicscience.shimian;

import atomicscience.fenlie.CCentrifuge;
import atomicscience.fenlie.TCentrifuge;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import universalelectricity.api.energy.UnitDisplay;

public class GCentrifuge extends GBase {
    private TCentrifuge tileEntity;

    public GCentrifuge(InventoryPlayer par1InventoryPlayer, TCentrifuge tileEntity) {
        super(new CCentrifuge(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRendererObj.drawString(
            this.tileEntity.getInventoryName(), 60, 6, 4210752
        );
        String displayText = "";
        if (this.tileEntity.isDisabled()) {
            displayText = "Disabled!";
        } else if (this.tileEntity.smeltingTicks > 0) {
            displayText = "Processing";
        } else if (this.tileEntity.canWork()) {
            displayText = "Ready";
        } else {
            displayText = "Idle";
        }

        this.fontRendererObj.drawString("Status: " + displayText, 70, 50, 4210752);
        this.fontRendererObj.drawString(
            UnitDisplay.getDisplay(10000.0D, UnitDisplay.Unit.WATT), 70, 60, 4210752
        );
        this.fontRendererObj.drawString(
            UnitDisplay.getDisplay(
                this.tileEntity.getVoltage(), UnitDisplay.Unit.VOLTAGE
            ),
            70,
            70,
            4210752
        );
        this.fontRendererObj.drawString(
            StatCollector.translateToLocal("container.inventory"),
            8,
            this.ySize - 96 + 2,
            4210752
        );
        if (this.func_146978_c(8, 18, 14, 49, x, y)
            && this.tileEntity.gasTank.getFluid() != null) {
            this.drawTooltip(
                x - this.guiLeft,
                y - this.guiTop + 10,
                new String[] { this.tileEntity.gasTank.getFluid().getLocalizedName(),
                               (float) this.tileEntity.gasTank.getFluidAmount() / 1000.0F
                                   + " dm3" }
            );
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int x, int y) {
        super.drawGuiContainerBackgroundLayer(par1, x, y);
        this.drawSlot(80, 25);
        this.drawSlot(100, 25);
        this.drawSlot(130, 25, GBase.SlotType.DIAN);
        this.drawBar(40, 26, (float) this.tileEntity.smeltingTicks / 2400.0F);
        this.drawMeter(
            8,
            18,
            (float) this.tileEntity.gasTank.getFluidAmount()
                / (float) this.tileEntity.gasTank.getCapacity(),
            0.84F,
            0.9F,
            0.69F
        );
        this.drawSlot(24, 49, GBase.SlotType.QI);
    }
}
