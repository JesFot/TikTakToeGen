public class MinMax
{
	private static void Min(Node node)
	{
		int value = 10;
		
		for (Node subNodes : node.subNodes)
		{
			Max(subNodes);
			value = Math.min(value, subNodes.value);
		}
		
		if (value < 2 && value > -2)
			node.value = value;
	}
	
	private static void Max(Node node)
	{
		int value = -10;
		
		for (Node subNodes : node.subNodes)
		{
			Min(subNodes);
			value = Math.max(value, subNodes.value);
		}
		
		if (value < 2 && value > -2)
			node.value = value;
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
}