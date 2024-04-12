package atomicscience.fenlie;

import java.util.Random;

import atomicscience.SoundManager;
import atomicscience.jiqi.BBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;

public class BSiren extends BBase {
    public BSiren() {
        super("siren");
        this.textureName = "atomicscience:siren";
    }

    @Override
    public void onBlockAdded(World par1World, int x, int y, int z) {
        par1World.scheduleBlockUpdate(x, y, z, this, 1);
    }

    @Override
    public void onNeighborBlockChange(World par1World, int x, int y, int z, Block block) {
        par1World.scheduleBlockUpdate(x, y, z, this, 1);
    }

    @Override
    public void updateTick(World worldObj, int x, int y, int z, Random par5Random) {
        int metadata = worldObj.getBlockMetadata(x, y, z);
        if (worldObj.getBlockPowerInput(x, y, z) > 0) {
            float volume = 0.5F;

            for (int i = 0; i < 6; ++i) {
                Vector3 pos = new Vector3((double) x, (double) y, (double) z);
                pos.modifyPositionFromSide(ForgeDirection.getOrientation(i));
                Block block = pos.getBlock(worldObj);
                if (block == this) {
                    volume *= 1.5F;
                }
            }

            worldObj.playSoundEffect(
                (double) x,
                (double) y,
                (double) z,
                SoundManager.ALARM,
                volume,
                1.0F - 0.18F * ((float) metadata / 15.0F)
            );
            worldObj.scheduleBlockUpdate(x, y, z, this, 30);
        }
    }

    @Override
    public boolean onUseWrench(
        World par1World,
        int x,
        int y,
        int z,
        EntityPlayer par5EntityPlayer,
        int side,
        float hitX,
        float hitY,
        float hitZ
    ) {
        int metadata = par1World.getBlockMetadata(x, y, z) + 1;
        if (metadata < 0) {
            metadata = 15;
        }

        par1World.setBlockMetadataWithNotify(x, y, z, metadata, 2);
        return true;
    }

    @Override
    public boolean onSneakUseWrench(
        World par1World,
        int x,
        int y,
        int z,
        EntityPlayer par5EntityPlayer,
        int side,
        float hitX,
        float hitY,
        float hitZ
    ) {
        int metadata = par1World.getBlockMetadata(x, y, z) - 1;
        if (metadata > 15) {
            metadata = 0;
        }

        par1World.setBlockMetadataWithNotify(x, y, z, metadata, 2);
        return true;
    }

    @Override
    public int tickRate(World world) {
        return 20;
    }
}
