package atomicscience.hecheng;

import java.util.Random;

import atomicscience.api.IElectromagnet;
import atomicscience.api.Plasma;
import atomicscience.hecheng.TElectromagnetBoiler;
import atomicscience.jiqi.BBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;

public class BPlasma extends BBase implements Plasma.IPlasma {
    public BPlasma() {
        super("plasma", Material.lava);
        this.setLightLevel(0.5F);
        super.textureName = "portal";
    }

    @Override
    public void onBlockAdded(World worldObj, int x, int y, int z) {
        if (!worldObj.isRemote) {
            Vector3 diDian = new Vector3((double) x, (double) y, (double) z);
            int xinMeta = Math.max(diDian.getBlockMetadata(worldObj) - 1, 0);
            if (xinMeta <= 0) {
                diDian.setBlock(worldObj, Blocks.fire);
                return;
            }

            for (int i = 0; i < 6; ++i) {
                if ((double) worldObj.rand.nextFloat() <= 0.8D) {
                    Vector3 zhaoDiDian = diDian.clone();
                    zhaoDiDian.modifyPositionFromSide(ForgeDirection.getOrientation(i));
                    if (this.canPlace(
                            worldObj,
                            zhaoDiDian.intX(),
                            zhaoDiDian.intY(),
                            zhaoDiDian.intZ()
                        )) {
                        Block tileEntity = zhaoDiDian.getBlock(worldObj);
                        if (tileEntity != this) {
                            zhaoDiDian.setBlock(worldObj, this, xinMeta);
                        }
                    } else {
                        TileEntity var10 = zhaoDiDian.getTileEntity(worldObj);
                        if (var10 instanceof TElectromagnetBoiler) {
                            ((TElectromagnetBoiler) var10)
                                .setTemperature(
                                    ((TElectromagnetBoiler) var10).getTemperature()
                                    + 100.0F
                                );
                        }
                    }
                }
            }

            worldObj.scheduleBlockUpdate(
                x,
                y,
                z,
                this,
                (new Vector3((double) x, (double) y, (double) z))
                        .getBlockMetadata(worldObj)
                    * 5
            );
        }
    }

    @Override
    public void updateTick(World worldObj, int x, int y, int z, Random par5Random) {
        Vector3 diDian = new Vector3((double) x, (double) y, (double) z);
        diDian.setBlock(worldObj, Blocks.fire);
    }

    @Override
    public void
    onNeighborBlockChange(World worldObj, int x, int y, int z, Block neighborID) {
        if (neighborID != this && neighborID != Blocks.fire) {
            worldObj.scheduleBlockUpdate(
                x,
                y,
                z,
                this,
                (new Vector3((double) x, (double) y, (double) z))
                        .getBlockMetadata(worldObj)
                    * 5
            );
        }
    }

    @Override
    public void spawn(World world, int x, int y, int z, byte strength) {
        if (this.canPlace(world, x, y, z)) {
            world.setBlock(x, y, z, this, strength, 3);
        }
    }

    @Override
    public boolean canPlace(World world, int x, int y, int z) {
        Vector3 position = new Vector3((double) x, (double) y, (double) z);
        Block block = position.getBlock(world);
        return block == null
            || block != Blocks.bedrock && !(block instanceof IElectromagnet)
            && block != Blocks.iron_block;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean
    isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        return false;
    }

    @Override
    public AxisAlignedBB
    getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    @Override
    public int tickRate(World par1World) {
        return 8;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public void onEntityCollidedWithBlock(
        World par1World, int par2, int par3, int par4, Entity par5Entity
    ) {
        if (par5Entity instanceof EntityLivingBase) {
            if (par5Entity.isImmuneToFire()) {
                par5Entity.attackEntityFrom(DamageSource.magic, 1073741823);
            } else {
                par5Entity.attackEntityFrom(DamageSource.inFire, 1073741823);
            }
        } else {
            par5Entity.setDead();
        }
    }

    @Override
    public ItemStack
    getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return null;
    }
}
