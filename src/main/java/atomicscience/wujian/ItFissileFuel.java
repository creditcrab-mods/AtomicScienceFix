package atomicscience.wujian;

import java.util.List;

import atomicscience.api.IFissileMaterial;
import atomicscience.api.IReactor;
import atomicscience.api.ITemperature;
import atomicscience.fenlie.ItRadioactive;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.vector.Vector3;

public class ItFissileFuel extends ItRadioactive implements IFissileMaterial {
    public static final int FU_LAN = 50000;

    public ItFissileFuel() {
        super("rodFissileFuel");
        this.setMaxDamage('\uc350');
        this.setMaxStackSize(1);
        this.setNoRepair();
        this.setTextureName("atomicscience:rodFissileFuel");
    }

    @Override
    public int onFissile(ITemperature reactor) {
        TileEntity tileEntity = (TileEntity) reactor;
        World worldObj = tileEntity.getWorldObj();
        float wenDu = 0.0F;
        int reactors = 0;

        for (int chance = 0; chance < 6; ++chance) {
            Vector3 iWenDuDiDian
                = (new Vector3(tileEntity))
                      .modifyPositionFromSide(ForgeDirection.getOrientation(chance));
            Block fangGe = iWenDuDiDian.getBlock(worldObj);
            if (fangGe != null) {
                TileEntity tile = iWenDuDiDian.getTileEntity(worldObj);
                if (tile instanceof IReactor) {
                    ++reactors;
                }

                if (fangGe instanceof ITemperature) {
                    wenDu += ((ITemperature) fangGe).getTemperature();
                } else if (tile instanceof ITemperature) {
                    wenDu += ((ITemperature) tile).getTemperature();
                }
            }
        }

        if (reactors >= 4) {
            float var11 = Math.max((wenDu - 5000.0F) / 2000.0F, 0.0F);
            if (Math.random() <= (double) var11) {
                return 2;
            } else {
                return 1;
            }
        } else {
            reactor.setTemperature((float
            ) ((double) reactor.getTemperature() + 1.6D
               + (double) worldObj.rand.nextFloat() * 0.4D));
            return 0;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, this.getMaxDamage() - 1));
    }
}
