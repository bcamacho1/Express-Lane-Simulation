import java.util.ArrayList;
import java.util.Random;

public class Customer 
{
	private ArrayList<Item> itemList;
	private int maxItems; 
	
	public Customer(int maxItems)
	{
		itemList = new ArrayList<Item>(); 
		this.maxItems = maxItems;
		loadItems();
	}
	
	private void loadItems()
	{
		Random r = new Random();
		int total = r.nextInt(maxItems) +1;
		for(int i = 0; i<total; i++)
		{
			addItem(new Item());
		}
	}
	public void addItem( Item i )
	{
		itemList.add(i);
	}
	
	public int getNumOfItems()
	{
		return itemList.size();
	}
	
	public ArrayList<Item> getItems()
	{
		return itemList;
	}
}
