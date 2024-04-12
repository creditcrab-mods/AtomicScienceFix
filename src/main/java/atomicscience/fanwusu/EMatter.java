package atomicscience.fanwusu;

import java.util.List;

import atomicscience.AtomicScience;
import atomicscience.SoundManager;
import atomicscience.api.IElectromagnet;
import atomicscience.api.poison.PoisonRadiation;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;

public class EMatter extends Entity implements IEntityAdditionalSpawnData {
    // private static final int FANG_XIANG_DATA = 20;
    private int lastTurn;
    private Vector3 jiQi;
    private ForgeDirection fangXiang;
    public Ticket youPiao;

    public EMatter(World par1World) {
        super(par1World);
        this.lastTurn = 60;
        this.jiQi = new Vector3();
        this.fangXiang = ForgeDirection.NORTH;
        this.setSize(0.2F, 0.2F);
        super.renderDistanceWeight = 3.0D;
        super.noClip = true;
        super.ignoreFrustumCheck = true;
    }

    public EMatter(
        World par1World, Vector3 diDian, Vector3 jiQi, ForgeDirection direction
    ) {
        this(par1World);
        this.setPosition(diDian.x, diDian.y, diDian.z);
        this.jiQi = jiQi;
        this.fangXiang = direction;
    }

    public void writeSpawnData(ByteBuf data) {
        data.writeInt(this.jiQi.intX());
        data.writeInt(this.jiQi.intY());
        data.writeInt(this.jiQi.intZ());
        data.writeInt(this.fangXiang.ordinal());
    }

    public void readSpawnData(ByteBuf data) {
        this.jiQi.x = (double) data.readInt();
        this.jiQi.y = (double) data.readInt();
        this.jiQi.z = (double) data.readInt();
        this.fangXiang = ForgeDirection.getOrientation(data.readInt());
    }

    protected void entityInit() {
        super.dataWatcher.addObject(20, Byte.valueOf((byte) 3));
        if (this.youPiao == null) {
            this.youPiao = ForgeChunkManager.requestTicket(
                AtomicScience.instance, super.worldObj, Type.ENTITY
            );
            this.youPiao.getModData();
            this.youPiao.bindEntity(this);
        }
    }

