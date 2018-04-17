import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Main
{
	public static final char NONE = 0;
	public static final char FIRST = 1;
	public static final char SECOND = 2;
	
	public static final int MAP_SIZE = 3;
	public static final int WIN_LEN = 3;
	
	private static final int SELF = FIRST;
	
	private static AtomicInteger ai = new AtomicInteger(0);
	private static AtomicInteger ai_nd = new AtomicInteger(0);

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		long time_0 = System.nanoTime();
		
		FileOutputStream fos_out = new FileOutputStream("output.log", false);
		PrintStream old_out = System.out;
		PrintStream ps = new PrintStream(fos_out);
		
		System.setOut(ps);
		
		Node nd;

		long time_1 = System.nanoTime();
		
		System.err.println("Initializing: Completed, Generating nodes...");
		
		long time_2 = System.nanoTime();
		
		{
			nd = Generator.generate();
		}
		
		long time_3 = System.nanoTime();
		
		System.err.println("Generation: Completed, Writing isEnd value...");
		
		long time_4 = System.nanoTime();
		
		{
			Generator.applyIsEnd(nd);
		}
		
		long time_5 = System.nanoTime();
		
		System.err.println("Writing isEnd value: Completed, Calculating end nodes value...");
		
		long time_6 = System.nanoTime();
		
		{
			Generator.applyEndValue(nd, SELF);
		}
		
		long time_7 = System.nanoTime();
		
		System.err.println("Calculation: Completed, Applying MinMax...");
		
		long time_8 = System.nanoTime();
		
		{
			MinMax.CalcMinMax(nd, SELF);
		}
		
		long time_9 = System.nanoTime();
		
		System.err.println("Applying MinMax: Completed, Writing to output.log...");
		
		long time_10 = System.nanoTime();
		
		{
			parse(nd);
		}
		
		long time_11 = System.nanoTime();
		
		System.err.println("Printing: Completed, Writing nodes file...");
		
		long time_12 = System.nanoTime();
		
		{
			FileOutputStream fos = new FileOutputStream("nodesv2.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(nd);

			oos.flush();
			oos.close();
			fos.flush();
			fos.close();
		}
		
		long time_13 = System.nanoTime();
		
		System.err.println("Writing nodes: Completed");
		
		System.out.printf("Total endpoint: %d\n", ai.get());
		System.out.printf("Total nodes: %d\n", ai_nd.get());
		System.err.println();
		System.err.printf("[Treatment] Total endpoint: %d\n", ai.get());
		System.err.printf("[Treatment] Total nodes: %d\n", ai_nd.get());
		
		Duration duration_ini = Duration.ofNanos(time_1 - time_0);
		Duration duration_gen = Duration.ofNanos(time_3 - time_2);
		Duration duration_end = Duration.ofNanos(time_5 - time_4);
		Duration duration_val = Duration.ofNanos(time_7 - time_6);
		Duration duration_minmax = Duration.ofNanos(time_9 - time_8);
		Duration duration_prit = Duration.ofNanos(time_11 - time_10);
		Duration duration_writ = Duration.ofNanos(time_13 - time_12);
		Duration duration_all = Duration.ofNanos(time_13 - time_0);

		System.err.println();
		System.err.printf("[Timer] Intialization: %s\n", humanReadableFormat(duration_ini));
		System.err.printf("[Timer] Node Generation: %s\n", humanReadableFormat(duration_gen));
		System.err.printf("[Timer] Finding end nodes: %s\n", humanReadableFormat(duration_end));
		System.err.printf("[Timer] Victory search: %s\n", humanReadableFormat(duration_val));
		System.err.printf("[Timer] Mini-Maxi application: %s\n", humanReadableFormat(duration_minmax));
		System.err.printf("[Timer] Output to file: %s\n", humanReadableFormat(duration_prit));
		System.err.printf("[Timer] Writing: %s\n", humanReadableFormat(duration_writ));
		System.err.println();
		System.err.printf("[Timer] Overall timing: %s\n", humanReadableFormat(duration_all));

		System.out.println();
		System.out.println("Timer section :");
		System.out.println();
		System.out.printf("[Timer] Intialization: %s\n", humanReadableFormat(duration_ini));
		System.out.printf("[Timer] Node Generation: %s\n", humanReadableFormat(duration_gen));
		System.out.printf("[Timer] Finding end nodes: %s\n", humanReadableFormat(duration_end));
		System.out.printf("[Timer] Victory search: %s\n", humanReadableFormat(duration_val));
		System.out.printf("[Timer] Mini-Maxi application: %s\n", humanReadableFormat(duration_minmax));
		System.out.printf("[Timer] Output to file: %s\n", humanReadableFormat(duration_prit));
		System.out.printf("[Timer] Writing: %s\n", humanReadableFormat(duration_writ));
		System.out.println();
		System.out.printf("[Timer] Overall timing: %s\n", humanReadableFormat(duration_all));
		
		System.setOut(old_out);

		ps.flush();
		ps.close();
		fos_out.flush();
		fos_out.close();
	}
	
	public static void parse(Node node)
	{
		ai_nd.incrementAndGet();
		System.out.printf("Found %s #%d: value = %2d; map %s\n",
				(node.isEnd) ? "endpoint" : "node",
				node.id, node.value,
				mapToStr(node.map));
		if (node.isEnd == true)
		{
			ai.incrementAndGet();
		}
		else
		{
			System.out.printf("\t=> next best move: #%d\n", node.bestId);
		}
		for (Node nd : node.subNodes)
		{
			parse(nd);
		}
	}
	
	public static String humanReadableFormat(Duration duration) {
	    return duration.toString()
	            .substring(2)
	            .replaceAll("(\\d[HMS])(?!$)", "$1 ")
	            .toLowerCase();
	}
	
	public static String mapToStr(char map[][])
	{
		StringBuilder sb = new StringBuilder(3 + (5 + 3 * Main.MAP_SIZE) * Main.MAP_SIZE);
		
		sb.append('[');
		for (int i = 0; i < Main.MAP_SIZE; ++i)
		{
			sb.append('[');
			for (int j = 0; j < Main.MAP_SIZE; ++j)
			{
				sb.append(' ').append((int) map[i][j]);
				if (j != (Main.MAP_SIZE - 1))
				{
					sb.append(',');
				}
			}
			sb.append(' ').append(']');
			if (i != (Main.MAP_SIZE - 1))
			{
				sb.append(',').append(' ');
			}
		}
		sb.append(']');
		return sb.toString();
	}
}
