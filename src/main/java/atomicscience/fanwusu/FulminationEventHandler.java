package atomicscience.fanwusu;

import java.util.ArrayList;
import java.util.List;

import atomicscience.AtomicScience;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import icbm.api.explosion.ExplosionEvent;
import net.minecraft.util.Vec3;
import universalelectricity.core.vector.Vector3;

public class FulminationEventHandler {
    public static final FulminationEventHandler INSTANCE = new FulminationEventHandler();
    public static final List<TFulminationGenerator> list = new ArrayList<>();

    public void register(TFulminationGenerator tileEntity) {
        if (!list.contains(tileEntity)) {
            list.add(tileEntity);
        }
    }

    public void unregister(TFulminationGenerator tileEntity) {
        list.remove(tileEntity);
    }

    @SubscribeEvent
    public void onPreExplosionEvent(ExplosionEvent.PreExplosionEvent event) {
        if (event.explosive != null && event.explosive.getRadius() > 0.0F
            && event.explosive.getEnergy() > 0.0D) {
            for (TFulminationGenerator tileEntity : list) {
                if (tileEntity != null && !tileEntity.isInvalid()) {
                    Vector3 tilePos = new Vector3(tileEntity);
                    tilePos.add(0.5D);
                    double explosionDistance
                        = tilePos.distanceTo(new Vector3(event.x, event.y, event.z));
                    if (explosionDistance <= (double) event.explosive.getRadius()
                        && explosionDistance > 0.0D) {
                        float density = event.world.getBlockDensity(
                            Vec3.createVectorHelper(event.x, event.y, event.z),
                            AtomicScience.bFulminationGenerator
                                .getCollisionBoundingBoxFromPool(
                                    event.world,
                                    tileEntity.xCoord,
                                    tileEntity.yCoord,
                                    tileEntity.zCoord
                                )
                        );
                        if (density < 1.0F) {
                            double nengLiang = Math.min(
                                event.explosive.getEnergy(),
                                event.explosive.getEnergy()
                                    / (explosionDistance
                                       / (double) event.explosive.getRadius())
                            );
                            nengLiang = Math.max(
                                nengLiang - (double) density * nengLiang, 0.0D
                            );
                            tileEntity.dian += nengLiang;
                        }
                    }
                }
            }
        }
    }
}
