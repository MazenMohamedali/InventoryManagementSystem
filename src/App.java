
public class App 
{
    public static void main(String[] args)
    {
        try
        {
            var c = Client.getData(10000);
            System.out.println(c.get(0).id);

            Client client = new Client("null", 0, "null", "null", "null", "null", 0);
            Client.addToClientTable(client);
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}
