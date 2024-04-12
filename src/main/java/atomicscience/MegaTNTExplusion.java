package atomicscience;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MegaTNTExplusion extends Explosion {
    private Random explosionRAND = new Random();
    private World worldObj;

    public MegaTNTExplusion(
        World world, Entity par2Entity, double par3, double par5, double par7, float par9
    ) {
        super(world, par2Entity, par3, par5, par7, par9);
        this.worldObj = world;
        super.isFlaming = true;
    }

    @Override
    public void doExplosionB(boolean par1) {
        super.doExplosionB(par1);
        for (Object chunkposition_obj : super.affectedBlockPositions) {
            ChunkPosition chunkposition = (ChunkPosition) chunkposition_obj;
            Block id = this.worldObj.getBlock(
                chunkposition.chunkPosX, chunkposition.chunkPosY, chunkposition.chunkPosZ
            );
            if (id == Blocks.air && id.isOpaqueCube()
                && this.explosionRAND.nextInt(3) == 0) {
                this.worldObj.setBlock(
                    chunkposition.chunkPosX,
                    chunkposition.chunkPosY,
                    chunkposition.chunkPosZ,
                    AtomicScience.blockRadioactive
                );
            }
        }
    }
}
