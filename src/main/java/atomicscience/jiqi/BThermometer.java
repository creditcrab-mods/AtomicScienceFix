package atomicscience.jiqi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

public class BThermometer extends BBase {
    private IIcon iconSide;

    public BThermometer() {
        super("thermometer");
        this.textureName = "atomicscience:thermometer";
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return side != 1 && side != 0 ? this.iconSide : this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);
        this.iconSide = iconRegister.registerIcon("atomicscience:machine");
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
        TThermometer tileEntity = (TThermometer) world.getTileEntity(x, y, z);
        if (entityPlayer.getCurrentEquippedItem() != null
            && entityPlayer.getCurrentEquippedItem().getItem() instanceof ItThermometer) {
            Vector3 savedCoords
                = ((ItThermometer) entityPlayer.getCurrentEquippedItem().getItem())
                      .getSavedCoord(entityPlayer.getCurrentEquippedItem());
            if (tileEntity.setLinkTile(savedCoords)) {
                if (world.isRemote) {
                    entityPlayer.addChatMessage(
                        new ChatComponentText("Linked thermal data.")
                    );
                }

                return true;
            }
        }

        tileEntity.setWarningTemp(tileEntity.getWarningTemp() - 100);
        return true;
    }

    @Override
    public boolean onSneakMachineActivated(
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
        TThermometer tileEntity = (TThermometer) par1World.getTileEntity(x, y, z);
        tileEntity.setWarningTemp(tileEntity.getWarningTemp() - 10);
        return true;
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
        TThermometer tileEntity = (TThermometer) par1World.getTileEntity(x, y, z);
        tileEntity.setWarningTemp(tileEntity.getWarningTemp() + 100);
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
        TThermometer tileEntity = (TThermometer) par1World.getTileEntity(x, y, z);
        tileEntity.setWarningTemp(tileEntity.getWarningTemp() + 10);
        return true;
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public int
    isProvidingWeakPower(IBlockAccess par1IBlockAccess, int x, int y, int z, int par5) {
        return this.isProvidingStrongPower(par1IBlockAccess, x, y, z, par5);
    }

    @Override
    public int
    isProvidingStrongPower(IBlockAccess par1IBlockAccess, int x, int y, int z, int par5) {
        TThermometer tileEntity = (TThermometer) par1IBlockAccess.getTileEntity(x, y, z);
        return tileEntity.shouldEmitRedstone() ? 15 : 0;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int meta) {
        return new TThermometer();
    }
}
