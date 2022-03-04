// linxuan ji
// 2063382
// TA: Jun song
// January 29th, 2021
// This class keeps track of who kills the other one and store the killed person in the graveyard.
// Also,the program have method to printout the killing process and graveyard.
import java.util.*;


public class AssassinManager {
    private AssassinNode killRing;    // the list of the current people alive.
    private AssassinNode graveyard;   // the list of died poeple in the graveyard.
    
    // Constructs a new AssassinManager to use the list of people.
    // List<String>names - use peoples name to create a list in an order.
    // throw new IllegalArgumentException if the list of name is empty
    public AssassinManager(List<String> names) {
        if (names.isEmpty()) {
            throw new IllegalArgumentException();
        }
        for (int i = names.size() -1; i > -1; i--) {
            killRing = new AssassinNode(names.get(i), killRing);
        }
    }
    
    // Prints out the names of the people in the killring and show their own kill goal.
    public void printKillRing() {
        AssassinNode printKill = killRing;
        while (printKill.next != null) {
            System.out.println("    " + printKill.name + " is stalking " + printKill.next.name);
            printKill = printKill.next;
        }
        System.out.println("    " + printKill.name + " is stalking " + killRing.name);
        
    }
    
    // Prints out the died people in the graveyard that was killed by other person.
    public void printGraveyard() {
        AssassinNode printGrav = graveyard;
        while (printGrav != null) {
            System.out.println("    " + printGrav.name + " was killed by " + printGrav.killer);
            printGrav = printGrav.next;
        }
    }

    // A boolean to check whether the AssassinNode has current people of the given name.
    // retrun true when have current people of the given name.
    // String name - the given name of the list of people and ignore to lower case
    // AssassinNode N - the list will use to be checked.
    private boolean printCheck(String name, AssassinNode N) {
        AssassinNode current = N;
        while (current != null) {
            if (name.equalsIgnoreCase(current.name)){
                return true;
            } current = current.next;
        }
        return false;
    }

    // return true if the list KillRing contains the given name people.
    // String name - the given name of the list of people and ignore to lower case
    public boolean killRingContains(String name) {
        return printCheck(name, killRing);
    }

    // return true if the list graveyard contains the given name people.
    // String name - the given name of the list of people and ignore to lower case.
    public boolean graveyardContains(String name) {
        return printCheck(name, graveyard);
    }
    
    // return true if the game is over.
    public boolean gameOver() {
        return killRing.next == null;
    }
    
    // return null if the game is not over
    // return the name of the winner
    public String winner() {
        if (!gameOver()) {
            return null;
        }
        return killRing.name;
    }
    
    // Record the assassination of the person with the given name, and put the
    // died person from the killring to the front of the graveyard.
    // String name - the given name of the list of people and ignore to lower case.
    // throw new IllegalStateException if the game is over.
    // throw new IllegalArgumentException if the killring do not contains given name.
    public void kill(String name) {
        name = name.toLowerCase();
        if (gameOver()) {
            throw new IllegalStateException();
        } else if (!killRingContains(name)) {
            throw new IllegalArgumentException();
        }
        AssassinNode currFront = killRing;
        AssassinNode currKilled = graveyard;
        while (currFront.next != null && !currFront.next.name.equalsIgnoreCase(name)) {
            currFront = currFront.next;
        }
        if (killRing.name.equalsIgnoreCase(name)) {
            currKilled = killRing;
            killRing = killRing.next;
            
        } else {
            currKilled = currFront.next;
            currFront.next = currKilled.next;
        }
        currKilled.killer = currFront.name;
        currKilled.next = graveyard;
        graveyard = currKilled;
    }
}
