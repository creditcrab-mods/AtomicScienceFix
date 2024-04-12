package atomicscience.muoxing;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class MCube extends ModelBase {
    public static final MCube INSTNACE = new MCube();
    private ModelRenderer cube = new ModelRenderer(this, 0, 0);

    public MCube() {
        byte size = 16;
        this.cube.addBox(
            (float) (-size / 2),
            (float) (-size / 2),
            (float) (-size / 2),
            size,
            size,
            size
        );
        this.cube.setTextureSize(112, 70);
        this.cube.mirror = true;
    }

    public void render() {
        float f = 0.0625F;
        this.cube.render(f);
    }
}
