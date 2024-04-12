package atomicscience;

import atomicscience.recipe.RecipeBuilder;
import atomicscience.recipe.ShapedOreRecipeAdapter;
import atomicscience.recipe.ShapelessOreRecipeAdapter;
import basiccomponents.common.BasicComponents;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import universalelectricity.core.item.ElectricItemHelper;

public class Recipes {
    /**
     * Registers the recipes and OreDictionary entries.
     */
    public static void addRecipes() {
        OreDictionary.registerOre("cellEmpty", AtomicScience.itCell);

        new RecipeBuilder(new ShapelessOreRecipeAdapter())
            .output(new ItemStack(AtomicScience.itCellAntimatter, 1, 1))
            .ingredient(AtomicScience.itCellAntimatter)
            .ingredient(AtomicScience.itCellAntimatter)
            .ingredient(AtomicScience.itCellAntimatter)
            .ingredient(AtomicScience.itCellAntimatter)
            .ingredient(AtomicScience.itCellAntimatter)
            .ingredient(AtomicScience.itCellAntimatter)
            .ingredient(AtomicScience.itCellAntimatter)
            .ingredient(AtomicScience.itCellAntimatter)
            .register();

        new RecipeBuilder(new ShapelessOreRecipeAdapter())
            .output(AtomicScience.itCellAntimatter, 8)
            .ingredient(new ItemStack(AtomicScience.itCellAntimatter, 1, 1))
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bFunnel, 2)
            .pattern(" B ", "B B", "B B")
            .ingredient('B', "ingotBronze")
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bFunnel, 2)
            .pattern(" I ", "I I", "I I")
            .ingredient('I', "ingotIron")
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bAtomicAssembler)
            .pattern("CCC", "SXS", "SSS")
            .ingredient('X', AtomicScience.bCentrifuge)
            .ingredient('C', BasicComponents.itemCircuitElite)
            .ingredient('S', "plateSteel")
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bFulminationGenerator)
            .pattern("OSO", "SCS", "OSO")
            .ingredient('O', Blocks.obsidian)
            .ingredient('C', BasicComponents.itemCircuitAdvanced)
            .ingredient('S', "plateSteel")
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bAccelerator)
            .pattern("SCS", "CMC", "SCS")
            .ingredient('M', BasicComponents.itemMotor)
            .ingredient('C', BasicComponents.itemCircuitElite)
            .ingredient('S', "plateSteel")
            .register();

        new RecipeBuilder(new ShapelessOreRecipeAdapter())
            .output(AtomicScience.bElectromagnetGlass)
            .ingredient(AtomicScience.bElectromagnet)
            .ingredient(Blocks.glass)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bCentrifuge)
            .pattern("BSB", "MCM", "BSB")
            .ingredient('C', BasicComponents.itemCircuitAdvanced)
            .ingredient('S', "plateSteel")
            .ingredient('B', "ingotBronze")
            .ingredient('M', BasicComponents.itemMotor)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bNuclearBoiler)
            .pattern("S S", "FBF", "SMS")
            .ingredient('F', Blocks.furnace)
            .ingredient('S', "plateSteel")
            .ingredient('B', Items.bucket)
            .ingredient('M', BasicComponents.itemMotor)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bChemicalExtractor)
            .pattern("BSB", "MCM", "BSB")
            .ingredient('C', BasicComponents.itemCircuitElite)
            .ingredient('S', "plateSteel")
            .ingredient('B', "ingotBronze")
            .ingredient('M', BasicComponents.itemMotor)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bSiren, 2)
            .pattern("NPN")
            .ingredient('N', Blocks.noteblock)
            .ingredient('P', "plateBronze")
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bFissionReactor)
            .pattern("SCS", "MEM", "SCS")
            .ingredient('E', "cellEmpty")
            .ingredient('C', BasicComponents.itemCircuitAdvanced)
            .ingredient('S', "plateSteel")
            .ingredient('M', BasicComponents.itemMotor)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bFusionReactor)
            .pattern("CPC", "PFP", "CPC")
            .ingredient('P', "plateSteel")
            .ingredient('F', AtomicScience.bFissionReactor)
            .ingredient('C', BasicComponents.itemCircuitElite)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bTurbine)
            .pattern(" B ", "BMB", " B ")
            .ingredient('B', "plateBronze")
            .ingredient('M', BasicComponents.itemMotor)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bReactorTap)
            .pattern("SBS", "TMT", "SBS")
            .ingredient('S', "plateBronze")
            .ingredient('M', BasicComponents.itemMotor)
            .ingredient('B', Items.bucket)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.itCell, 16)
            .pattern(" T ", "TGT", " T ")
            .ingredient('T', "ingotTin")
            .ingredient('G', Blocks.glass)
            .register();

        new RecipeBuilder(new ShapelessOreRecipeAdapter())
            .output(AtomicScience.itCellWater)
            .ingredient(AtomicScience.itCell)
            .ingredient(Items.water_bucket)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(ElectricItemHelper.getUncharged(AtomicScience.itThermometer))
            .pattern("SSS", "GCG", "GSG")
            .ingredient('S', "ingotSteel")
            .ingredient('G', Blocks.glass)
            .ingredient('C', BasicComponents.itemCircuitBasic)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bThermometer)
            .pattern("SSS", "SWS", "SSS")
            .ingredient('S', "ingotSteel")
            .ingredient('W', ElectricItemHelper.getUncharged(AtomicScience.itThermometer))
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bControlRod)
            .pattern("I", "I", "I")
            .ingredient('I', Items.iron_ingot)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.itCellUranium)
            .pattern("CUC", "CUC", "CUC")
            .ingredient('U', AtomicScience.itUranium)
            .ingredient('C', "cellEmpty")
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.itCellBreederFuel)
            .pattern("CUC", "CUC", "CUC")
            .ingredient('C', "cellEmpty")
            .ingredient('U', "breederUranium")
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bElectromagnet, 2)
            .pattern("BBB", "BMB", "BBB")
            .ingredient('B', "ingotBronze")
            .ingredient('M', BasicComponents.itemMotor)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.bElectromagnet, 2)
            .pattern("III", "IMI", "III")
            .ingredient('I', Items.iron_ingot)
            .ingredient('M', BasicComponents.itemMotor)
            .register();

        new RecipeBuilder(new ShapelessOreRecipeAdapter())
            .output(AtomicScience.bElectromagnetBoiler)
            .ingredient(AtomicScience.bElectromagnet)
            .register();

        new RecipeBuilder(new ShapelessOreRecipeAdapter())
            .output(AtomicScience.bElectromagnet)
            .ingredient(AtomicScience.bElectromagnetBoiler)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.itHazmatHelmet)
            .pattern("SSS", " A ", "SCS")
            .ingredient('A', Items.leather_helmet)
            .ingredient('C', BasicComponents.itemCircuitBasic)
            .ingredient('S', Blocks.wool)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.itHazmatChestplate)
            .pattern("SSS", " A ", "SCS")
            .ingredient('A', Items.leather_chestplate)
            .ingredient('C', BasicComponents.itemCircuitBasic)
            .ingredient('S', Blocks.wool)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.itHazmanLeggings)
            .pattern("SSS", " A ", "SCS")
            .ingredient('A', Items.leather_leggings)
            .ingredient('C', BasicComponents.itemCircuitBasic)
            .ingredient('S', Blocks.wool)
            .register();

        new RecipeBuilder(new ShapedOreRecipeAdapter())
            .output(AtomicScience.itHazmatBoots)
            .pattern("SSS", " A ", "SCS")
            .ingredient('A', Items.leather_boots)
            .ingredient('C', BasicComponents.itemCircuitBasic)
            .ingredient('S', Blocks.wool)
            .register();
    }
}
