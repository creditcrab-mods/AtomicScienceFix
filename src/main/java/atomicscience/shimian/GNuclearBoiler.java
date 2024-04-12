package atomicscience.shimian;

import atomicscience.fenlie.TNuclearBoiler;
import atomicscience.hecheng.CNuclearBoiler;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import universalelectricity.api.energy.UnitDisplay;

public class GNuclearBoiler extends GBase {
    private TNuclearBoiler tileEntity;

    public GNuclearBoiler(
        InventoryPlayer par1InventoryPlayer, TNuclearBoiler tileEntity
    ) {
        super(new CNuclearBoiler(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRendererObj.drawString(
            this.tileEntity.getInventoryName(), 52, 6, 4210752
        );
        this.tileEntity.getClass();
        this.fontRendererObj.drawString(
            UnitDisplay.getDisplay((double) (800.0F * 20.0F), UnitDisplay.Unit.WATT),
            55,
            48,
            4210752
        );
        this.fontRendererObj.drawString(
            UnitDisplay.getDisplay(
                this.tileEntity.getVoltage(), UnitDisplay.Unit.VOLTAGE
            ),
            55,
            60,
            4210752
        );
        this.fontRendererObj.drawString(
            StatCollector.translateToLocal("container.inventory"),
            8,
            this.ySize - 96 + 2,
            4210752
        );
        if (this.func_146978_c(8, 18, 14, 49, x, y)
            && this.tileEntity.waterTank.getFluid() != null) {
            this.drawTooltip(
                x - this.guiLeft,
                y - this.guiTop + 10,
                new String[] { this.tileEntity.waterTank.getFluid().getLocalizedName(),
                               this.tileEntity.waterTank.getFluidAmount() + " ml" }
            );
        } else if (this.func_146978_c(155, 18, 14, 49, x, y) && this.tileEntity.gasTank.getFluid() != null) {
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
        this.drawSlot(55, 25, GBase.SlotType.DIAN);
        this.drawSlot(80, 25);
        float var10003 = (float) this.tileEntity.smeltingTicks;
        this.tileEntity.getClass();
        this.drawBar(110, 26, var10003 / 300.0F);
        this.drawMeter(
            8,
            18,
            (float) this.tileEntity.waterTank.getFluidAmount()
                / (float) this.tileEntity.waterTank.getCapacity(),
            0.16F,
            0.52F,
            0.99F
        );
        this.drawSlot(24, 49, GBase.SlotType.SHUI);
        this.drawMeter(
            155,
            18,
            (float) this.tileEntity.gasTank.getFluidAmount()
                / (float) this.tileEntity.gasTank.getCapacity(),
            0.84F,
            0.9F,
            0.69F
        );
        this.drawSlot(135, 49, GBase.SlotType.QI);
    }
}
