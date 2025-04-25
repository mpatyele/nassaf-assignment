package com.example;

import java.util.*;
import org.json.simple.*;

public class EgyptianPyramidsAppExample {

  // I've used two arrays here for O(1) reading of the pharaohs and pyramids.
  // other structures or additional structures can be used
  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;
  protected HashMap<String, String> hieroglyphicToPharaoh;
  protected HashMap<String, Integer> goldByName;
  protected Set<Integer> requestedPyramids = new HashSet<>();

  public static void main(String[] args) {
    // create and start the app
    EgyptianPyramidsAppExample app = new EgyptianPyramidsAppExample();
    app.start();
  }

  // main loop for app
  public void start() {
    Scanner scan = new Scanner(System.in);
    Character command = '_';

    // loop until user quits
    while (command != 'q') {
      printMenu();
      System.out.print("Enter a command: ");
      command = menuGetCommand(scan);

      executeCommand(scan, command);
    }
  }

  // constructor to initialize the app and read commands
  public EgyptianPyramidsAppExample() {
    // read egyptian pharaohs
    String pharaohFile = "C:\\Users\\maria\\Documents\\GitHub\\NassefAssignment\\n" + //
        "assaf-assignment\\demo\\src\\main\\java\\com\\example\\pharaoh.json";
    JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFile);

    // create and intialize the pharaoh array

    initializePharaoh(pharaohJSONArray);

    // populate the hashmap
    hieroglyphicToPharaoh = new HashMap<>();
    for (Pharaoh pharaoh : pharaohArray) {
      hieroglyphicToPharaoh.put(pharaoh.hieroglyphic, pharaoh.name);
    }

    // populate the hashmap
    goldByName = new HashMap<>();
    for (Pharaoh pharaoh : pharaohArray) {
      goldByName.put(pharaoh.hieroglyphic, pharaoh.contribution);
    }

