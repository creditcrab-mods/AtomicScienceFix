package atomicscience.render;

import atomicscience.fenlie.TFissionReactor;
import atomicscience.muoxing.MCube;
import atomicscience.muoxing.MReactorCellBottom;
import atomicscience.muoxing.MReactorCellMiddle;
import atomicscience.muoxing.MReactorCellTop;
import calclavia.lib.render.RenderTaggedTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RReactorCell extends RenderTaggedTile {
    public static final MReactorCellTop MODEL_TOP = new MReactorCellTop();
    public static final MReactorCellMiddle MODEL_MIDDLE = new MReactorCellMiddle();
    public static final MReactorCellBottom MODEL_BOTTOM = new MReactorCellBottom();
    public static final String TEXTURE_TOP = "reactorCell_top.png";
    public static final String TEXTURE_MIDDLE = "reactorCell_middle.png";
    public static final String TEXTURE_BOTTOM = "reactorCell_bottom.png";

    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f) {
        TFissionReactor tileEntity = (TFissionReactor) t;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        switch (tileEntity.getBlockMetadata()) {
            case 0:
                this.bindTexture(new ResourceLocation(
                    "atomicscience", "textures/models/reactorCell_bottom.png"
                ));
                MODEL_BOTTOM.render(0.0625F);
                break;
            case 1:
                this.bindTexture(new ResourceLocation(
                    "atomicscience", "textures/models/reactorCell_middle.png"
                ));
                MODEL_MIDDLE.render(0.0625F);
                break;
            case 2:
                GL11.glTranslatef(0.0F, -0.4F, 0.0F);
                GL11.glScalef(1.0F, 1.3F, 1.0F);
                this.bindTexture(new ResourceLocation(
                    "atomicscience", "textures/models/reactorCell_top.png"
                ));
                MODEL_TOP.render(0.0625F);
        }

        GL11.glPopMatrix();
        if (tileEntity.getStackInSlot(0) != null) {
            float height = (float) tileEntity.getHeight()
                * ((float
                   ) (tileEntity.getStackInSlot(0).getMaxDamage()
                      - tileEntity.getStackInSlot(0).getItemDamage())
                   / (float) tileEntity.getStackInSlot(0).getMaxDamage());
            GL11.glPushMatrix();
            GL11.glTranslatef(
                (float) x + 0.5F, (float) y + 0.5F * height, (float) z + 0.5F
            );
            GL11.glScalef(0.4F, 1.0F * height, 0.4F);
            this.bindTexture(new ResourceLocation(
                "atomicscience", "textures/models/fissileMaterial.png"
            ));
            MCube.INSTNACE.render();
            GL11.glPopMatrix();
        }
    }
}
