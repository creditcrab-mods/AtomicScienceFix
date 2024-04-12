package atomicscience.fenlie;

import java.util.List;

import atomicscience.AtomicScience;
import atomicscience.MegaTNTExplusion;
import atomicscience.api.IFissileMaterial;
import atomicscience.api.IReactor;
import atomicscience.api.ITemperature;
import atomicscience.api.poison.PoisonRadiation;
import atomicscience.wujian.ItBreederFuel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.tile.TileEntityDisableable;

public class TFissionReactor extends TileEntityDisableable
    implements IInventory, ITemperature, IReactor, IFluidHandler {
    public static final int BAN_JING = 2;
    public static final int WEN_DU = 2000;
    public float temperature = 0.0F;
    private ItemStack[] containingItems = new ItemStack[1];
    public float rotation = 0.0F;
    private TFissionReactor cachedZhuYao = null;
    public FluidTank wasteTank;

    public TFissionReactor() {
        this.wasteTank = new FluidTank(AtomicScience.FLUID_TOXIC_WASTE, 0, 800000);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (this.cachedZhuYao == null || super.ticks % 20L == 0L) {
            this.zhaoZuiDi();
        }

        if (!this.isDisabled()) {
            if (this.cachedZhuYao != this && this.getStackInSlot(0) != null
                && this.cachedZhuYao.getStackInSlot(0) == null) {
                this.cachedZhuYao.setInventorySlotContents(0, this.getStackInSlot(0));
                this.setInventorySlotContents(0, (ItemStack) null);
            }

            ItemStack fissileFuel = this.cachedZhuYao.getStackInSlot(0);
            int i;
            if (fissileFuel != null
                && fissileFuel.getItem() instanceof IFissileMaterial) {
                i = ((IFissileMaterial) fissileFuel.getItem()).onFissile(this);
                if (!this.worldObj.isRemote) {
                    if (i == 0) {
                        fissileFuel.setItemDamage(Math.min(
                            fissileFuel.getItemDamage() + 1, fissileFuel.getMaxDamage()
                        ));
                        if (fissileFuel.getItemDamage() >= fissileFuel.getMaxDamage()) {
                            this.cachedZhuYao.setInventorySlotContents(
                                0, (ItemStack) null
                            );
                        }
                    } else if (i == 2) {
                        fissileFuel.setItemDamage(
                            Math.max(fissileFuel.getItemDamage() - 1, 0)
                        );
                    }
                }

                if (super.ticks % 20L == 0L
                    && (double) this.worldObj.rand.nextFloat() > 0.65D) {
                    List<EntityLivingBase> entitiesInRange
                        = this.worldObj.getEntitiesWithinAABB(
                            EntityLivingBase.class,
                            AxisAlignedBB.getBoundingBox(
                                (double) (this.xCoord - 4),
                                (double) (this.yCoord - 4),
                                (double) (this.xCoord - 4),
                                (double) (this.xCoord + 4),
                                (double) (this.yCoord + 4),
                                (double) (this.xCoord + 4)
                            )
                        );

                    for (EntityLivingBase entity : entitiesInRange) {
                        PoisonRadiation.INSTANCE.poisonEntity(new Vector3(this), entity);
                    }
                }

                if ((double) this.worldObj.rand.nextFloat() > 0.5D) {
                    this.wasteTank.fill(
                        new FluidStack(AtomicScience.FLUID_TOXIC_WASTE, 1), true
                    );
                }
            }

            if (this.temperature > 2000.0F) {
                this.meltDown();
            } else if (this.temperature > 100.0F && (fissileFuel == null || !(fissileFuel.getItem() instanceof ItBreederFuel))) {
                for (int x = -2; x <= 2; ++x) {
                    for (int z = -2; z <= 2; ++z) {
                        Vector3 offsetPos = new Vector3(this);
                        offsetPos.add(new Vector3((double) x, 0.0D, (double) z));
                        Block offsetBlock = this.worldObj.getBlock(
                            offsetPos.intX(), offsetPos.intY(), offsetPos.intZ()
                        );
                        if (offsetBlock != Blocks.water) {
                            if (this.isOverToxic() && !this.worldObj.isRemote
                                && (double) this.worldObj.rand.nextFloat() > 0.999D) {
                                if (offsetBlock == Blocks.grass) {
                                    offsetPos.setBlock(
                                        this.worldObj, AtomicScience.blockRadioactive
                                    );
                                } else if (offsetBlock != Blocks.air) {
                                    offsetPos.setBlock(
                                        this.worldObj,
                                        this.wasteTank.getFluid().getFluid().getBlock()
                                    );
                                }

                                this.wasteTank.drain(1000, true);
                            }
                        } else if (super.ticks % (long) ((int) Math.max(40.0F - this.temperature / 2000.0F * 40.0F, 2.0F)) == 0L) {
                            AtomicScience.boilWater(this.worldObj, offsetPos, 3, 8);
                            this.temperature = (float) ((double) this.temperature - 0.2D);
                        }
                    }
                }
            }

            for (int direction = 2; direction < 6; ++direction) {
                Vector3 neighborPos = new Vector3(this);
                neighborPos.modifyPositionFromSide(ForgeDirection.getOrientation(direction
                ));
                if (neighborPos.getBlock(this.worldObj) == AtomicScience.bControlRod) {
                    this.setTemperature(this.getTemperature() - 0.5F);
                }
            }

            if (!this.worldObj.isRemote) {
                this.setTemperature(this.getTemperature() - 1.1F);
            }
        }

        if (!this.worldObj.isRemote && super.ticks % 60L == 0L) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    public boolean isOverToxic() {
        return AtomicScience.ALLOW_TOXIC_WASTE &&
            // TODO >=?
            this.wasteTank.getFluidAmount() > this.wasteTank.getCapacity();
    }

    // TODO: WTF
    //@Override
    public void updatePositionStatus() {
        boolean top = (new Vector3(this))
                          .add(new Vector3(0.0D, 1.0D, 0.0D))
                          .getTileEntity(this.worldObj)
                          instanceof TFissionReactor;
        boolean bottom = (new Vector3(this))
                             .add(new Vector3(0.0D, -1.0D, 0.0D))
                             .getTileEntity(this.worldObj)
                             instanceof TFissionReactor;
        if (top && bottom) {
            this.worldObj.setBlockMetadataWithNotify(
                this.xCoord, this.yCoord, this.zCoord, 1, 3
            );
        } else if (top) {
            this.worldObj.setBlockMetadataWithNotify(
                this.xCoord, this.yCoord, this.zCoord, 0, 3
            );
        } else {
            this.worldObj.setBlockMetadataWithNotify(
                this.xCoord, this.yCoord, this.zCoord, 2, 3
            );
        }
    }

    public TFissionReactor zhaoZuiDi() {
        Vector3 checkPosition = new Vector3(this);
        TFissionReactor tile = null;

        do {
            TileEntity newTile = checkPosition.getTileEntity(this.worldObj);
            if (!(newTile instanceof TFissionReactor)) {
                this.cachedZhuYao = tile;
                return tile;
            }

            tile = (TFissionReactor) newTile;
            --checkPosition.y;
        } while (tile != null);

        this.cachedZhuYao = this;
        return this;
    }

    public int getHeight() {
        int height = 0;
        Vector3 checkPosition = new Vector3(this);

        for (Object tile = this; tile instanceof TFissionReactor;
             tile = checkPosition.getTileEntity(this.worldObj)) {
            ++checkPosition.y;
            ++height;
        }

        return height;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(
            this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), nbt
        );
    }

    private void meltDown() {
        if (!this.worldObj.isRemote) {
            this.setInventorySlotContents(0, (ItemStack) null);
            MegaTNTExplusion baoZha = new MegaTNTExplusion(
                this.worldObj,
                (Entity) null,
                (double) this.xCoord,
                (double) this.yCoord,
                (double) this.zCoord,
                9.0F
            );
            baoZha.doExplosionA();
            baoZha.doExplosionB(true);
            this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.lava);
        }
    }

    @Override
    public void onDataPacket(NetworkManager arg0, S35PacketUpdateTileEntity packet) {
        this.readFromNBT(packet.func_148857_g());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.temperature = nbt.getFloat("temperature");
        this.wasteTank.readFromNBT(nbt.getCompoundTag("tank"));
        NBTTagList items = nbt.getTagList("Items", 10); // 10 = magic number for compound
        this.containingItems = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound item = items.getCompoundTagAt(i);
            byte var5 = item.getByte("Slot");
            if (var5 >= 0 && var5 < this.containingItems.length) {
                this.containingItems[var5] = ItemStack.loadItemStackFromNBT(item);
                this.containingItems[var5].setItemDamage(item.getInteger("damage"));
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setFloat("temperature", this.temperature);
        NBTTagCompound tankNBT = new NBTTagCompound();
        this.wasteTank.writeToNBT(tankNBT);
        par1NBTTagCompound.setTag("tank", tankNBT);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.containingItems.length; ++var3) {
            if (this.containingItems[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.containingItems[var3].writeToNBT(var4);
                var4.setInteger("damage", this.containingItems[var3].getItemDamage());
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
    }

    @Override
    public int getSizeInventory() {
        return this.containingItems.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.containingItems[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.containingItems[par1] != null) {
            ItemStack var3;
            if (this.containingItems[par1].stackSize <= par2) {
                var3 = this.containingItems[par1];
                this.containingItems[par1] = null;
                return var3;
            } else {
                var3 = this.containingItems[par1].splitStack(par2);
                if (this.containingItems[par1].stackSize == 0) {
                    this.containingItems[par1] = null;
                }

                return var3;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.containingItems[par1] != null) {
            ItemStack var2 = this.containingItems[par1];
            this.containingItems[par1] = null;
            return var2;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.containingItems[par1] = par2ItemStack;
        if (par2ItemStack != null
            && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this
            ? false
            : par1EntityPlayer.getDistanceSq(
                  (double) this.xCoord + 0.5D,
                  (double) this.yCoord + 0.5D,
                  (double) this.zCoord + 0.5D
              ) <= 64.0D;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public void onDisable(int duration) {
        super.disabledTicks = duration;
    }

    @Override
    public boolean isDisabled() {
        return super.disabledTicks > 0;
    }

    @Override
    public float getTemperature() {
        return this.temperature;
    }

    @Override
    public void setTemperature(float celsius) {
        this.temperature = Math.max(celsius, 0.0F);
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
        return this.cachedZhuYao != null && this.cachedZhuYao.getStackInSlot(0) == null
            ? itemStack.getItem() instanceof IFissileMaterial
            : false;
    }

    @Override
    public boolean canFill(ForgeDirection arg0, Fluid arg1) {
        return false;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public boolean canDrain(ForgeDirection arg0, Fluid arg1) {
        return arg1 == AtomicScience.FLUID_TOXIC_WASTE
            && this.wasteTank.getFluidAmount() > 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return this.wasteTank.drain(maxDrain, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack fluid, boolean doDrain) {
        return fluid.getFluid() == AtomicScience.FLUID_TOXIC_WASTE
            ? this.drain(from, fluid.amount, doDrain)
            : null;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
        return new FluidTankInfo[] { new FluidTankInfo(this.wasteTank) };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return this.cachedZhuYao == this ? TileEntity.INFINITE_EXTENT_AABB
                                         : super.getRenderBoundingBox();
    }

    @Override
    public String getInventoryName() {
        return this.getBlockType().getLocalizedName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }
}
