import java.io.Serializable;

public class OptimisedNode implements Serializable
{
	private static final long serialVersionUID = 4706819850775998113L;

	public long id;
	
	public boolean isEnd;
	public int value;
	public long bestId;
	public int placed;
	
	public int placeX;
	public int placeY;
	public char player;
	
	public OptimisedNode parent;
	
	private int pos = 0;
	private OptimisedNode[] childs = new OptimisedNode[Main.MAP_SIZE_POW];
	
	public OptimisedNode(OptimisedNode parent, char player, int x, int y)
	{
		if (parent != null)
		{
			this.parent = parent;
			this.placed = parent.placed + 1;
			this.placeX = x;
			this.placeY = y;
			this.player = player;
		}
	}
	
	public void addChildNode(OptimisedNode child)
	{
		if (pos < 0 || pos >= Main.MAP_SIZE_POW)
		{
			return;
		}
		childs[pos] = child;
		pos += 1;
	}
	
	public OptimisedNode[] getChilds()
	{
		return childs;
	}
	
	public boolean hasChildren()
	{
		return (pos > 0);
	}
	
	public void fillStateMap(char[][] map)
	{
		if (parent != null)
		{
			map[placeX][placeY] = player;
			parent.fillStateMap(map);
		}
	}
}