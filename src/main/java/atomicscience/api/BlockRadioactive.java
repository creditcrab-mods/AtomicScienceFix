package atomicscience.api;

import atomicscience.api.poison.PoisonRadiation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

public class BlockRadioactive extends Block {

  public static int RECOMMENDED_ID = 3768;
  public boolean canSpread;
  public float radius;
  public int amplifier;
  public boolean canWalkPoison;
  public boolean isRandomlyRadioactive;
  private IIcon iconTop;
  private IIcon iconBottom;

  public BlockRadioactive(Material material) {
    super(material);
    this.canSpread = true;
    this.radius = 5.0F;
    this.amplifier = 2;
    this.canWalkPoison = true;
    this.isRandomlyRadioactive = true;
    this.setTickRandomly(true);
    this.setHardness(0.2F);
    this.setLightLevel(0.1F);
  }

  public BlockRadioactive() { this(Material.rock); }

  @Override
  public IIcon getIcon(int side, int metadata) {
    return side == 1 ? this.iconTop
                     : (side == 0 ? this.iconBottom : this.blockIcon);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister iconRegister) {
    super.registerBlockIcons(iconRegister);
    this.blockIcon = iconRegister.registerIcon(
        this.getUnlocalizedName().replace("tile.", ""));
    this.iconTop = iconRegister.registerIcon(
        this.getUnlocalizedName().replace("tile.", "") + "_top");
    this.iconBottom = iconRegister.registerIcon(
        this.getUnlocalizedName().replace("tile.", "") + "_bottom");
  }

  @Override
  public void onBlockClicked(World world, int x, int y, int z,
                             EntityPlayer par5EntityPlayer) {
    if ((double)world.rand.nextFloat() > 0.8D) {
      this.updateTick(world, x, y, z, world.rand);
    }
  }

  @Override
  public void updateTick(World world, int x, int y, int z, Random rand) {
    if (!world.isRemote) {
      if (this.isRandomlyRadioactive) {
        AxisAlignedBB i = AxisAlignedBB.getBoundingBox(
            (double)((float)x - this.radius), (double)((float)y - this.radius),
            (double)((float)z - this.radius), (double)((float)x + this.radius),
            (double)((float)y + this.radius), (double)((float)z + this.radius));
        List<EntityLiving> newX =
            world.getEntitiesWithinAABB(EntityLiving.class, i);

        for (EntityLiving newZ : newX) {
          PoisonRadiation.INSTANCE.poisonEntity(
              new Vector3((double)x, (double)y, (double)z), newZ,
              this.amplifier);
        }
      }

      if (this.canSpread) {
        for (int var11 = 0; var11 < 4; ++var11) {
          int xOffset = x + rand.nextInt(3) - 1;
          int yOffset = y + rand.nextInt(5) - 3;
          int zOffset = z + rand.nextInt(3) - 1;
          Block block = world.getBlock(xOffset, yOffset, zOffset);
          if ((double)rand.nextFloat() > 0.4D &&
              (block == Blocks.farmland || block == Blocks.grass)) {
            world.setBlock(xOffset, yOffset, zOffset, this);
          }
        }

        if ((double)rand.nextFloat() > 0.85D) {
          world.setBlock(x, y, z, Blocks.mycelium);
        }
      }
    }
  }

  @Override
  public void onEntityWalking(World par1World, int x, int y, int z,
                           Entity par5Entity) {
    if (par5Entity instanceof EntityLivingBase && this.canWalkPoison) {
      PoisonRadiation.INSTANCE.poisonEntity(
          new Vector3((double)x, (double)y, (double)z),
          (EntityLivingBase) par5Entity);
    }
  }

  public int func_71925_a(Random par1Random) { return 0; }

  @SideOnly(Side.CLIENT)
  public void func_71862_a(World world, int x, int y, int z,
                           Random par5Random) {
    if (Minecraft.getMinecraft().gameSettings.particleSetting == 0) {
      byte radius = 3;

      for (int i = 0; i < 2; ++i) {
        Vector3 diDian = new Vector3((double)x, (double)y, (double)z);
        diDian.x += Math.random() * (double)radius - (double)(radius / 2);
        diDian.y += Math.random() * (double)radius - (double)(radius / 2);
        diDian.z += Math.random() * (double)radius - (double)(radius / 2);
        EntitySmokeFX fx = new EntitySmokeFX(
            world, diDian.x, diDian.y, diDian.z, (Math.random() - 0.5D) / 2.0D,
            (Math.random() - 0.5D) / 2.0D, (Math.random() - 0.5D) / 2.0D);
        fx.setRBGColorF(0.2F, 0.8F, 0.0F);
        Minecraft.getMinecraft().effectRenderer.addEffect(fx);
      }
    }
  }
}
