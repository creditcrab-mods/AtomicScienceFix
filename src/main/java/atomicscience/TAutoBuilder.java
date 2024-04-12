package atomicscience;

import net.minecraft.init.Blocks;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.tile.TileEntityAdvanced;

public class TAutoBuilder extends TileEntityAdvanced {
    public boolean canUpdate() {
        return false;
    }

    public void onActivatePacket(TAutoBuilder.AutoBuilderType type, int radius) {
        if (!this.worldObj.isRemote) {
            try {
                Vector3 diDian = new Vector3(this);
                if (radius > 0) {
                    int r;
                    int x;
                    int z;
                    Vector3 targetPosition;
                    Vector3 targetPosition1;
                    byte var16;
                    int var17;
                    switch (
                        TAutoBuilder.NamelessClass2141744605
                            .$SwitchMap$atomicscience$TJianLi$JianLiType[type.ordinal()]
                    ) {
                        case 1:
                            r = radius;

                            for (x = -radius; x < r; ++x) {
                                for (z = -r; z < r; ++z) {
                                    for (var17 = -1; var17 <= 1; ++var17) {
                                        targetPosition1 = diDian.clone();
                                        targetPosition1.add(new Vector3(
                                            (double) x, (double) var17, (double) z
                                        ));
                                        if (x == -r || x == r - 1 || z == -r
                                            || z == r - 1) {
                                            targetPosition1.setBlock(
                                                this.worldObj,
                                                AtomicScience.bElectromagnet
                                            );
                                        }
                                    }
                                }
                            }

                            r = radius - 2;

                            for (x = -r; x < r; ++x) {
                                for (z = -r; z < r; ++z) {
                                    for (var17 = -1; var17 <= 1; ++var17) {
                                        if (x == -r || x == r - 1 || z == -r
                                            || z == r - 1) {
                                            targetPosition1 = diDian.clone();
                                            targetPosition1.add(new Vector3(
                                                (double) x, (double) var17, (double) z
                                            ));
                                            targetPosition1.setBlock(
                                                this.worldObj,
                                                AtomicScience.bElectromagnet
                                            );
                                        }
                                    }
                                }
                            }

                            r = radius - 1;

                            for (x = -r; x < r; ++x) {
                                for (z = -r; z < r; ++z) {
                                    for (var17 = -1; var17 <= 1; ++var17) {
                                        if (x == -r || x == r - 1 || z == -r
                                            || z == r - 1) {
                                            targetPosition1 = diDian.clone();
                                            targetPosition1.add(new Vector3(
                                                (double) x, (double) var17, (double) z
                                            ));
                                            if (var17 != -1 && var17 != 1) {
                                                if (var17 == 0) {
                                                    targetPosition1.setBlock(
                                                        this.worldObj, Blocks.air
                                                    );
                                                }
                                            } else {
                                                targetPosition1.setBlock(
                                                    this.worldObj,
                                                    AtomicScience.bElectromagnetGlass
                                                );
                                            }
                                        }
                                    }
                                }
                            }

                            return;
                        case 2:
                            if (radius <= 1) {
                                var16 = 2;

                                for (x = -var16; x <= var16; ++x) {
                                    for (z = -var16; z <= var16; ++z) {
                                        targetPosition
                                            = (new Vector3((double) x, 0.0D, (double) z))
                                                  .add(diDian);
                                        targetPosition.setBlock(
                                            this.worldObj, Blocks.water
                                        );
                                    }
                                }

                                r = var16 - 1;

                                for (x = -r; x <= r; ++x) {
                                    for (z = -r; z <= r; ++z) {
                                        targetPosition
                                            = (new Vector3((double) x, 1.0D, (double) z))
                                                  .add(diDian);
                                        targetPosition.setBlock(
                                            this.worldObj, AtomicScience.bTurbine
                                        );
                                        if ((x != -r && x != r || z != -r && z != r)
                                            && (new Vector3((double) x, 0.0D, (double) z))
                                                    .getMagnitude()
                                                <= 1.0D) {
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, -1.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj, AtomicScience.bControlRod
                                            );
                                            targetPosition.add(
                                                new Vector3(0.0D, -1.0D, 0.0D)
                                            );
                                            targetPosition.setBlock(
                                                this.worldObj, Blocks.sticky_piston, 1
                                            );
                                        }
                                    }
                                }

                                Vector3.add(diDian, new Vector3(0.0D, -1.0D, 0.0D))
                                    .setBlock(this.worldObj, AtomicScience.bThermometer);
                                Vector3.add(diDian, new Vector3(0.0D, -3.0D, 0.0D))
                                    .setBlock(this.worldObj, AtomicScience.bSiren);
                                Vector3.add(diDian, new Vector3(0.0D, -2.0D, 0.0D))
                                    .setBlock(this.worldObj, Blocks.redstone_wire);
                                diDian.setBlock(
                                    this.worldObj, AtomicScience.bFissionReactor
                                );
                                break;
                            } else {
                                var16 = 2;

                                for (x = 0; x < radius; ++x) {
                                    for (z = -var16; z <= var16; ++z) {
                                        for (var17 = -var16; var17 <= var16; ++var17) {
                                            targetPosition1 = (new Vector3(
                                                                   (double) z,
                                                                   (double) x,
                                                                   (double) var17
                                                               ))
                                                                  .add(diDian);
                                            Vector3 leveledDiDian = Vector3.add(
                                                diDian,
                                                new Vector3(0.0D, (double) x, 0.0D)
                                            );
                                            if (x < radius - 1) {
                                                if (targetPosition1.distanceTo(
                                                        leveledDiDian
                                                    )
                                                    == 2.0D) {
                                                    targetPosition1.setBlock(
                                                        this.worldObj,
                                                        AtomicScience.bControlRod
                                                    );
                                                } else if (z != -var16 && z != var16 && var17 != -var16 && var17 != var16) {
                                                    if (z == 0 && var17 == 0) {
                                                        targetPosition1.setBlock(
                                                            this.worldObj,
                                                            AtomicScience.bFissionReactor
                                                        );
                                                    } else {
                                                        targetPosition1.setBlock(
                                                            this.worldObj, Blocks.water
                                                        );
                                                    }
                                                } else {
                                                    targetPosition1.setBlock(
                                                        this.worldObj, Blocks.glass
                                                    );
                                                }
                                            } else if (targetPosition1.distanceTo(leveledDiDian) < 2.0D) {
                                                targetPosition1.setBlock(
                                                    this.worldObj, AtomicScience.bTurbine
                                                );
                                            }
                                        }
                                    }
                                }

                                return;
                            }
                        case 3:
                            r = Math.max(radius, 2);

                            for (x = -r; x <= r; ++x) {
                                for (z = -r; z <= r; ++z) {
                                    targetPosition
                                        = (new Vector3((double) x, 0.0D, (double) z))
                                              .add(diDian);
                                    targetPosition.setBlock(this.worldObj, Blocks.water);
                                }
                            }

                            --r;

                            for (x = -r; x <= r; ++x) {
                                for (z = -r; z <= r; ++z) {
                                    targetPosition
                                        = (new Vector3((double) x, 1.0D, (double) z))
                                              .add(diDian);
                                    if ((new Vector3((double) x, 0.0D, (double) z))
                                            .getMagnitude()
                                        <= 2.0D) {
                                        if ((x == -r || x == r) && (z == -r || z == r)) {
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, -1.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj, AtomicScience.bControlRod
                                            );
                                            targetPosition.add(
                                                new Vector3(0.0D, -1.0D, 0.0D)
                                            );
                                            targetPosition.setBlock(
                                                this.worldObj, Blocks.sticky_piston, 1
                                            );
                                        } else {
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, 0.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj,
                                                AtomicScience.bFissionReactor
                                            );
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, -1.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj, AtomicScience.bThermometer
                                            );
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, -3.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj, AtomicScience.bSiren
                                            );
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, -2.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj, Blocks.redstone_wire
                                            );
                                        }
                                    }
                                }
                            }

                            Vector3.add(diDian, new Vector3(0.0D, -3.0D, 0.0D))
                                .setBlock(this.worldObj, Blocks.stone);
                            Vector3.add(diDian, new Vector3(0.0D, -2.0D, 0.0D))
                                .setBlock(this.worldObj, Blocks.stone);
                            diDian.setBlock(this.worldObj, AtomicScience.bFissionReactor);
                            break;
                        case 4:
                            r = Math.max(radius, 1);

                            for (x = -r; x <= r; ++x) {
                                for (z = -r; z <= r; ++z) {
                                    targetPosition
                                        = new Vector3((double) x, 0.0D, (double) z);
                                    if (targetPosition.getMagnitude() > 1.0D) {
                                        if (targetPosition.getMagnitude() < (double) r) {
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, 1.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj,
                                                AtomicScience.bElectromagnetBoiler
                                            );
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, -1.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj,
                                                AtomicScience.bElectromagnet
                                            );
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, 2.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj, Blocks.water
                                            );
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, 3.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj, AtomicScience.bTurbine
                                            );
                                        } else if (targetPosition.getMagnitude() - 0.7D < (double) r) {
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, 0.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj,
                                                AtomicScience.bElectromagnetGlass
                                            );
                                            targetPosition
                                                = (new Vector3(
                                                       (double) x, 2.0D, (double) z
                                                   ))
                                                      .add(diDian);
                                            targetPosition.setBlock(
                                                this.worldObj,
                                                AtomicScience.bElectromagnetGlass
                                            );
                                        }
                                    }
                                }
                            }

                            var16 = 1;

                            for (x = -var16; x <= var16; ++x) {
                                for (z = -var16; z <= var16; ++z) {
                                    targetPosition
                                        = (new Vector3((double) x, 0.0D, (double) z))
                                              .add(diDian);
                                    targetPosition.setBlock(
                                        this.worldObj, AtomicScience.bElectromagnet
                                    );
                                }
                            }

                            diDian.setBlock(this.worldObj, AtomicScience.bFusionReactor);
                    }
                }
            } catch (Exception var15) {
                var15.printStackTrace();
            }
        }
    }

    // $FF: synthetic class
    static class NamelessClass2141744605 {
        // $FF: synthetic field
        static final int[] $SwitchMap$atomicscience$TJianLi$JianLiType
            = new int[TAutoBuilder.AutoBuilderType.values().length];

        static {
            try {
                $SwitchMap$atomicscience$TJianLi$JianLiType[TAutoBuilder.AutoBuilderType
                                                                .JIA_SU_QI.ordinal()]
                    = 1;
            } catch (NoSuchFieldError var4) {
                ;
            }

            try {
                $SwitchMap$atomicscience$TJianLi$JianLiType[TAutoBuilder.AutoBuilderType
                                                                .FEN_LIE.ordinal()]
                    = 2;
            } catch (NoSuchFieldError var3) {
                ;
            }

            try {
                $SwitchMap$atomicscience$TJianLi$JianLiType[TAutoBuilder.AutoBuilderType
                                                                .HUAN_YUAN.ordinal()]
                    = 3;
            } catch (NoSuchFieldError var2) {
                ;
            }

            try {
                $SwitchMap$atomicscience$TJianLi$JianLiType[TAutoBuilder.AutoBuilderType
                                                                .HE_CHENG.ordinal()]
                    = 4;
            } catch (NoSuchFieldError var1) {
                ;
            }
        }
    }

    public static enum AutoBuilderType {
        JIA_SU_QI("Particle Accelerator"),
        FEN_LIE("Fission Reactor"),
        HE_CHENG("Fusion Reactor"),
        HUAN_YUAN("Breeder Reactor");

        public final String name;

        private AutoBuilderType(String name) {
            this.name = name;
        }

        public static TAutoBuilder.AutoBuilderType get(int id) {
            return id >= 0 && id < values().length ? values()[id] : null;
        }

        public TAutoBuilder.AutoBuilderType next() {
            return values()[(this.ordinal() + 1) % values().length];
        }
    }
}
