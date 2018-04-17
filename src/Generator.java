
public class Generator
{
	private static long id = 0;
	
	public static Node generate()
	{
		char map[][] = new char[Main.MAP_SIZE][Main.MAP_SIZE];
		
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			for (int j = 0; j < Main.MAP_SIZE; ++j)
			{
				map[i][j] = Main.NONE;
			}
		}
		
		Node start = new Node(map);
		start.id = id++;
		genSubs1(map, start, 0);
		return start;
	}
	
	public static boolean checkWin(final char map[][], final int c)
	{
		int cnt = 0;
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			if (map[i][i] == c)
				cnt++;
		}
		if (cnt == 0)
			return false;
		if (cnt >= Main.WIN_LEN)
			return true;
		for (int a = 0; a < Main.MAP_SIZE; ++a)
		{
			cnt = 0;
			for (int i = 0; i < Main.MAP_SIZE; ++i)
			{
				if (map[a][i] == c)
					cnt++;
				if (cnt >= Main.WIN_LEN)
					return true;
			}
		}
		for (int a = 0; a < Main.MAP_SIZE; ++a)
		{
			cnt = 0;
			for (int i = 0; i < Main.MAP_SIZE; ++i)
			{
				if (map[i][a] == c)
					cnt++;
				if (cnt >= Main.WIN_LEN)
					return true;
			}
		}
		cnt = 0;
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			if (map[Main.MAP_SIZE - i - 1][i] == c)
				cnt++;
		}
		if (cnt >= Main.WIN_LEN)
			return true;
		return false;
	}
	
	public static void genSubs1(char map[][], Node parent, int placed)
	{
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			for (int j = 0; j < Main.MAP_SIZE; ++j)
			{
				if (map[i][j] != Main.NONE)
					continue;
				map[i][j] = Main.FIRST;
				Node nNode = new Node(map);
				nNode.id = id++;
				if (!checkWin(map, Main.FIRST))
				{
					genSubs2(map, nNode, placed + 1);
				}
				parent.subNodes.add(nNode);
				map[i][j] = Main.NONE;
			}
		}
	}
	
	public static void genSubs2(char map[][], Node parent, int placed)
	{
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			for (int j = 0; j < Main.MAP_SIZE; ++j)
			{
				if (map[i][j] != Main.NONE)
					continue;
				map[i][j] = Main.SECOND;
				Node nNode = new Node(map);
				nNode.id = id++;
				if (!checkWin(map, Main.SECOND))
				{
					genSubs1(map, nNode, placed + 1);
				}
				parent.subNodes.add(nNode);
				map[i][j] = Main.NONE;
			}
		}
	}
	
	public static void applyIsEnd(Node node)
	{
		if (node.subNodes.isEmpty())
		{
			node.isEnd = true;
		}
		for (Node nd : node.subNodes)
		{
			applyIsEnd(nd);
		}
	}
	
	public static void applyEndValue(Node node, final int self)
	{
		if (node.isEnd == true)
		{
			if (checkWin(node.map, Main.FIRST))
			{
				node.value = (self == Main.FIRST) ? 1 : -1;
			}
			else if (checkWin(node.map, Main.SECOND))
			{
				node.value = (self == Main.FIRST) ? -1 : 1;
			}
			else
			{
				node.value = 0;
			}
		}
		for (Node nd : node.subNodes)
		{
			applyEndValue(nd, self);
		}
	}
}