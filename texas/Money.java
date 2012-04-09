package texas;
public class Money
{
    private int amount;
    private int profit;
    public Money()
    {
	    amount = 500;
	    profit = 0;
	}
	public Money(int m)
	{
	    amount = m;
	    profit = 0;
	}
	public void add(int a)
	{
	    amount+= a;
	    profit += a;
	}
	public void remove(int b)
	{
	    if (b >= amount)
	    {
	        profit -= amount;
	        amount = 0;
	    }
	    else
	    {
	        amount -= b;
	        profit -= b;
	    }
	}
	public int chipstack()
	{
	    return amount;
	}
	public void loan(double c)
	{
	    int d = (int) (c+.5);
	    amount +=c;
	    profit -= c;
	}
	public int getProfit()
	{
	    return profit;
	}
}