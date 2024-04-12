package atomicscience;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PHAutoBuilder implements IMessageHandler<PAutoBuilder, IMessage> {
    @Override
    public IMessage onMessage(PAutoBuilder packet, MessageContext ctx) {
        World world = ctx.getServerHandler().playerEntity.worldObj;

        TileEntity te = packet.pos.getTileEntity(world);
        if (!(te instanceof TAutoBuilder))
            return null;

        TAutoBuilder ab = (TAutoBuilder) te;

        ab.onActivatePacket(packet.type, packet.radius);

        return null;
    }
}
