package Project;

import Project.CallbackPackage.MessageCallback;
import Project.TilePackage.PlayerPackage.Player;
import Project.TilePackage.TileFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

interface InputReader
{
    String read();
}
public class CLI {
    private static CLI instance;
    private MessageCallback m;
    private InputReader r;
    private Scanner scanner;
    private TileFactory factory;
    private LinkedList<Player> allPlayers;
    private int playerCount;
    private CLI()
    {

        m = (s) -> displayMessage(s);
        r = () -> readLine();
        scanner = new Scanner(System.in);
        factory = new TileFactory();
        playerCount = factory.getPlayerCount();
        allPlayers = new LinkedList<>();
        for(int i =1;i<=playerCount;i++)
        {
            allPlayers.add(factory.producePlayer(i,null,null,null));
        }
    }
    public static CLI getInstance()
    {
        if(instance== null)
        {
            instance = new CLI();
        }
        return instance;
    }
    private void displayMessage(String message)
    {
        System.out.println(message);
    }
    private String readLine()
    {
        return scanner.nextLine();
    }
    public MessageCallback getMessageCallback()
    {
        return m;
    }
    public InputReader getInputReader()
    {
        return r;
    }
    public Player getPlayer()
    {
        int i = -1;
        while( i == -1)
        {
            System.out.println("Select a player");
            printPlayers();
            try
            {
                int temp = Integer.parseInt(readLine());
                if(temp>0&&temp<=playerCount)
                {
                    i = temp;
                }
                else
                {
                    System.out.println("Invalid input!");
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid input!");
            }

        }
        return factory.producePlayer(i,null,null,null);
    }

    private void printPlayers() {
        Iterator<Player> iterator = allPlayers.iterator();
        for(int i =1;i<=playerCount;i++)
        {
            System.out.print(i + ". " +  iterator.next().describe());
            System.out.println("");
        }
    }

}
