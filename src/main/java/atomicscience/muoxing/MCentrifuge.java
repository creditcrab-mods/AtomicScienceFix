package atomicscience.muoxing;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class MCentrifuge extends ModelBase {
    ModelRenderer A;
    ModelRenderer B;
    ModelRenderer C;
    ModelRenderer D;
    ModelRenderer E;
    ModelRenderer F;
    ModelRenderer G;
    ModelRenderer H;
    ModelRenderer I;
    ModelRenderer JROT;
    ModelRenderer KROT;
    ModelRenderer LROT;
    ModelRenderer MROT;

    public MCentrifuge() {
        super.textureWidth = 128;
        super.textureHeight = 128;
        this.A = new ModelRenderer(this, 0, 0);
        this.A.addBox(-8.0F, 0.0F, -8.0F, 16, 1, 16);
        this.A.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.A.setTextureSize(64, 32);
        this.A.mirror = true;
        this.setRotation(this.A, 0.0F, 0.0F, 0.0F);
        this.B = new ModelRenderer(this, 0, 19);
        this.B.addBox(-6.0F, 0.0F, -6.0F, 12, 1, 12);
        this.B.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.B.setTextureSize(64, 32);
        this.B.mirror = true;
        this.setRotation(this.B, 0.0F, 0.0F, 0.0F);
        this.C = new ModelRenderer(this, 0, 43);
        this.C.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2);
        this.C.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.C.setTextureSize(64, 32);
        this.C.mirror = true;
        this.setRotation(this.C, 0.0F, 0.0F, 0.0F);
        this.D = new ModelRenderer(this, 67, 0);
        this.D.addBox(-4.0F, 0.0F, -4.0F, 8, 4, 8);
        this.D.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.D.setTextureSize(64, 32);
        this.D.mirror = true;
        this.setRotation(this.D, 0.0F, 0.0F, 0.0F);
        this.E = new ModelRenderer(this, 0, 34);
        this.E.addBox(-1.0F, 0.0F, 4.0F, 2, 6, 1);
        this.E.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.E.setTextureSize(64, 32);
        this.E.mirror = true;
        this.setRotation(this.E, 0.0F, 0.7853982F, 0.0F);
        this.F = new ModelRenderer(this, 0, 34);
        this.F.addBox(-1.0F, 0.0F, 4.0F, 2, 6, 1);
        this.F.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.F.setTextureSize(64, 32);
        this.F.mirror = true;
        this.setRotation(this.F, 0.0F, -0.7853982F, 0.0F);
        this.G = new ModelRenderer(this, 67, 13);
        this.G.addBox(-4.0F, 0.0F, -8.0F, 8, 12, 4);
        this.G.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.G.setTextureSize(64, 32);
        this.G.mirror = true;
        this.setRotation(this.G, 0.0F, 0.0F, 0.0F);
        this.H = new ModelRenderer(this, 67, 31);
        this.H.addBox(-4.0F, 0.0F, -4.0F, 8, 7, 2);
        this.H.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.H.setTextureSize(64, 32);
        this.H.mirror = true;
        this.setRotation(this.H, -1.064651F, 0.0F, 0.0F);
        this.I = new ModelRenderer(this, 9, 43);
        this.I.addBox(-2.0F, -3.0F, -2.0F, 4, 2, 4);
        this.I.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.I.setTextureSize(64, 32);
        this.I.mirror = true;
        this.setRotation(this.I, 0.0F, 0.0F, 0.0F);
        this.JROT = new ModelRenderer(this, 8, 34);
        this.JROT.addBox(1.0F, 0.0F, 1.0F, 1, 2, 1);
        this.JROT.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.JROT.setTextureSize(64, 32);
        this.JROT.mirror = true;
        this.setRotation(this.JROT, 0.0F, 0.0F, 0.0F);
        this.KROT = new ModelRenderer(this, 8, 34);
        this.KROT.addBox(-2.0F, 0.0F, 1.0F, 1, 2, 1);
        this.KROT.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.KROT.setTextureSize(64, 32);
        this.KROT.mirror = true;
        this.setRotation(this.KROT, 0.0F, 0.0F, 0.0F);
        this.LROT = new ModelRenderer(this, 8, 34);
        this.LROT.addBox(1.0F, 0.0F, -2.0F, 1, 2, 1);
        this.LROT.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.LROT.setTextureSize(64, 32);
        this.LROT.mirror = true;
        this.setRotation(this.LROT, 0.0F, 0.0F, 0.0F);
        this.MROT = new ModelRenderer(this, 8, 34);
        this.MROT.addBox(-2.0F, 0.0F, -2.0F, 1, 2, 1);
        this.MROT.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.MROT.setTextureSize(64, 32);
        this.MROT.mirror = true;
        this.setRotation(this.MROT, 0.0F, 0.0F, 0.0F);
    }

    public void render(float xuanZhuan, float f5) {
        this.A.render(f5);
        this.B.render(f5);
        this.C.render(f5);
        this.D.render(f5);
        this.E.render(f5);
        this.F.render(f5);
        this.G.render(f5);
        this.H.render(f5);
        this.I.render(f5);
        this.JROT.rotateAngleY = xuanZhuan;
        this.JROT.render(f5);
        this.KROT.rotateAngleY = xuanZhuan;
        this.KROT.render(f5);
        this.LROT.rotateAngleY = xuanZhuan;
        this.LROT.render(f5);
        this.MROT.rotateAngleY = xuanZhuan;
        this.MROT.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
