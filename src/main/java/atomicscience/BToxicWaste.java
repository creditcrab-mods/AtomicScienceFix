package atomicscience;

import java.util.Random;

import atomicscience.api.poison.PoisonRadiation;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import universalelectricity.core.vector.Vector3;

public class BToxicWaste extends BlockFluidClassic {
    public BToxicWaste(String name) {
        super(AtomicScience.FLUID_TOXIC_WASTE, Material.water);
        this.setBlockName("atomicscience:" + name);
        this.setCreativeTab(TabAS.INSTANCE);
        this.setHardness(100.0F);
        this.setLightOpacity(3);
    }

    @Override
    public int tickRate(World par1World) {
        return 20;
    }

    @Override
    public void
    randomDisplayTick(World par1World, int x, int y, int z, Random par5Random) {
        super.randomDisplayTick(par1World, x, y, z, par5Random);
        if (par5Random.nextInt(100) == 0) {
            double d5 = (double) ((float) x + par5Random.nextFloat());
            double d7 = (double) y + this.maxY;
            double d6 = (double) ((float) z + par5Random.nextFloat());
            par1World.spawnParticle("suspended", d5, d7, d6, 0.0D, 0.0D, 0.0D);
            par1World.playSound(
                d5,
                d7,
                d6,
                "liquid.lavapop",
                0.2F + par5Random.nextFloat() * 0.2F,
                0.9F + par5Random.nextFloat() * 0.15F,
                false
            );
        }

        if (par5Random.nextInt(200) == 0) {
            par1World.playSound(
                (double) x,
                (double) y,
                (double) z,
                "liquid.lava",
                0.2F + par5Random.nextFloat() * 0.2F,
                0.9F + par5Random.nextFloat() * 0.15F,
                false
            );
        }
    }

    @Override
    public void
    onEntityCollidedWithBlock(World par1World, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            entity.attackEntityFrom(DamageSource.wither, 3);
            PoisonRadiation.INSTANCE.poisonEntity(
                new Vector3((double) x, (double) y, (double) z), (EntityLivingBase) entity, 4
            );
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("atomicscience:toxicWaste");
        AtomicScience.FLUID_TOXIC_WASTE.setIcons(this.blockIcon);

        AtomicScience.FLUID_URANIUM_HEXAFLOURIDE.setIcons(
            iconRegister.registerIcon("atomicscience:uraniumHexafluoride")
        );
        if (!Loader.isModLoaded("Railcraft")) {
            AtomicScience.FLUID_STEAM.setIcons(
                iconRegister.registerIcon("atomicscience:steam")
            );
        }
        if (!Loader.isModLoaded("Mekanism")) {
            AtomicScience.FLUID_DEUTERIUM.setIcons(
                iconRegister.registerIcon("atomicscience:deuterium")
            );
            AtomicScience.FLUID_TRITIUM.setIcons(
                iconRegister.registerIcon("atomicscience:tritium")
            );
        }
    }
}
