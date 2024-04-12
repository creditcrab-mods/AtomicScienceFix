package atomicscience.fenlie;

import java.util.Random;

import atomicscience.AtomicScience;
import atomicscience.api.IFissileMaterial;
import atomicscience.jiqi.BBase;
import atomicscience.render.RH;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BFissionReactor extends BBase {
    public BFissionReactor() {
        super("fissionReactor");
    }

    @Override
    public void onBlockAdded(World par1World, int x, int y, int z) {
        super.onBlockAdded(par1World, x, y, z);
        TFissionReactor tileEntity = (TFissionReactor) par1World.getTileEntity(x, y, z);
        tileEntity.updatePositionStatus();
    }

    @Override
    public void onNeighborBlockChange(World par1World, int x, int y, int z, Block block) {
        TFissionReactor tileEntity = (TFissionReactor) par1World.getTileEntity(x, y, z);
        tileEntity.updatePositionStatus();
    }

    @Override
    public boolean onMachineActivated(
        World world,
        int x,
        int y,
        int z,
        EntityPlayer entityPlayer,
        int side,
        float hitX,
        float hitY,
        float hitZ
    ) {
        if (!world.isRemote) {
            TFissionReactor tileEntity = (TFissionReactor) world.getTileEntity(x, y, z);
            tileEntity = tileEntity.zhaoZuiDi();
            if (!entityPlayer.isSneaking()) {
                if (entityPlayer.inventory.getCurrentItem() != null
                    && entityPlayer.inventory.getCurrentItem().getItem()
                        == AtomicScience.itThermometer) {
                    return false;
                }

                if (tileEntity.getStackInSlot(0) != null) {
                    EntityItem itemStack1 = new EntityItem(
                        world,
                        entityPlayer.posX,
                        entityPlayer.posY,
                        entityPlayer.posZ,
                        tileEntity.getStackInSlot(0)
                    );
                    Random random = new Random();
                    float var13 = 0.05F;
                    itemStack1.motionX = (double) ((float) random.nextGaussian() * var13);
                    itemStack1.motionY
                        = (double) ((float) random.nextGaussian() * var13 + 0.2F);
                    itemStack1.motionZ = (double) ((float) random.nextGaussian() * var13);
                    itemStack1.delayBeforeCanPickup = 0;
                    world.spawnEntityInWorld(itemStack1);
                    tileEntity.setInventorySlotContents(0, (ItemStack) null);
                    return true;
                }

                if (entityPlayer.inventory.getCurrentItem() != null
                    && entityPlayer.inventory.getCurrentItem().getItem()
                            instanceof IFissileMaterial) {
                    ItemStack itemStack = entityPlayer.inventory.getCurrentItem().copy();
                    itemStack.stackSize = 1;
                    tileEntity.setInventorySlotContents(0, itemStack);
                    entityPlayer.inventory.decrStackSize(
                        entityPlayer.inventory.currentItem, 1
                    );
                    return true;
                }
            }

            entityPlayer.openGui(
                AtomicScience.instance,
                0,
                world,
                tileEntity.xCoord,
                tileEntity.yCoord,
                tileEntity.zCoord
            );
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return RH.BLOCK_RENDER_ID;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int meta) {
        return new TFissionReactor();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
