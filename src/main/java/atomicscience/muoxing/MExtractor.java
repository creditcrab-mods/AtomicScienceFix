package atomicscience.muoxing;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class MExtractor extends ModelBase {
    ModelRenderer MAIN_BASE;
    ModelRenderer CORNER_SUPPORT_1;
    ModelRenderer CORNER_SUPPORT_2;
    ModelRenderer CORNER_SUPPORT_3;
    ModelRenderer CORNER_SUPPORT_4;
    ModelRenderer SUPPORT_BEAM_1;
    ModelRenderer SUPPORT_BEAM_2;
    ModelRenderer MAIN_CHAMBER_ROTATES;
    ModelRenderer MAGNET_1_ROTATES;
    ModelRenderer MAGNET_2_ROTATES;
    ModelRenderer ENERGY_PLUG;
    ModelRenderer KEYBOARD_SUPPORT;
    ModelRenderer KEYBOARD;

    public MExtractor() {
        super.textureWidth = 128;
        super.textureHeight = 128;
        this.MAIN_BASE = new ModelRenderer(this, 0, 0);
        this.MAIN_BASE.addBox(0.0F, 0.0F, 0.0F, 14, 1, 14);
        this.MAIN_BASE.setRotationPoint(-7.0F, 23.0F, -7.0F);
        this.MAIN_BASE.setTextureSize(128, 128);
        this.MAIN_BASE.mirror = true;
        this.setRotation(this.MAIN_BASE, 0.0F, 0.0F, 0.0F);
        this.CORNER_SUPPORT_1 = new ModelRenderer(this, 0, 19);
        this.CORNER_SUPPORT_1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
        this.CORNER_SUPPORT_1.setRotationPoint(6.0F, 22.0F, -8.0F);
        this.CORNER_SUPPORT_1.setTextureSize(128, 128);
        this.CORNER_SUPPORT_1.mirror = true;
        this.setRotation(this.CORNER_SUPPORT_1, 0.0F, 0.0F, 0.0F);
        this.CORNER_SUPPORT_2 = new ModelRenderer(this, 0, 19);
        this.CORNER_SUPPORT_2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
        this.CORNER_SUPPORT_2.setRotationPoint(-8.0F, 22.0F, 6.0F);
        this.CORNER_SUPPORT_2.setTextureSize(128, 128);
        this.CORNER_SUPPORT_2.mirror = true;
        this.setRotation(this.CORNER_SUPPORT_2, 0.0F, 0.0F, 0.0F);
        this.CORNER_SUPPORT_3 = new ModelRenderer(this, 0, 19);
        this.CORNER_SUPPORT_3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
        this.CORNER_SUPPORT_3.setRotationPoint(-8.0F, 22.0F, -8.0F);
        this.CORNER_SUPPORT_3.setTextureSize(128, 128);
        this.CORNER_SUPPORT_3.mirror = true;
        this.setRotation(this.CORNER_SUPPORT_3, 0.0F, 0.0F, 0.0F);
        this.CORNER_SUPPORT_4 = new ModelRenderer(this, 0, 19);
        this.CORNER_SUPPORT_4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
        this.CORNER_SUPPORT_4.setRotationPoint(6.0F, 22.0F, 6.0F);
        this.CORNER_SUPPORT_4.setTextureSize(128, 128);
        this.CORNER_SUPPORT_4.mirror = true;
        this.setRotation(this.CORNER_SUPPORT_4, 0.0F, 0.0F, 0.0F);
        this.SUPPORT_BEAM_1 = new ModelRenderer(this, 10, 19);
        this.SUPPORT_BEAM_1.addBox(0.0F, 0.0F, 0.0F, 2, 8, 1);
        this.SUPPORT_BEAM_1.setRotationPoint(2.0F, 16.0F, -8.0F);
        this.SUPPORT_BEAM_1.setTextureSize(128, 128);
        this.SUPPORT_BEAM_1.mirror = true;
        this.setRotation(this.SUPPORT_BEAM_1, 0.0F, 0.0F, 0.0F);
        this.SUPPORT_BEAM_2 = new ModelRenderer(this, 10, 19);
        this.SUPPORT_BEAM_2.addBox(0.0F, 0.0F, 0.0F, 2, 8, 1);
        this.SUPPORT_BEAM_2.setRotationPoint(2.0F, 16.0F, 7.0F);
        this.SUPPORT_BEAM_2.setTextureSize(128, 128);
        this.SUPPORT_BEAM_2.mirror = true;
        this.setRotation(this.SUPPORT_BEAM_2, 0.0F, 0.0F, 0.0F);
        this.MAIN_CHAMBER_ROTATES = new ModelRenderer(this, 0, 29);
        this.MAIN_CHAMBER_ROTATES.addBox(-2.0F, -2.0F, -7.0F, 4, 4, 14);
        this.MAIN_CHAMBER_ROTATES.setRotationPoint(3.0F, 17.0F, 0.0F);
        this.MAIN_CHAMBER_ROTATES.setTextureSize(128, 128);
        this.MAIN_CHAMBER_ROTATES.mirror = true;
        this.setRotation(this.MAIN_CHAMBER_ROTATES, 0.0F, 0.0F, 0.0F);
        this.MAGNET_1_ROTATES = new ModelRenderer(this, 25, 19);
        this.MAGNET_1_ROTATES.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4);
        this.MAGNET_1_ROTATES.setRotationPoint(3.0F, 17.0F, 3.0F);
        this.MAGNET_1_ROTATES.setTextureSize(128, 128);
        this.MAGNET_1_ROTATES.mirror = true;
        this.setRotation(this.MAGNET_1_ROTATES, 0.0F, 0.0F, 0.0F);
        this.MAGNET_2_ROTATES = new ModelRenderer(this, 25, 19);
        this.MAGNET_2_ROTATES.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4);
        this.MAGNET_2_ROTATES.setRotationPoint(3.0F, 17.0F, -3.0F);
        this.MAGNET_2_ROTATES.setTextureSize(128, 128);
        this.MAGNET_2_ROTATES.mirror = true;
        this.setRotation(this.MAGNET_2_ROTATES, 0.0F, 0.0F, 0.0F);
        this.ENERGY_PLUG = new ModelRenderer(this, 58, 0);
        this.ENERGY_PLUG.addBox(0.0F, 0.0F, 0.0F, 4, 12, 11);
        this.ENERGY_PLUG.setRotationPoint(-6.0F, 12.0F, -3.0F);
        this.ENERGY_PLUG.setTextureSize(128, 128);
        this.ENERGY_PLUG.mirror = true;
        this.setRotation(this.ENERGY_PLUG, 0.0F, 0.0F, 0.0F);
        this.KEYBOARD_SUPPORT = new ModelRenderer(this, 0, 48);
        this.KEYBOARD_SUPPORT.addBox(0.0F, 0.0F, 0.0F, 1, 12, 2);
        this.KEYBOARD_SUPPORT.setRotationPoint(-6.0F, 11.0F, -6.0F);
        this.KEYBOARD_SUPPORT.setTextureSize(128, 128);
        this.KEYBOARD_SUPPORT.mirror = true;
        this.setRotation(this.KEYBOARD_SUPPORT, 0.0F, 1.570796F, 0.0F);
        this.KEYBOARD = new ModelRenderer(this, 7, 48);
        this.KEYBOARD.addBox(0.0F, 0.0F, 0.0F, 4, 1, 12);
        this.KEYBOARD.setRotationPoint(3.0F, 11.0F, -8.0F);
        this.KEYBOARD.setTextureSize(128, 128);
        this.KEYBOARD.mirror = true;
        this.setRotation(this.KEYBOARD, 0.0F, -1.570796F, -0.5235988F);
    }

    public void render(float rotation, float f5) {
        this.MAIN_BASE.render(f5);
        this.CORNER_SUPPORT_1.render(f5);
        this.CORNER_SUPPORT_2.render(f5);
        this.CORNER_SUPPORT_3.render(f5);
        this.CORNER_SUPPORT_4.render(f5);
        this.SUPPORT_BEAM_1.render(f5);
        this.SUPPORT_BEAM_2.render(f5);
        this.MAIN_CHAMBER_ROTATES.rotateAngleZ = rotation;
        this.MAIN_CHAMBER_ROTATES.render(f5);
        this.MAGNET_1_ROTATES.rotateAngleZ = rotation;
        this.MAGNET_1_ROTATES.render(f5);
        this.MAGNET_2_ROTATES.rotateAngleZ = rotation;
        this.MAGNET_2_ROTATES.render(f5);
        this.ENERGY_PLUG.render(f5);
        this.KEYBOARD_SUPPORT.render(f5);
        this.KEYBOARD.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