    public void onUpdate() {
        if (super.ticksExisted % 10 == 0) {
            super.worldObj.playSoundAtEntity(
                this,
                SoundManager.ACCELERATOR,
                1.5F,
                (float) (0.6000000238418579D + 0.4D * (this.getSuDu() / 1.0D))
            );
        }

        TileEntity t = super.worldObj.getTileEntity(
            this.jiQi.intX(), this.jiQi.intY(), this.jiQi.intZ()
        );
        if (!(t instanceof TAccelerator)) {
            this.setDead();
        } else {
            TAccelerator tileEntity = (TAccelerator) t;
            if (tileEntity.wuSu == null) {
                tileEntity.wuSu = this;
            }

            for (int jianKuai = -1; jianKuai < 1; ++jianKuai) {
                for (int z = -1; z < 1; ++z) {
                    ForgeChunkManager.forceChunk(
                        this.youPiao,
                        new ChunkCoordIntPair(
                            ((int) super.posX >> 4) + jianKuai,
                            ((int) super.posZ >> 4) + z
                        )
                    );
                }
            }

            try {
                if (!super.worldObj.isRemote) {
                    super.dataWatcher.updateObject(
                        20, Byte.valueOf((byte) this.fangXiang.ordinal())
                    );
                } else {
                    this.fangXiang = ForgeDirection.getOrientation(
                        super.dataWatcher.getWatchableObjectByte(20)
                    );
                }
            } catch (Exception var13) {
                var13.printStackTrace();
            }

            double var14 = 6.99999975040555E-4D;
            ForgeDirection zuoFangXiang = VectorHelper.getOrientationFromSide(
                this.fangXiang, ForgeDirection.EAST
            );
            Block zuoBlockID = super.worldObj.getBlock(
                MathHelper.floor_double(super.posX + (double) zuoFangXiang.offsetX),
                MathHelper.floor_double(super.posY),
                MathHelper.floor_double(super.posZ + (double) zuoFangXiang.offsetZ)
            );
            ForgeDirection youFangXiang = VectorHelper.getOrientationFromSide(
                this.fangXiang, ForgeDirection.WEST
            );
            Block youBlockID = super.worldObj.getBlock(
                MathHelper.floor_double(super.posX + (double) youFangXiang.offsetX),
                MathHelper.floor_double(super.posY),
                MathHelper.floor_double(super.posZ + (double) youFangXiang.offsetZ)
            );
            if ((zuoBlockID == Blocks.air || youBlockID == Blocks.air)
                && this.lastTurn <= 0) {
                var14 = this.turn();
                super.motionX = 0.0D;
                super.motionY = 0.0D;
                super.motionZ = 0.0D;
                this.lastTurn = 40;
            }

            --this.lastTurn;
            if (canExist(super.worldObj, new Vector3(this), this.fangXiang)
                && !super.isCollided) {
                Vector3 dongLi = new Vector3();
                dongLi.modifyPositionFromSide(this.fangXiang);
                dongLi.multiply(var14);
                super.motionX = Math.min(dongLi.x + super.motionX, 1.0D);
                super.motionY = Math.min(dongLi.y + super.motionY, 1.0D);
                super.motionZ = Math.min(dongLi.z + super.motionZ, 1.0D);
                super.isAirBorne = true;
                super.lastTickPosX = super.posX;
                super.lastTickPosY = super.posY;
                super.lastTickPosZ = super.posZ;
                super.posX += super.motionX;
                super.posY += super.motionY;
                super.posZ += super.motionZ;
                this.setPosition(super.posX, super.posY, super.posZ);
                if (super.lastTickPosX == super.posX && super.lastTickPosY == super.posY
                    && super.lastTickPosZ == super.posZ && this.getSuDu() <= 0.0D
                    && this.lastTurn <= 0) {
                    this.setDead();
                }

                super.worldObj.spawnParticle(
                    "portal", super.posX, super.posY, super.posZ, 0.0D, 0.0D, 0.0D
                );
                super.worldObj.spawnParticle(
                    "largesmoke", super.posX, super.posY, super.posZ, 0.0D, 0.0D, 0.0D
                );
                if (super.ticksExisted % 10 == 0) {
                    float radius = 0.6F;
                    AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(
                        super.posX - (double) radius,
                        super.posY - (double) radius,
                        super.posZ - (double) radius,
                        super.posX + (double) radius,
                        super.posY + (double) radius,
                        super.posZ + (double) radius
                    );
                    List<Entity> entitiesNearby
                        = super.worldObj.getEntitiesWithinAABB(Entity.class, bounds);
                    if (entitiesNearby.size() > 1) {
                        this.explode();
                        return;
                    }
                }

            } else {
                this.explode();
            }
        }
    }

    private double turn() {
        ForgeDirection zuoFangXiang
            = VectorHelper.getOrientationFromSide(this.fangXiang, ForgeDirection.EAST);
        Vector3 zuoBian = new Vector3(this);
        zuoBian.modifyPositionFromSide(zuoFangXiang);
        ForgeDirection youFangXiang
            = VectorHelper.getOrientationFromSide(this.fangXiang, ForgeDirection.WEST);
        Vector3 youBian = new Vector3(this);
        youBian.modifyPositionFromSide(youFangXiang);
        if (zuoBian.getBlock(super.worldObj) == Blocks.air) {
            this.fangXiang = zuoFangXiang;
        } else {
            if (youBian.getBlock(super.worldObj) != Blocks.air) {
                this.setDead();
                return 0.0D;
            }

            this.fangXiang = youFangXiang;
        }

        this.setPosition(
            Math.floor(super.posX) + 0.5D,
            Math.floor(super.posY) + 0.5D,
            Math.floor(super.posZ) + 0.5D
        );
        return this.getSuDu()
            - this.getSuDu() / Math.min(Math.max(70.0D * this.getSuDu(), 4.0D), 30.0D);
    }

