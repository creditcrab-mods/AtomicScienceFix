package atomicscience.shimian;

import atomicscience.hecheng.CChemicalExtractor;
import atomicscience.jiqi.TChemicalExtractor;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import universalelectricity.api.energy.UnitDisplay;

public class GChemicalExtractor extends GBase {
    private TChemicalExtractor tileEntity;

    public GChemicalExtractor(
        InventoryPlayer par1InventoryPlayer, TChemicalExtractor tileEntity
    ) {
        super(new CChemicalExtractor(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRendererObj.drawString(
            this.tileEntity.getInventoryName(), 45, 6, 4210752
        );
        this.tileEntity.getClass();
        this.fontRendererObj.drawString(
            UnitDisplay.getDisplay((double) (500.0F * 20.0F), UnitDisplay.Unit.WATT),
            90,
            48,
            4210752
        );
        this.fontRendererObj.drawString(
            UnitDisplay.getDisplay(
                this.tileEntity.getVoltage(), UnitDisplay.Unit.VOLTAGE
            ),
            90,
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
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int x, int y) {
        super.drawGuiContainerBackgroundLayer(par1, x, y);
        this.drawSlot(64, 24);
        this.drawSlot(64, 48, GBase.SlotType.DIAN);
        this.drawSlot(117, 24);
        float var10003 = (float) this.tileEntity.smeltingTicks;
        this.tileEntity.getClass();
        this.drawBar(87, 24, var10003 / 280.0F);
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
    }
}
