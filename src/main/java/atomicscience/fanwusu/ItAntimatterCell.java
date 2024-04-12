package atomicscience.fanwusu;

import java.util.Iterator;
import java.util.List;

import atomicscience.AtomicScience;
import atomicscience.SoundManager;
import atomicscience.api.poison.PoisonRadiation;
import atomicscience.wujian.ItCell;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import icbm.api.explosion.ExplosionEvent;
import icbm.api.explosion.IExplosive;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.flag.FlagRegistry;

public class ItAntimatterCell extends ItCell {
    private IIcon iconGram;

    public ItAntimatterCell() {
        super("antimatter");
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public void addInformation(
        ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4
    ) {
        if (par1ItemStack.getItemDamage() >= 1) {
            list.add("1 Gram");
        } else {
            list.add("125 Milligrams");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(
            this.getUnlocalizedName().replace("item.", "") + "_milligram"
        );
        this.iconGram = iconRegister.registerIcon(
            this.getUnlocalizedName().replace("item.", "") + "_gram"
        );
    }

    @Override
    public IIcon getIconFromDamage(int metadata) {
        return metadata >= 1 ? this.iconGram : this.itemIcon;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(item, 1, 0));
        par3List.add(new ItemStack(item, 1, 1));
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return 160;
    }

    @SubscribeEvent
    public void onItemExpireEvent(ItemExpireEvent event) {
        if (event.entityItem != null) {
            ItemStack itemStack = event.entityItem.getEntityItem();
            if (itemStack != null && itemStack.getItem() == this) {
                event.entityItem.worldObj.playSoundEffect(
                    event.entityItem.posX,
                    event.entityItem.posY,
                    event.entityItem.posZ,
                    SoundManager.ANTIMATTER,
                    3.0F,
                    1.0F - event.entityItem.worldObj.rand.nextFloat() * 0.3F
                );
                if (!event.entityItem.worldObj.isRemote
                    && !FlagRegistry.getModFlag("ModFlags")
                            .containsValue(
                                event.entityItem.worldObj,
                                AtomicScience.QIZI_FAN_WU_SU_BAO_ZHA,
                                "true",
                                new Vector3(event.entityItem)
                            )) {
                    IExplosive explosive = new IExplosive() {
                        public int getID() {
                            return -1;
                        }

                        public String getUnlocalizedName() {
                            return "Antimatter";
                        }

                        public String getExplosiveName() {
                            return this.getUnlocalizedName();
                        }

                        public String getGrenadeName() {
                            return this.getUnlocalizedName();
                        }

                        public String getMissileName() {
                            return this.getUnlocalizedName();
                        }

                        public String getMinecartName() {
                            return this.getUnlocalizedName();
                        }

                        public float getRadius() {
                            return 4.0F;
                        }

                        public int getTier() {
                            return 4;
                        }

                        public double getEnergy() {
                            return 400000.0D;
                        }
                    };
                    if (itemStack.getItemDamage() == 1) {
                        explosive = new IExplosive() {
                            public int getID() {
                                return -1;
                            }

                            public String getUnlocalizedName() {
                                return "antimatter";
                            }

                            public String getExplosiveName() {
                                return this.getUnlocalizedName();
                            }

                            public String getGrenadeName() {
                                return this.getUnlocalizedName();
                            }

                            public String getMissileName() {
                                return this.getUnlocalizedName();
                            }

                            public String getMinecartName() {
                                return this.getUnlocalizedName();
                            }

                            public float getRadius() {
                                return 6.0F;
                            }

                            public int getTier() {
                                return 4;
                            }

                            public double getEnergy() {
                                return 2000000.0D;
                            }
                        };
                    }

                    MinecraftForge.EVENT_BUS.post(new ExplosionEvent.PreExplosionEvent(
                        event.entity.worldObj,
                        event.entityItem.posX,
                        event.entityItem.posY,
                        event.entityItem.posZ,
                        explosive
                    ));
                    event.entityItem.worldObj.createExplosion(
                        event.entityItem,
                        event.entityItem.posX,
                        event.entityItem.posY,
                        event.entityItem.posZ,
                        explosive.getRadius(),
                        true
                    );
                    AtomicScience.LOGGER.fine(
                        "Antimatter cell detonated at: " + event.entityItem.posX + ", "
                        + event.entityItem.posY + ", " + event.entityItem.posZ
                    );
                    boolean radius = true;
                    AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(
                        event.entityItem.posX - 20.0D,
                        event.entityItem.posY - 20.0D,
                        event.entityItem.posZ - 20.0D,
                        event.entityItem.posX + 20.0D,
                        event.entityItem.posY + 20.0D,
                        event.entityItem.posZ + 20.0D
                    );
                    List entitiesNearby = event.entityItem.worldObj.getEntitiesWithinAABB(
                        EntityLiving.class, bounds
                    );
                    Iterator i$ = entitiesNearby.iterator();

                    while (i$.hasNext()) {
                        EntityLiving entity = (EntityLiving) i$.next();
                        PoisonRadiation.INSTANCE.poisonEntity(
                            new Vector3(entity), entity
                        );
                    }
                }
            }
        }
    }
}
