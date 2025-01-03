package atomicscience.fanwusu;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;
import universalelectricity.prefab.tile.TileEntityRFProducer;

public class TFulminationGenerator extends TileEntityRFProducer {
    public static final int DIAN = 2000;
    public double dian;
    public boolean cached = false;

    public IEnergyReceiver[] receivers = {null,null,null,null,null,null};

    public void updateAdjacentReceiver(){
        for (int i = 0; i < 6; ++i) {
            TileEntity te = VectorHelper.getTileEntityFromSide(this.worldObj, new Vector3(this.xCoord,this.yCoord,this.zCoord), ForgeDirection.getOrientation(i));
            if (te instanceof IEnergyReceiver) receivers[i] = (IEnergyReceiver) te;
        }
    }

    @Override public void onNeighborChange(){
        updateAdjacentReceiver();
    }

    public void pushEnergy(int energy){
        energy = energyStorage.extractEnergy(energy,true);
        int toExtract = 0;
        for (var receiver : receivers){
            if(receiver != null){
                int extracted = receiver.receiveEnergy(ForgeDirection.DOWN,energy,false);
                toExtract += extracted;
                energy = Integer.max(0,energy - extracted);
            }
        }
        energyStorage.extractEnergy(toExtract,false);

    }

    public TFulminationGenerator() {
        super(Integer.MAX_VALUE,DIAN,Integer.MAX_VALUE);
        FulminationEventHandler.INSTANCE.register(this);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!cached){
            updateAdjacentReceiver();
            cached = true;
        }
        if (!this.isDisabled()) {
            this.pushEnergy(DIAN);
        }
    }

    public void invalidate() {
        FulminationEventHandler.INSTANCE.unregister(this);
        super.initiate();
    }

}
