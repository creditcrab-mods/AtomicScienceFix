package atomicscience.muoxing;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class MNuclearBoiler extends ModelBase {
    ModelRenderer BASE;
    ModelRenderer THERMAL_DISPLAY;
    ModelRenderer SUPPORT;
    ModelRenderer RAD_SHIELD_PLATE_1;
    ModelRenderer RAD_SHIELD_PLATE_2;
    ModelRenderer RAD_SHIELD_PLATE_3;
    ModelRenderer FUEL_BAR_SUPPORT_1_ROTATES;
    ModelRenderer FUEL_BAR_SUPPORT_2_ROTATES;
    ModelRenderer FUEL_BAR_1_ROTATES;
    ModelRenderer FUEL_BAR_2_ROTATES;
    ModelRenderer TOP_SUPPORT_A;
    ModelRenderer TOP_SUPPORT_B;
    ModelRenderer TOP_SUPPORT_C;

    public MNuclearBoiler() {
        super.textureWidth = 128;
        super.textureHeight = 128;
        this.BASE = new ModelRenderer(this, 0, 0);
        this.BASE.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16);
        this.BASE.setRotationPoint(-8.0F, 23.0F, -8.0F);
        this.BASE.setTextureSize(128, 128);
        this.BASE.mirror = true;
        this.setRotation(this.BASE, 0.0F, 0.0F, 0.0F);
        this.THERMAL_DISPLAY = new ModelRenderer(this, 0, 37);
        this.THERMAL_DISPLAY.addBox(0.0F, 0.0F, 0.0F, 8, 11, 3);
        this.THERMAL_DISPLAY.setRotationPoint(-4.0F, 13.0F, -3.0F);
        this.THERMAL_DISPLAY.setTextureSize(128, 128);
        this.THERMAL_DISPLAY.mirror = true;
        this.setRotation(this.THERMAL_DISPLAY, -0.4363323F, 0.0F, 0.0F);
        this.SUPPORT = new ModelRenderer(this, 0, 19);
        this.SUPPORT.addBox(0.0F, 0.0F, 0.0F, 14, 3, 13);
        this.SUPPORT.setRotationPoint(-7.0F, 20.0F, -5.0F);
        this.SUPPORT.setTextureSize(128, 128);
        this.SUPPORT.mirror = true;
        this.setRotation(this.SUPPORT, 0.0F, 0.0F, 0.0F);
        this.RAD_SHIELD_PLATE_1 = new ModelRenderer(this, 46, 37);
        this.RAD_SHIELD_PLATE_1.addBox(0.0F, 0.0F, 0.0F, 8, 14, 1);
        this.RAD_SHIELD_PLATE_1.setRotationPoint(-4.0F, 9.0F, 0.0F);
        this.RAD_SHIELD_PLATE_1.setTextureSize(128, 128);
        this.RAD_SHIELD_PLATE_1.mirror = true;
        this.setRotation(this.RAD_SHIELD_PLATE_1, 0.0F, 0.0F, 0.0F);
        this.RAD_SHIELD_PLATE_2 = new ModelRenderer(this, 24, 37);
        this.RAD_SHIELD_PLATE_2.addBox(0.0F, 0.0F, 0.0F, 8, 14, 2);
        this.RAD_SHIELD_PLATE_2.setRotationPoint(-8.0F, 9.0F, 7.0F);
        this.RAD_SHIELD_PLATE_2.setTextureSize(128, 128);
        this.RAD_SHIELD_PLATE_2.mirror = true;
        this.setRotation(this.RAD_SHIELD_PLATE_2, 0.0F, 1.064651F, 0.0F);
        this.RAD_SHIELD_PLATE_3 = new ModelRenderer(this, 24, 37);
        this.RAD_SHIELD_PLATE_3.addBox(0.0F, 0.0F, -2.0F, 8, 14, 2);
        this.RAD_SHIELD_PLATE_3.setRotationPoint(8.0F, 9.0F, 7.0F);
        this.RAD_SHIELD_PLATE_3.setTextureSize(128, 128);
        this.RAD_SHIELD_PLATE_3.mirror = true;
        this.setRotation(this.RAD_SHIELD_PLATE_3, 0.0F, 2.094395F, 0.0F);
        this.FUEL_BAR_SUPPORT_1_ROTATES = new ModelRenderer(this, 75, 13);
        this.FUEL_BAR_SUPPORT_1_ROTATES.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4);
        this.FUEL_BAR_SUPPORT_1_ROTATES.setRotationPoint(3.0F, 19.0F, 5.0F);
        this.FUEL_BAR_SUPPORT_1_ROTATES.setTextureSize(128, 128);
        this.FUEL_BAR_SUPPORT_1_ROTATES.mirror = true;
        this.setRotation(this.FUEL_BAR_SUPPORT_1_ROTATES, 0.0F, 0.5235988F, 0.0F);
        this.FUEL_BAR_SUPPORT_2_ROTATES = new ModelRenderer(this, 75, 13);
        this.FUEL_BAR_SUPPORT_2_ROTATES.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4);
        this.FUEL_BAR_SUPPORT_2_ROTATES.setRotationPoint(-3.0F, 19.0F, 5.0F);
        this.FUEL_BAR_SUPPORT_2_ROTATES.setTextureSize(128, 128);
        this.FUEL_BAR_SUPPORT_2_ROTATES.mirror = true;
        this.setRotation(this.FUEL_BAR_SUPPORT_2_ROTATES, 0.0F, 1.047198F, 0.0F);
        this.FUEL_BAR_1_ROTATES = new ModelRenderer(this, 65, 13);
        this.FUEL_BAR_1_ROTATES.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2);
        this.FUEL_BAR_1_ROTATES.setRotationPoint(3.0F, 10.0F, 5.0F);
        this.FUEL_BAR_1_ROTATES.setTextureSize(128, 128);
        this.FUEL_BAR_1_ROTATES.mirror = true;
        this.setRotation(this.FUEL_BAR_1_ROTATES, 0.0F, 0.5235988F, 0.0F);
        this.FUEL_BAR_2_ROTATES = new ModelRenderer(this, 65, 13);
        this.FUEL_BAR_2_ROTATES.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2);
        this.FUEL_BAR_2_ROTATES.setRotationPoint(-3.0F, 10.0F, 5.0F);
        this.FUEL_BAR_2_ROTATES.setTextureSize(128, 128);
        this.FUEL_BAR_2_ROTATES.mirror = true;
        this.setRotation(this.FUEL_BAR_2_ROTATES, 0.0F, 1.047198F, 0.0F);
        this.TOP_SUPPORT_A = new ModelRenderer(this, 65, 6);
        this.TOP_SUPPORT_A.addBox(-4.0F, 0.0F, -4.0F, 5, 1, 4);
        this.TOP_SUPPORT_A.setRotationPoint(6.0F, 9.0F, 6.0F);
        this.TOP_SUPPORT_A.setTextureSize(128, 128);
        this.TOP_SUPPORT_A.mirror = true;
        this.setRotation(this.TOP_SUPPORT_A, 0.0F, 0.5235988F, 0.0F);
        this.TOP_SUPPORT_B = new ModelRenderer(this, 65, 6);
        this.TOP_SUPPORT_B.addBox(0.0F, 0.0F, 0.0F, 5, 1, 4);
        this.TOP_SUPPORT_B.setRotationPoint(-5.0F, 9.0F, 2.0F);
        this.TOP_SUPPORT_B.setTextureSize(128, 128);
        this.TOP_SUPPORT_B.mirror = true;
        this.setRotation(this.TOP_SUPPORT_B, 0.0F, -0.5235988F, 0.0F);
        this.TOP_SUPPORT_C = new ModelRenderer(this, 65, 0);
        this.TOP_SUPPORT_C.addBox(0.0F, 0.0F, 0.0F, 12, 1, 4);
        this.TOP_SUPPORT_C.setRotationPoint(-6.0F, 9.0F, 4.0F);
        this.TOP_SUPPORT_C.setTextureSize(128, 128);
        this.TOP_SUPPORT_C.mirror = true;
        this.setRotation(this.TOP_SUPPORT_C, 0.0F, 0.0F, 0.0F);
    }

    public void render(float rotation, float f5) {
        this.BASE.render(f5);
        this.THERMAL_DISPLAY.render(f5);
        this.SUPPORT.render(f5);
        this.RAD_SHIELD_PLATE_1.render(f5);
        this.RAD_SHIELD_PLATE_2.render(f5);
        this.RAD_SHIELD_PLATE_3.render(f5);
        this.FUEL_BAR_SUPPORT_1_ROTATES.rotateAngleY = rotation;
        this.FUEL_BAR_SUPPORT_1_ROTATES.render(f5);
        this.FUEL_BAR_SUPPORT_2_ROTATES.rotateAngleY = rotation;
        this.FUEL_BAR_SUPPORT_2_ROTATES.render(f5);
        this.FUEL_BAR_1_ROTATES.rotateAngleY = rotation;
        this.FUEL_BAR_1_ROTATES.render(f5);
        this.FUEL_BAR_2_ROTATES.rotateAngleY = rotation;
        this.FUEL_BAR_2_ROTATES.render(f5);
        this.TOP_SUPPORT_A.render(f5);
        this.TOP_SUPPORT_B.render(f5);
        this.TOP_SUPPORT_C.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
