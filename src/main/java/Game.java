import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{

    private final boolean DEBUG = false;
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private HashMap<String, Objects> myPossessions;

    private Room[] allRooms;
    private Characters[] allCharacters;
    private Objects[] allObjects;

    private String possibleDirections[] = {"north", "south", "east", "west"};

    private Random randomNum = new Random();

    private static int trials = 0;
    private static double totalPossessionWeight = 0;
    private static String lastMove = "";
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        myPossessions = new HashMap<>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, virtualReality, jungle, darkRoom, underWater, fireRoom, mirrorRoom, gardenRoom, princessRoom;
        Objects goggles, potion, torch, mask, boots, wand, sand, flowers, glasses, ring;
        Characters warrior, knight, diver, shark, eagle, climber, princess;
      
        // Create the rooms
        outside = new Room("OUTSIDE", "outside the main entrance of the university", null);
        virtualReality = new Room("VIRTUAL REALITY", "in VIRTUAL REALITY ROOM. The origin of fake reality", new Objects("goggles", 200));
        jungle = new Room("JUNGLE ROOM", "in the JUNGLE ROOM. Find the potion, or find your destiny",  new Objects("potion", 200));
        darkRoom = new Room("DARK ROOM", "in the DARK ROOM. The torch of light is the torch of life", new Objects("torch", 200));
        underWater = new Room("UNDERWATER ROOM", "in the UNDERWATER ROOM. Find the Oxygen mask,  that's your survival task", new Objects("mask", 200));
        fireRoom = new Room("FIRE ROOM", "in the FIRE ROOM. Look for the boots", new Objects("boots", 200));
        mirrorRoom = new Room("MIRROR ROOM", "in the MIRROR ROOM. Find the glasses, it's your only way to see through", new Objects("glasses", 200));
        gardenRoom = new Room("GARDEN ROOM", "in the GARDEN ROOM. You are about to be transported. Watch your move!!!", null);
        princessRoom = new Room("PRINCESS ROOM", "in the PRINCESS ROOM. Find the princess before you go outside", new Objects("wand", 0));

        // initialise room exits
        outside.setExit("north", fireRoom);
        outside.setExit("south", virtualReality);
        outside.setExit("east", gardenRoom);
        outside.setExit("west", jungle);

        virtualReality.setExit("north", outside);
        virtualReality.setExit("east", jungle);
        virtualReality.setExit("west", darkRoom);

        jungle.setExit("south", fireRoom);
        jungle.setExit("east", outside);
        jungle.setExit("west", virtualReality);

        darkRoom.setExit("south", princessRoom);
        darkRoom.setExit("east", virtualReality);

        underWater.setExit("south", gardenRoom);
        underWater.setExit("east", fireRoom);
        underWater.setExit("west", mirrorRoom);

        fireRoom.setExit("north", jungle);
        fireRoom.setExit("south", outside);
        fireRoom.setExit("west", underWater);

        mirrorRoom.setExit("east", underWater);
        mirrorRoom.setExit("west", princessRoom);

        gardenRoom.setExit("north", underWater);
        gardenRoom.setExit("west", outside);

        princessRoom.setExit("north", darkRoom);
        princessRoom.setExit("east", mirrorRoom);

        // Add all rooms to allRooms
        allRooms = new Room[]{outside, virtualReality, jungle, darkRoom, underWater, fireRoom, mirrorRoom, gardenRoom, princessRoom};

        // Create Objects
        goggles = new Objects("goggles", 200);
        potion = new Objects("potion", 200);
        torch = new Objects("torch", 200);
        mask = new Objects("mask", 200);
        boots = new Objects("boots", 200);
        wand = new Objects("wand", 0);
        sand = new Objects("sand", 150);
        flowers = new Objects("flowers", 50);
        glasses = new Objects("glasses", 200);
        ring = new Objects("ring", 75);

        // Add all Objects to allObjects
        allObjects = new Objects[]{goggles, potion, torch, mask, boots, sand, flowers, glasses, ring};

        // Create Characters
        warrior = new Characters("warrior", outside);
        knight = new Characters("knight", outside);
        diver = new Characters("diver", outside);
        shark = new Characters("shark", outside);
        eagle = new Characters("eagle", outside);
        climber = new Characters("climber", outside);
        princess = new Characters("princess", outside);

        // Add all Characters to allCharacters
        allCharacters = new Characters[]{warrior, knight, diver, shark, eagle, climber, princess};

        // Add all objects (except wand) to outside room
        outside.addObject(goggles);
        outside.addObject(potion);
        outside.addObject(torch);
        outside.addObject(mask);
        outside.addObject(boots);
        outside.addObject(sand);
        outside.addObject(flowers);
        outside.addObject(glasses);
        outside.addObject(ring);

        // Add all objects (except wand) to virtualReality room
        virtualReality.addObject(goggles);
        virtualReality.addObject(potion);
        virtualReality.addObject(torch);
        virtualReality.addObject(mask);
        virtualReality.addObject(boots);
        virtualReality.addObject(sand);
        virtualReality.addObject(flowers);
        virtualReality.addObject(glasses);
        virtualReality.addObject(ring);

        // Add all objects (except wand) to jungle room
        jungle.addObject(goggles);
        jungle.addObject(potion);
        jungle.addObject(torch);
        jungle.addObject(mask);
        jungle.addObject(boots);
        jungle.addObject(sand);
        jungle.addObject(flowers);
        jungle.addObject(glasses);
        jungle.addObject(ring);

        // Add all objects (except wand) to darkRoom room
        darkRoom.addObject(goggles);
        darkRoom.addObject(potion);
        darkRoom.addObject(torch);
        darkRoom.addObject(mask);
        darkRoom.addObject(boots);
        darkRoom.addObject(sand);
        darkRoom.addObject(flowers);
        darkRoom.addObject(glasses);
        darkRoom.addObject(ring);

        // Add all objects (except wand) to underWater room
        underWater.addObject(goggles);
        underWater.addObject(potion);
        underWater.addObject(torch);
        underWater.addObject(mask);
        underWater.addObject(boots);
        underWater.addObject(sand);
        underWater.addObject(flowers);
        underWater.addObject(glasses);
        underWater.addObject(ring);

        // Add all objects (except wand) to fireRoom room
        fireRoom.addObject(goggles);
        fireRoom.addObject(potion);
        fireRoom.addObject(torch);
        fireRoom.addObject(mask);
        fireRoom.addObject(boots);
        fireRoom.addObject(sand);
        fireRoom.addObject(flowers);
        fireRoom.addObject(glasses);
        fireRoom.addObject(ring);

        // Add all objects (except wand) to mirrorRoom room
        mirrorRoom.addObject(goggles);
        mirrorRoom.addObject(potion);
        mirrorRoom.addObject(torch);
        mirrorRoom.addObject(mask);
        mirrorRoom.addObject(boots);
        mirrorRoom.addObject(sand);
        mirrorRoom.addObject(flowers);
        mirrorRoom.addObject(glasses);
        mirrorRoom.addObject(ring);

        // Add all objects (except wand) to gardenRoom room
        gardenRoom.addObject(goggles);
        gardenRoom.addObject(potion);
        gardenRoom.addObject(torch);
        gardenRoom.addObject(mask);
        gardenRoom.addObject(boots);
        gardenRoom.addObject(sand);
        gardenRoom.addObject(flowers);
        gardenRoom.addObject(glasses);
        gardenRoom.addObject(ring);

        // Add all objects to princessRoom room
        princessRoom.addObject(goggles);
        princessRoom.addObject(potion);
        princessRoom.addObject(torch);
        princessRoom.addObject(mask);
        princessRoom.addObject(boots);
        princessRoom.addObject(wand);
        princessRoom.addObject(sand);
        princessRoom.addObject(flowers);
        princessRoom.addObject(glasses);
        princessRoom.addObject(ring);

        // Add all characters (except Princess) to outside room
        outside.admitCharacter(warrior);
        outside.admitCharacter(knight);
        outside.admitCharacter(diver);
        outside.admitCharacter(shark);
        outside.admitCharacter(eagle);
        outside.admitCharacter(climber);

        // Add Princess to princess room
        princessRoom.admitCharacter(princess);

        currentRoom = outside;  // start game outside
        previousRoom = outside;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
        System.out.println();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            System.out.println();
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);

            // If we are in the Garden room, we will be moved to another randomly selected room (excluding the Princess Room)
            while(currentRoom.getRoomName().equalsIgnoreCase("GARDEN ROOM")) {
                Room tempRoom = allRooms[randomNum.nextInt(allRooms.length - 1)];
                currentRoom.setIsCurrentRoom(false);
                previousRoom = currentRoom;
                currentRoom = tempRoom;
                currentRoom.setIsCurrentRoom(true);
                lastMove = "";
                System.out.println("Transporting from " + previousRoom.getRoomName() + " to " + currentRoom.getRoomName());
                gameStatusString();
            }
        }
        else if (commandWord.equals("back")) {
            goBack();
        }
        else if (commandWord.equals("pick")) {
            pickObject(command);
        }
        else if (commandWord.equals("drop")) {
            dropObject(command);
        }
        else if (commandWord.equals("give")) {
            giveObject(command);
        }
        else if (commandWord.equals("take")) {
            takeObject(command);
        }
        else if (commandWord.equals("status")) {
            fullStatus();
        }
        else if (commandWord.equals("rooms")) {
            getRoomSummary();
        }
        else if (commandWord.equals("characters")) {
            getCharacterSummary();
        }
        else if (commandWord.equals("objects")) {
            getObjectSummary();
        }
        else if (commandWord.equals("clear")) {
            for(int i = 0; i < 50; i++) {
                System.out.println();
            };
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        gameStatusString();
    }

    /**
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            System.out.println();
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
            System.out.println();
        }
        else {
            boolean canExit = (false || (currentRoom.getSecretOutName().equalsIgnoreCase("")));
            String sercetKey = currentRoom.getSecretOutName();;
            if(DEBUG) {
                System.out.println("Current Room's Secret Key: " + sercetKey);
            }
            Set<String> keys = myPossessions.keySet();
            for(String itemName : keys) {
                if(DEBUG) {
                    System.out.println("Possession: " + itemName);
                    System.out.println("SecretKey: [" + sercetKey + "],\tItemName: [" + itemName + "]");
                    System.out.println("(sercetKey.equalsIgnoreCase(itemName)): --> " + (sercetKey.equalsIgnoreCase(itemName)));
                }
                if(sercetKey.equalsIgnoreCase(itemName)) {
                    canExit = true;
                    break;
                }
            }
            if(DEBUG) {
                System.out.println("CanExit: " + canExit);
                System.out.println("MyPossessions's Size(): " + myPossessions.size());
                System.out.println("(!canExit && trials < 5) -->: " + (!canExit && trials < 5));
            }
            // If player doesn't have the secret Object to exit, keep them trapped until they pick the
            // secret object up or go for 5 rounds
            if (!canExit && trials < 5) {
                System.out.println("You must have the Secret Object to exit " + currentRoom.getRoomName() + "!!!");
                System.out.println("HINT: Try to find the secret object: " + currentRoom.getSecretOutName() + ", or lose 5 moves!!!");
                System.out.println();
                trials++;
                return;
            }
            // Once the player is out to a new room, reset the trial counter to zero
            trials = 0;

            // Maintain less than 500 weight units of possessions to move from one room to another
            if(totalPossessionWeight >= 500) {
                System.out.println("You have more than the allowed possessions to transport from one room to another!!!");
                System.out.println("Consider dropping some of your possessions in the current room, or donating them to a friend!!!");
                return;
            }

            // locator is an integer used to locate array elements randomly to assign objects to characters
            // and assign characters to rooms
            int locator;
            // randomRoom, randomCharacter and randomObject will be used with a successful move to move characters
            // between rooms during gameplay, and allow different characters to move Objects items while they are moving
            Room randomRoom;
            Characters randomCharacter;
            Objects randomObject;

            previousRoom = currentRoom;
            currentRoom = nextRoom;
            previousRoom.setIsCurrentRoom(false);
            currentRoom.setIsCurrentRoom(true);

            lastMove = direction;

            if(currentRoom.getRoomName().equalsIgnoreCase("outside") && myPossessions.containsKey("wand")) {
                System.out.println("You WON!!!");
                System.exit(0);
            }

            // Randomly move a character from one room to another room
            locator = randomNum.nextInt(allCharacters.length);
            randomCharacter = allCharacters[locator];
            locator = randomNum.nextInt(allRooms.length);
            randomRoom = allRooms[locator];

            randomCharacter.getCurrentRoom().removeCharacter(randomCharacter.getTitle());
            randomCharacter.setCurrentRoom(randomRoom);
            randomRoom.admitCharacter(randomCharacter);

            // Randomly let a character acquire an object
            locator = randomNum.nextInt(allObjects.length);
            randomObject = allObjects[locator];
            // Pick any character other than the princess
            locator = randomNum.nextInt(allCharacters.length - 1);
            randomCharacter = allCharacters[locator];

            randomCharacter.addPossession(randomObject);

            gameStatusString();
        }
    }

    /**
     * Try to pick an object. If there is such an object, add it to our
     * possessions, otherwise print an error message.
     */
    private void pickObject(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pick..
            System.out.println("Pick What?");
            System.out.println();
            return;
        }

        String objectName = command.getSecondWord();

        // Try to retrieve object from current room.
        Objects object = currentRoom.removeObject(objectName);

        if (object == null) {
            System.out.println("There is no such object!");
            System.out.println();
        }
        else {
            myPossessions.put(object.getItemName(), object);
            totalPossessionWeight += object.getItemWeight();
            gameStatusString();
        }
    }

    /**
     * Try to drop an object. If such object is in
     * myPossessions, remove it and insert it to the room's objects,
     * otherwise print an error message.
     */
    private void dropObject(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop What?");
            System.out.println();
            return;
        }

        String objectName = command.getSecondWord();

        // Try to remove the object from my possessions.
        Objects object = myPossessions.remove(objectName);

        if (object == null) {
            System.out.println("You do not have such object!");
            System.out.println();
        }
        else {
            currentRoom.addObject(object);
            totalPossessionWeight -= object.getItemWeight();
            gameStatusString();
        }
    }

    /**
     * Try to take an object from a character. If there is such an object in their possessions
     * , add it to our possessions, otherwise print an error message.
     */
    private void giveObject(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to give..
            System.out.println("Give What?");
            System.out.println("Usage: Give [Object] [Recipient]");
            System.out.println();
            return;
        }

        if(!command.hasThirdWord()) {
            // if there is no third word, we don't know who to give to from..
            System.out.println("Give to who?");
            System.out.println("Usage: Give [Object] [Recipient]");
            System.out.println();
            return;
        }

        String objectName = command.getSecondWord();
        String recipientName = command.getThirdWord();

        // Try to find the designated recipient.
        Characters recipient = currentRoom.getCharacter(recipientName);

        if (recipient == null) {
            System.out.println("There is no such a character!");
            System.out.println();
            return;
        }

        // Try to find and remove the offered Objects Item in MyPossessions
        Objects object = myPossessions.remove(objectName);
        if (object == null) {
            System.out.println("There is no such object in your possession!");
            System.out.println();
            return;
        }

        // Transfer the item to the recipient
        recipient.addPossession(object);
        totalPossessionWeight -= object.getItemWeight();
        gameStatusString();
    }

    /**
     * Try to take an object from a character.
     * If there is such an object, add it to our possessions amd
     * remove it from theirs, otherwise print an error message.
     */
    private void takeObject(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take..
            System.out.println("Take What?");
            System.out.println("Usage: Take [Object] [Donor]");
            System.out.println();
            return;
        }

        if(!command.hasThirdWord()) {
            // if there is no third word, we don't know who to take from..
            System.out.println("Take from who?");
            System.out.println("Usage: Take [Object] [Donor]");
            System.out.println();
            return;
        }

        String objectName = command.getSecondWord();
        String donorName = command.getThirdWord();

        // try to find the donor in the current room
        Characters donor = currentRoom.getCharacter(donorName);

        if (donor == null) {
            System.out.println("There is no such a character!");
            return;
        }

        // Try to find the Objects item with the donor.
        Objects object = donor.removePossession(donor.getOnePossession(objectName));

        if (object == null) {
            System.out.println("The selected character does not have such object!");
            return;
        }
        myPossessions.put(object.getItemName(), object);
        totalPossessionWeight += object.getItemWeight();
        gameStatusString();
    }

    /**
     * Go back to previous room
     */
    private void goBack()
    {
        switch (lastMove) {
            case "north":
                goRoom(new Command("go", "south", ""));
                break;
            case "south":
                goRoom(new Command("go", "north", ""));
                break;
            case "east":
                goRoom(new Command("go", "west", ""));
                break;
            case "west":
                goRoom(new Command("go", "east", ""));
                break;
            default:
                System.out.println("You currently don't have any valid moves back!!!");
                break;
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Return a string describing the myPossessions' objects, for example
     * "My Objects: potion mask boots".
     * @return Details of the room's objects.
     */
    public String getMyPossessionString()
    {
        String returnString = "My Objects:";
        Set<String> keys = myPossessions.keySet();
        if(myPossessions.size() <= 0) {
            returnString += " [NONE]";
            return returnString + ".\n\tTotal possessions weight: " + totalPossessionWeight + " weight units";
        }
        for(String item : keys) {
            returnString += " " + item;
        }
        return returnString + ".\n\tTotal possessions weight: " + totalPossessionWeight + " weight units";
    }

    private void gameStatusString() {
        System.out.println(currentRoom.getLongDescription());
        System.out.println("====================================================================");
        System.out.println(getMyPossessionString());
        System.out.println("====================================================================");
        System.out.println("====================================================================");
        System.out.println("\n\n\n\n\n");
    }

    private void fullStatus() {
        getRoomSummary();
        System.out.println("====================================================================");
        System.out.println("====================================================================");
        getCharacterSummary();
        System.out.println("====================================================================");
        System.out.println("====================================================================");
        System.out.println("\n\n\n\n\n");
    }

    private void getRoomSummary() {
        for(Room r : allRooms) {
            System.out.println(r.getRoomSummary());
            System.out.println("====================================================================");
        }
    }

    private void getCharacterSummary() {
        for(Characters c : allCharacters) {
            System.out.println(c.getTitle() + ": " + c.getCurrentRoom().getRoomName() + "\t" + c.showAllPossessions());
        }
    }

    private void getObjectSummary() {
        for(Objects o : allObjects) {
            System.out.println(o.getItemName() + ": " + o.getItemWeight() + " weight units");
        }
    }
}
