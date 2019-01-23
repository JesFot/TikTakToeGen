public class MinMax
{
	private static void Min(Node node)
	{
		int value = 10;
		long id = -1;
		
		for (Node subNodes : node.subNodes)
		{
			Max(subNodes);
			if (subNodes.value < value)
			{
				value = subNodes.value;
				id = subNodes.id;
			}
		}
		
		if (id > -1)
		{
			node.value = value;
			node.bestId = id;
		}
	}
	
	private static void Min(OptimisedNode node)
	{
		int value = 10;
		long id = -1;
		
		for (OptimisedNode subNodes : node.getChilds())
		{
			if (subNodes == null)
			{
				break;
			}
			Max(subNodes);
			if (subNodes.value < value)
			{
				value = subNodes.value;
				id = subNodes.id;
			}
		}
		
		if (id > -1)
		{
			node.value = value;
			node.bestId = id;
		}
	}
	
	private static void Max(Node node)
	{
		int value = -10;
		long id = -1;
		
		for (Node subNodes : node.subNodes)
		{
			Min(subNodes);
			if (subNodes.value > value)
			{
				value = subNodes.value;
				id = subNodes.id;
			}
		}
		
		if (id > -1)
		{
			node.value = value;
			node.bestId = id;
		}
	}
	
	private static void Max(OptimisedNode node)
	{
		int value = -10;
		long id = -1;
		
		for (OptimisedNode subNodes : node.getChilds())
		{
			if (subNodes == null)
			{
				break;
			}
			Min(subNodes);
			if (subNodes.value > value)
			{
				value = subNodes.value;
				id = subNodes.id;
			}
		}
		
		if (id > -1)
		{
			node.value = value;
			node.bestId = id;
		}
	}
	
	public static void CalcMinMax(Node root, final int self)
	{
		if (self == Main.FIRST)
		{
			Max(root);
		}
		else
		{
			Min(root);
		}
	}
	
	public static void CalcMinMax(OptimisedNode root, final int self)
	{
		if (self == Main.FIRST)
		{
			Max(root);
		}
		else
		{
			Min(root);
		}
	}
}