    public void explode() {
        if (!super.worldObj.isRemote) {
            boolean radius = false;
            if (this.getSuDu() > 0.5D) {
                float bounds = 1.0F;
                AxisAlignedBB livingNearby = AxisAlignedBB.getBoundingBox(
                    super.posX - (double) bounds,
                    super.posY - (double) bounds,
                    super.posZ - (double) bounds,
                    super.posX + (double) bounds,
                    super.posY + (double) bounds,
                    super.posZ + (double) bounds
                );
                List<Entity> entities
                    = super.worldObj.getEntitiesWithinAABB(Entity.class, livingNearby);

                for (Entity entity : entities) {
                    if (entity instanceof EMatter) {
                        if ((double) super.worldObj.rand.nextFloat() > 0.85D) {
                            super.worldObj.spawnEntityInWorld(new EntityItem(
                                super.worldObj,
                                super.posX,
                                super.posY,
                                super.posZ,
                                new ItemStack(AtomicScience.itCellStrangeMatter)
                            ));
                        }

                        radius = true;
                    }
                }

                if ((double) super.worldObj.rand.nextFloat() > 0.95D) {
                    super.worldObj.spawnEntityInWorld(new EntityItem(
                        super.worldObj,
                        super.posX,
                        super.posY,
                        super.posZ,
                        new ItemStack(AtomicScience.itCellStrangeMatter)
                    ));
                }
            }

            if (!radius) {
                super.worldObj.createExplosion(
                    this,
                    super.posX,
                    super.posY,
                    super.posZ,
                    (float) this.getSuDu() * 2.5F,
                    true
                );
            }
        }

        float radius1 = 6.0F;
        AxisAlignedBB bounds1 = AxisAlignedBB.getBoundingBox(
            super.posX - (double) radius1,
            super.posY - (double) radius1,
            super.posZ - (double) radius1,
            super.posX + (double) radius1,
            super.posY + (double) radius1,
            super.posZ + (double) radius1
        );
        List<EntityLivingBase> livingNearby1
            = super.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bounds1);

        for (EntityLivingBase entity2 : livingNearby1) {
            PoisonRadiation.INSTANCE.poisonEntity(new Vector3(entity2), entity2);
        }

        super.worldObj.playSoundEffect(
            super.posX,
            super.posY,
            super.posZ,
            SoundManager.ANTIMATTER,
            1.5F,
            1.0F - super.worldObj.rand.nextFloat() * 0.3F
        );
        this.setDead();
    }

    public double getSuDu() {
        return Math.abs(super.motionX) + Math.abs(super.motionY)
            + Math.abs(super.motionZ);
    }

    public static boolean
    canExist(World worldObj, Vector3 position, ForgeDirection facing) {
        if (position.getBlock(worldObj) != Blocks.air) {
            return false;
        } else {
            for (int i = 0; i <= 1; ++i) {
                ForgeDirection dir = ForgeDirection.getOrientation(i);
                if (!isElectromagnet(worldObj, position, dir)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean
    isElectromagnet(World worldObj, Vector3 position, ForgeDirection dir) {
        Vector3 checkPos = position.clone();
        checkPos.modifyPositionFromSide(dir);
        Block block = checkPos.getBlock(worldObj);
        return block != Blocks.air ? block instanceof IElectromagnet : false;
    }

    public void applyEntityCollision(Entity par1Entity) {
        this.explode();
    }

    public void setDead() {
        ForgeChunkManager.releaseTicket(this.youPiao);
        super.setDead();
    }

    protected void readEntityFromNBT(NBTTagCompound nbt) {
        this.jiQi = Vector3.readFromNBT(nbt.getCompoundTag("jiqi"));
        ForgeDirection.getOrientation(nbt.getByte("fangXiang"));
    }

    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setTag("jiqi", this.jiQi.writeToNBT(new NBTTagCompound()));
        nbt.setByte("fangXiang", (byte) this.fangXiang.ordinal());
    }
}
