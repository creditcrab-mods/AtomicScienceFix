package atomicscience.shimian;

import atomicscience.AtomicScience;
import atomicscience.PAutoBuilder;
import atomicscience.TAutoBuilder;
import calclavia.lib.gui.GuiScreenBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.tileentity.TileEntity;
import universalelectricity.core.vector.Vector3;

public class GAutoBuilder extends GuiScreenBase {
    private TileEntity tileEntity;
    private GuiTextField tfBbanJing;
    private TAutoBuilder.AutoBuilderType mode = TAutoBuilder.AutoBuilderType.get(0);

    public GAutoBuilder(TileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.tfBbanJing = new GuiTextField(this.fontRendererObj, 45, 58, 50, 12);
        this.buttonList.add(
            new GuiButton(0, this.width / 2 - 80, this.height / 2 - 10, 58, 20, "Build")
        );
        this.buttonList.add(
            new GuiButton(1, this.width / 2 - 50, this.height / 2 - 35, 120, 20, "Mode")
        );
    }

    @Override
    protected void drawForegroundLayer(int x, int y, float var1) {
        this.fontRendererObj.drawString("Auto Builder", 60, 6, 4210752);
        this.fontRendererObj.drawString("This is a creative only cheat", 9, 20, 4210752);
        this.fontRendererObj.drawString("which allows you to auto build", 9, 30, 4210752);
        this.fontRendererObj.drawString("structures for testing.", 9, 40, 4210752);
        this.fontRendererObj.drawString("Radius: ", 9, 60, 4210752);
        this.tfBbanJing.drawTextBox();
        ((GuiButton) this.buttonList.get(1)).displayString = this.mode.name;
        this.fontRendererObj.drawString("Mode: ", 9, 80, 4210752);
        super.drawForegroundLayer(x, y, var1);
        this.fontRendererObj.drawString("Warning!", 9, 130, 4210752);
        this.fontRendererObj.drawString(
            "This will replace blocks without", 9, 140, 4210752
        );
        this.fontRendererObj.drawString(
            "dropping it! You may lose items.", 9, 150, 4210752
        );
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        super.keyTyped(par1, par2);
        this.tfBbanJing.textboxKeyTyped(par1, par2);
    }

    @Override
    protected void mouseClicked(int x, int y, int par3) {
        super.mouseClicked(x, y, par3);
        this.tfBbanJing.mouseClicked(
            x - super.containerWidth, y - super.containerHeight, par3
        );
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        super.actionPerformed(par1GuiButton);
        if (par1GuiButton.id == 0) {
            int radius = 0;

            try {
                radius = Integer.parseInt(this.tfBbanJing.getText());
            } catch (Exception var4) {
                ;
            }

            if (radius > 0) {
                AtomicScience.channel.sendToServer(
                    new PAutoBuilder(new Vector3(this.tileEntity), this.mode, radius)
                );
            }
        } else if (par1GuiButton.id == 1) {
            this.mode = this.mode.next();
        }
    }
}
