import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node implements Serializable
{
	private static final long serialVersionUID = -6524808074581681802L;

	public long id;
	
	public boolean isEnd;
	public int value;
	public long bestId;
	public int placed;
	char map[][] = new char[Main.MAP_SIZE][];
	
	public List<Node> subNodes = new ArrayList<>();
	
	public Node(char map[][])
	{
		if (map != null)
		{
			for (int i = 0; i < Main.MAP_SIZE; ++i)
			{
				this.map[i] = Arrays.copyOf(map[i], Main.MAP_SIZE);
			}
		}
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeLong(id);
		out.writeBoolean(isEnd);
		out.writeInt(value);
		out.writeLong(bestId);
		out.writeObject(map);
		out.writeObject(subNodes);
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		id = in.readLong();
		isEnd = in.readBoolean();
		value = in.readInt();
		bestId = in.readLong();
		map = (char[][])in.readObject();
		subNodes = (List<Node>)in.readObject();
	}
}