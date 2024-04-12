package atomicscience.hecheng;

import atomicscience.AtomicScience;
import atomicscience.jiqi.BBase;
import atomicscience.render.RH;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class BFusionReactor extends BBase {
    public BFusionReactor() {
        super("fusionReactor");
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
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (entityPlayer.inventory.getCurrentItem() != null) {
            if (AtomicScience.isFusionFuel(entityPlayer.inventory.getCurrentItem())) {
                int deuterium = entityPlayer.inventory.getCurrentItem().stackSize * 200;
                int wouldFill
                    = ((TFusionReactor) tileEntity)
                          .fill(
                              null,
                              new FluidStack(AtomicScience.FLUID_DEUTERIUM, deuterium),
                              false
                          );
                int remainder = wouldFill % 200;
                ((TFusionReactor) tileEntity)
                    .fill(
                        null,
                        new FluidStack(
                            AtomicScience.FLUID_DEUTERIUM, deuterium - remainder
                        ),
                        true
                    );
                entityPlayer.inventory.getCurrentItem().stackSize -= wouldFill / 200;
                if (entityPlayer.inventory.getCurrentItem().stackSize <= 0) {
                    entityPlayer.inventory.setInventorySlotContents(
                        entityPlayer.inventory.currentItem, (ItemStack) null
                    );
                }
                return true;
            } else if (entityPlayer.inventory.getCurrentItem().getItem() == AtomicScience.itCellTritium) {
                int tritium = entityPlayer.inventory.getCurrentItem().stackSize * 200;
                int wouldFill
                    = ((TFusionReactor) tileEntity)
                          .fill(
                              null,
                              new FluidStack(AtomicScience.FLUID_TRITIUM, tritium),
                              false
                          );
                int remainder = wouldFill % 200;
                ((TFusionReactor) tileEntity)
                    .fill(
                        null,
                        new FluidStack(AtomicScience.FLUID_TRITIUM, tritium - remainder),
                        true
                    );
                entityPlayer.inventory.getCurrentItem().stackSize -= wouldFill / 200;
                if (entityPlayer.inventory.getCurrentItem().stackSize <= 0) {
                    entityPlayer.inventory.setInventorySlotContents(
                        entityPlayer.inventory.currentItem, (ItemStack) null
                    );
                }
                return true;
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return RH.BLOCK_RENDER_ID;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int meta) {
        return new TFusionReactor();
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
