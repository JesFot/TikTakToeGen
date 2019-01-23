
public class WinChecker
{
	private WinChecker()
	{}
	
	private static char[][] sampleMap = new char[Main.MAP_SIZE][Main.MAP_SIZE];
	
	public static char[][] getSampleMap()
	{
		IterativeGenerator.clearMap(sampleMap);
		return sampleMap;
	}
	
	public static boolean checkWinningAll(Node node, char player)
	{
		boolean result = false;
		
		result |= WinChecker.checkWinningfNtS(node.map, player);
		result |= WinChecker.checkWinningfWtE(node.map, player);
		result |= WinChecker.checkWinningfNWtSE(node.map, player);
		result |= WinChecker.checkWinningfNEtSW(node.map, player);
		
		return result;
	}
	
	public static boolean checkWinningAll(OptimisedNode node, char player)
	{
		IterativeGenerator.clearMap(sampleMap);
		node.fillStateMap(sampleMap);
		boolean result = false;
		
		result |= WinChecker.checkWinningfNtS(sampleMap, player);
		result |= WinChecker.checkWinningfWtE(sampleMap, player);
		result |= WinChecker.checkWinningfNWtSE(sampleMap, player);
		result |= WinChecker.checkWinningfNEtSW(sampleMap, player);
		
		return result;
	}
	
	public static boolean checkWinningfNWtSE(char[][] map, char player)
	{
		int cnt = 0;
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			if (map[i][i] == player)
			{
				cnt++;
			}
		}
		return (cnt >= Main.WIN_LEN);
	}
	
	public static boolean checkWinningfNEtSW(char[][] map, char player)
	{
		int cnt = 0;
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			if (map[Main.MAP_SIZE - i - 1][i] == player)
			{
				cnt++;
			}
		}
		return (cnt >= Main.WIN_LEN);
	}
	
	public static boolean checkWinningfNtS(char[][] map, char player)
	{
		int cnt;
		for (int a = 0; a < Main.MAP_SIZE; ++a)
		{
			cnt = 0;
			for (int i = 0; i < Main.MAP_SIZE; ++i)
			{
				if (map[a][i] == player)
				{
					cnt++;
				}
				if (cnt >= Main.WIN_LEN)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean checkWinningfWtE(char[][] map, char player)
	{
		int cnt;
		for (int a = 0; a < Main.MAP_SIZE; ++a)
		{
			cnt = 0;
			for (int i = 0; i < Main.MAP_SIZE; ++i)
			{
				if (map[i][a] == player)
				{
					cnt++;
				}
				if (cnt >= Main.WIN_LEN)
				{
					return true;
				}
			}
		}
		return false;
	}
}