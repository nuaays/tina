import java.util.concurrent.atomic.AtomicInteger;

import com.zhongan.cashier.TradeNoGeneration;

public class TestGenerator {

	TradeNoGeneration generator = new TradeNoGeneration();

	public static void main(String [] args){
		
		for (int i=0;i<4;i++)
		{
		final AtomicInteger counter = new AtomicInteger(0);

		String s = String.format("C%05d",counter.incrementAndGet());
		System.out.println(s);
		}
		
}
}
