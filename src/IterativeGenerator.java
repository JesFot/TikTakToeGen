import java.util.ArrayDeque;
import java.util.Deque;

public class IterativeGenerator
{
	private IterativeGenerator()
	{}
	
	private static long id = 0;
	
	public static long getId()
	{
		return id;
	}
	
	public static Node generate()
	{
		Deque<Node> lifoStack = new ArrayDeque<>();
		
		Node root = new Node(null);
		root.map = new char[Main.MAP_SIZE][Main.MAP_SIZE];
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			for (int j = 0; j < Main.MAP_SIZE; ++j)
			{
				root.map[i][j] = Main.NONE;
			}
		}
		root.placed = 0;
		root.id = IterativeGenerator.id++;

		lifoStack.push(root);
		
		char pl = Main.FIRST;
		
		while (!lifoStack.isEmpty())
		{
			Node parent = lifoStack.pop();
			
			generateNextMoves(parent, pl);
			
			for (Node child : parent.subNodes)
			{
				if (!IterativeGenerator.checkWinningNode(child, pl))
				{
					lifoStack.push(child);
				}
			}
			
			pl = (pl == Main.FIRST) ? Main.SECOND : Main.FIRST;
		}
		
		lifoStack.clear();
		
		return root;
	}
	
	public static OptimisedNode generateOptimised()
	{
		Deque<OptimisedNode> lifoStack = new ArrayDeque<>();
		
		OptimisedNode root = new OptimisedNode(null, '\0', 0, 0);
		root.placed = 0;
		root.id = IterativeGenerator.id++;

		lifoStack.push(root);
		
		char pl = Main.FIRST;
		
		while (!lifoStack.isEmpty())
		{
			OptimisedNode parent = lifoStack.pop();
			
			generateNextMoves(parent, pl);
			
			for (OptimisedNode child : parent.getChilds())
			{
				if (child != null && !IterativeGenerator.checkWinningNode(child, pl))
				{
					lifoStack.push(child);
				}
			}
			
			pl = (pl == Main.FIRST) ? Main.SECOND : Main.FIRST;
		}
		
		lifoStack.clear();
		
		return root;
	}
	
	public static void generateNextMoves(Node node, char nextPlayer)
	{
		if (node.placed >= (Main.MAP_SIZE * Main.MAP_SIZE))
		{
			return;
		}
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			for (int j = 0; j < Main.MAP_SIZE; ++j)
			{
				if (node.map[i][j] == Main.NONE)
				{
					Node nextMove = new Node(node.map);
					nextMove.map[i][j] = nextPlayer;
					nextMove.id = IterativeGenerator.id++;
					nextMove.placed = node.placed + 1;
					node.subNodes.add(nextMove);
				}
			}
		}
	}
	
	public static void generateNextMoves(OptimisedNode node, char nextPlayer)
	{
		char[][] helperMap = WinChecker.getSampleMap();
		node.fillStateMap(helperMap);
		if (node.placed >= (Main.MAP_SIZE * Main.MAP_SIZE))
		{
			return;
		}
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			for (int j = 0; j < Main.MAP_SIZE; ++j)
			{
				if (helperMap[i][j] == Main.NONE)
				{
					OptimisedNode nextMove = new OptimisedNode(node, nextPlayer, i, j);
					nextMove.id = IterativeGenerator.id++;
					node.addChildNode(nextMove);
				}
			}
		}
	}
	
	public static boolean checkWinningNode(Node node, char player)
	{
		if (node.placed < Main.WIN_LEN)
		{
			return false;
		}
		return WinChecker.checkWinningAll(node, player);
	}
	
	public static boolean checkWinningNode(OptimisedNode node, char player)
	{
		if (node == null)
		{
			return false;
		}
		if (node.placed < Main.WIN_LEN)
		{
			return false;
		}
		return WinChecker.checkWinningAll(node, player);
	}
	
	public static void clearMap(char[][] map)
	{
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			for (int j = 0; j < Main.MAP_SIZE; ++j)
			{
				map[i][j] = Main.NONE;
			}
		}
	}
}