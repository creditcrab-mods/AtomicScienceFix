package atomicscience;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import universalelectricity.core.vector.Vector3;

public class PAutoBuilder implements IMessage {
    public Vector3 pos;
    public TAutoBuilder.AutoBuilderType type;
    public int radius;

    public PAutoBuilder(Vector3 pos, TAutoBuilder.AutoBuilderType type, int radius) {
        this.pos = pos;
        this.type = type;
        this.radius = radius;
    }

    public PAutoBuilder() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            NBTTagCompound nbt = CompressedStreamTools.read(
                new DataInputStream(new ByteBufInputStream(buf))
            );

            this.pos = Vector3.readFromNBT(nbt.getCompoundTag("pos"));
            this.type = TAutoBuilder.AutoBuilderType.get(nbt.getInteger("type"));
            this.radius = nbt.getInteger("radius");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setTag("pos", this.pos.writeToNBT(new NBTTagCompound()));
        nbt.setInteger("type", this.type.ordinal());
        nbt.setInteger("radius", this.radius);

        try {
            CompressedStreamTools.write(
                nbt, new DataOutputStream(new ByteBufOutputStream(buf))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
