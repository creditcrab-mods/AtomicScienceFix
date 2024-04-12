package atomicscience.fenlie;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import universalelectricity.core.path.IPathCallBack;
import universalelectricity.core.path.Pathfinder;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.tile.TileEntityAdvanced;

public class TReactorTap extends TileEntityAdvanced implements IFluidHandler {
    private final Set<FluidTank> tanks = new HashSet<>();
    private long lastFindTime = -1L;

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.worldObj.isRemote && this.ticks % 20 == 0) {
            FluidTank optimalTank = this.getOptimalTank();
            if (optimalTank == null)
                return;
            ForgeDirection rotation = ForgeDirection.getOrientation(
                this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord)
            );

            Vector3 neighborPos = new Vector3(this).modifyPositionFromSide(rotation);
            if (neighborPos.getTileEntity(this.worldObj) instanceof IFluidHandler) {
                IFluidHandler fHandler
                    = (IFluidHandler) neighborPos.getTileEntity(this.worldObj);
                int drained
                    = fHandler.fill(rotation.getOpposite(), optimalTank.getFluid(), true);

                if (drained != 0)
                    optimalTank.drain(drained, true);
            }
        }
    }

    public void find() {
        this.tanks.clear();
        final World world = this.worldObj;
        final Vector3 position = new Vector3(this);
        Pathfinder finder
            = (new Pathfinder(new IPathCallBack() {
                  public Set getConnectedNodes(Pathfinder finder, Vector3 currentNode) {
                      HashSet<Vector3> neighbors = new HashSet<>();

                      for (int i = 0; i < 6; ++i) {
                          ForgeDirection direction = ForgeDirection.getOrientation(i);
                          Vector3 positionx
                              = currentNode.clone().modifyPositionFromSide(direction);
                          Block connectedBlockID = positionx.getBlock(world);
                          if (connectedBlockID == Blocks.air
                              || connectedBlockID instanceof BlockLiquid
                              || connectedBlockID instanceof BFissionReactor) {
                              neighbors.add(positionx);
                          }
                      }

                      return neighbors;
                  }

                  public boolean onSearch(Pathfinder finder, Vector3 node) {
                      if (node.getTileEntity(world) instanceof TFissionReactor) {
                          finder.results.add(node);
                      }

                      return node.distanceTo(position) > 6.0D;
                  }
              }))
                  .init((new Vector3(this))
                            .modifyPositionFromSide(
                                ForgeDirection.getOrientation(this.getBlockMetadata())
                                    .getOpposite()
                            ));
        for (Vector3 node : (Set<Vector3>) finder.results) {
            TileEntity tileEntity = node.getTileEntity(this.worldObj);
            if (tileEntity instanceof TFissionReactor) {
                this.tanks.add(((TFissionReactor) tileEntity).wasteTank);
            }
        }

        this.lastFindTime = this.worldObj.getWorldTime();
    }

    public FluidTank getOptimalTank() {
        if (this.lastFindTime == -1L
            || this.worldObj.getWorldTime() - this.lastFindTime > 20L) {
            this.find();
        }

        if (this.tanks.size() <= 0) {
            return null;
        }

        FluidTank optimalTank = null;
        for (FluidTank tank : this.tanks) {
            int optimalTankContent
                = optimalTank == null ? 0 : optimalTank.getFluidAmount();
            if (tank.getFluidAmount() > optimalTankContent) {
                optimalTank = tank;
            }
        }

        return optimalTank;
    }

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    public int fill(int tankIndex, FluidStack resource, boolean doFill) {
        return 0;
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return this.getOptimalTank() != null
            ? this.getOptimalTank().drain(maxDrain, doDrain)
            : null;
    }

    @Override
    public FluidStack drain(ForgeDirection arg0, FluidStack arg1, boolean arg2) {
        return this.getOptimalTank() != null
                && arg1.getFluid() == this.getOptimalTank().getFluid().getFluid()
            ? this.drain(arg0, arg1.amount, arg2)
            : null;
    }

    @Override
    public boolean canDrain(ForgeDirection arg0, Fluid arg1) {
        return this.getOptimalTank() != null
            && this.getOptimalTank().getFluid().getFluid() == arg1
            && this.getOptimalTank().getFluidAmount() != 0;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection arg0) {
        if (arg0.ordinal() != this.getBlockMetadata())
            return null;

        this.find();
        return (FluidTankInfo[]) this.tanks.stream().map(FluidTankInfo::new).toArray();
    }

    @Override
    public boolean canFill(ForgeDirection arg0, Fluid arg1) {
        return false;
    }
}
