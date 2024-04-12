package atomicscience.shimian;

import atomicscience.fenlie.TFissionReactor;
import calclavia.lib.gui.GuiContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import universalelectricity.api.energy.UnitDisplay;

public class GFissionReactor extends GuiContainerBase {
    private TFissionReactor tileEntity;

    public GFissionReactor(InventoryPlayer inventory, TFissionReactor tileEntity) {
        super(new CFissionReactor(inventory.player, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRendererObj.drawString(
            this.tileEntity.getInventoryName(),
            this.xSize / 2
                - this.fontRendererObj.getStringWidth(this.tileEntity.getInventoryName())
                    / 2,
            6,
            4210752
        );
        int toxicity = 0;
        if (this.tileEntity.wasteTank.getFluid() != null) {
            toxicity = this.tileEntity.wasteTank.getFluidAmount();
        }

        this.fontRendererObj.drawString("Toxicity: " + toxicity + " dm3", 9, 45, 4210752);
        this.fontRendererObj.drawString(
            "Temperature: " + (int) this.tileEntity.temperature + " Celsius", 9, 70, 4210752
        );
        this.fontRendererObj.drawString(
            "Steam Emission: "
                + UnitDisplay.roundDecimals((double
                ) (1.0F
                   / (Math.max(40.0F - this.tileEntity.temperature / 2000.0F * 40.0F, 2.0F)
                      / 20.0F)))
                + "/second",
            9,
            95,
            4210752
        );
        if (this.tileEntity.getStackInSlot(0) != null) {
            int ticksLeft = this.tileEntity.getStackInSlot(0).getMaxDamage()
                - this.tileEntity.getStackInSlot(0).getItemDamage();
            this.fontRendererObj.drawString(
                "Time Left: " + ticksLeft / 20 / this.tileEntity.getHeight() + " seconds "
                    + ticksLeft,
                9,
                105,
                4210752
            );
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int x, int y) {
        super.drawGuiContainerBackgroundLayer(par1, x, y);
        this.drawSlot(78, 20);
        if (this.tileEntity.wasteTank.getFluid() != null) {
            int toxicity1 = this.tileEntity.wasteTank.getFluidAmount();
            this.drawForce(
                10,
                55,
                (float) toxicity1 / (float) this.tileEntity.wasteTank.getCapacity()
            );
        }

        this.drawForce(10, 80, this.tileEntity.temperature / 2000.0F);
        if (this.tileEntity.getStackInSlot(0) != null) {
            float ticksLeft = (float
            ) (this.tileEntity.getStackInSlot(0).getMaxDamage()
               - this.tileEntity.getStackInSlot(0).getItemDamage());
            this.drawElectricity(
                10,
                115,
                ticksLeft / (float) this.tileEntity.getStackInSlot(0).getMaxDamage()
            );
        }
    }
}