    // read pyramids
    String pyramidFile = "C:\\Users\\maria\\Documents\\GitHub\\NassefAssignment\\n" + //
        "assaf-assignment\\demo\\src\\main\\java\\com\\example\\pyramid.json";
    JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFile);

    // create and initialize the pyramid array
    initializePyramid(pyramidJSONArray);

  }

  // initialize the pharaoh array
  private void initializePharaoh(JSONArray pharaohJSONArray) {
    // create array and hash map
    pharaohArray = new Pharaoh[pharaohJSONArray.size()];

    // initalize the array
    for (int i = 0; i < pharaohJSONArray.size(); i++) {
      // get the object
      JSONObject o = (JSONObject) pharaohJSONArray.get(i);

      // parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      Integer begin = toInteger(o, "begin");
      Integer end = toInteger(o, "end");
      Integer contribution = toInteger(o, "contribution");
      String hieroglyphic = o.get("hieroglyphic").toString();

      // add a new pharoah to array
      Pharaoh p = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
      pharaohArray[i] = p;
    }
  }

  // initialize the pyramid array
  private void initializePyramid(JSONArray pyramidJSONArray) {
    // create array and hash map
    pyramidArray = new Pyramid[pyramidJSONArray.size()];

    // initalize the array
    for (int i = 0; i < pyramidJSONArray.size(); i++) {
      // get the object
      JSONObject o = (JSONObject) pyramidJSONArray.get(i);

      // parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
      String[] contributors = new String[contributorsJSONArray.size()];
      for (int j = 0; j < contributorsJSONArray.size(); j++) {
        String c = contributorsJSONArray.get(j).toString();
        contributors[j] = c;
      }

      // add a new pyramid to array
      Pyramid p = new Pyramid(id, name, contributors);
      pyramidArray[i] = p;
    }
  }

  // get a integer from a json object, and parse it
  private Integer toInteger(JSONObject o, String key) {
    String s = o.get(key).toString();
    Integer result = Integer.parseInt(s);
    return result;
  }

  // get first character from input
  private static Character menuGetCommand(Scanner scan) {
    Character command = '_';

    String rawInput = scan.nextLine().trim();

    if (rawInput.length() > 0) {
      rawInput = rawInput.toLowerCase();
      command = rawInput.charAt(0);
    }

    return command;
  }

  // print all pharaohs
  private void printAllPharaoh() {
    for (int i = 0; i < pharaohArray.length; i++) {
      printMenuLine();
      pharaohArray[i].print();
      printMenuLine();
    }
  }

  // print all pyramids
  private void printAllPyramid() {
    for (Pyramid pyramid : pyramidArray)  {
      printMenuLine();
      pyramid.print(hieroglyphicToPharaoh, goldByName);
      printMenuLine();
    }
}

  // get the ID from the User to select the Pharaoh
  private static Integer getPharaohID(Scanner scan) {
    while (true) {
      try {
        System.out.print("Enter the Pharaoh ID: ");
        int pharaohID = scan.nextInt();
        scan.nextLine();
        return pharaohID;
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a valid integer.");
        scan.nextLine(); // Clear the invalid input from the scanner
      }
    }
  }

  // Show the requested Pharaoh
  private void displayPharaoh(int pharaohID) {
    boolean found = false;
    for (Pharaoh pharaoh : pharaohArray) {
      if (pharaoh.id == pharaohID) {
        printMenuLine();
        pharaoh.print();
        printMenuLine();
        found = true;
        break;
      }
    }
    if (!found) {
      System.out.println();
      System.out.println("Pharaoh with ID " + pharaohID + " not found.");
    }
  }

  // get the ID from the User to select the Pyramid
  private static Integer getPyramidID(Scanner scan, Set<Integer> requestedPyramids) {
    while (true) {
      try {
        System.out.print("Enter the Pyramid ID: ");
        int pyramidID = scan.nextInt();
        scan.nextLine();
        requestedPyramids.add(pyramidID);
        return pyramidID;
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a valid integer.");
        scan.nextLine(); // Clear the invalid input from the scanner
      }
    }
  }

  // Show the requested Pyramid
  private void displayPyramid(int pyramidID) {
    boolean found = false;
    for (Pyramid pyramid : pyramidArray) {
      if (pyramid.id == pyramidID) {
        printMenuLine();
        pyramid.print(hieroglyphicToPharaoh, goldByName);
        printMenuLine();
        found = true;
        break;
      }
    }
    if (!found) {
      System.out.println();
      System.out.println("Pyramid with ID " + pyramidID + " not found.");
    }
  }

  private void displayRequestedPyramids() {
    System.out.println("List of requested pyramid: ");
    System.out.printf("ID\t\tName\n");
    System.out.printf("-------\t\t---------------------------------------\n");
    for (int pyramidID : requestedPyramids) {
      for (Pyramid pyramid : pyramidArray) {
        if (pyramid.id == pyramidID) {
          printRequestedPyramids(pyramid.id, pyramid.name);
          break;
        }
      }
    }
  }

  // Method to print the previously requested Pyramids
  private static void printRequestedPyramids(Integer id, String name) {
    System.out.printf("%s\t\t%s\n", id, name);
  }

  private Boolean executeCommand(Scanner scan, Character command) {
    Boolean success = true;

    switch (command) {
      case '1':
        printAllPharaoh();
        break;
      case '2':
        int pharaohID = getPharaohID(scan);
        displayPharaoh(pharaohID);
        break;
      case '3':
        printAllPyramid();
        break;
      case '4':
        int pyramidID = getPyramidID(scan, requestedPyramids);
        displayPyramid(pyramidID);
        break;
      case '5':
        displayRequestedPyramids();
        break;
      case 'q':
        System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
        break;
      default:
        System.out.println("ERROR: Unknown commmand");
        success = false;
    }

    return success;
  }

  private static void printMenuCommand(Character command, String desc) {
    System.out.printf("%s\t\t%s\n", command, desc);
  }

  private static void printMenuLine() {
    System.out.println(
        "--------------------------------------------------------------------------");
  }

  // prints the menu
  public static void printMenu() {
    printMenuLine();
    System.out.println("Nassef's Egyptian Pyramids App");
    printMenuLine();
    System.out.printf("Command\t\tDescription\n");
    System.out.printf("-------\t\t---------------------------------------\n");
    printMenuCommand('1', "List all the pharoahs");
    printMenuCommand('2', "Displays a specific Egyptian pharaoh");
    printMenuCommand('3', "List all the pyramids");
    printMenuCommand('4', "Displays a specific pyramid");
    printMenuCommand('5', "Displays a list of requested pyramid");
    printMenuCommand('q', "Quit");
    printMenuLine();
  }
}
