package personthecat.mod.objects.items;

import personthecat.mod.CreativeTab;
import personthecat.mod.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import personthecat.mod.init.ItemInit;
import personthecat.mod.util.IHasModel;

public class ItemBase extends Item implements IHasModel
{

private String name;
	
	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
//		setCreativeTab(CreativeTab.ORE_EXPANSION);
		
		this.name = name;
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer((this), name);
	}
}
