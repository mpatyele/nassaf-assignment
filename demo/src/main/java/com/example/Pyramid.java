package com.example;

import java.util.HashMap;

// pyramid class, that corresponds to the information in the json file
public class Pyramid {

  protected Integer id;
  protected String name;
  protected String[] contributors;

  // constructor
  public Pyramid(
    Integer pyramidId,
    String pyramidName,
    String[] pyramidContributors
  ) {
    id = pyramidId;
    name = pyramidName;
    contributors = pyramidContributors;
  }

    //  print Pyramid
    public void print (HashMap<String, String> hieroglyphicToPharaoh, HashMap<String, Integer> goldbyName) {
      System.out.printf("Pyramid %-5s\n", name);
      System.out.printf("\tid: %-5d\n", id);

      int totalContribution = 0;

        for (String contributor : contributors) {
          if (hieroglyphicToPharaoh.containsKey(contributor)){ // If it is, retrieve the corresponding pharaoh's name
          String pharaohName = hieroglyphicToPharaoh.get(contributor);
          Integer pharaohContribution = goldbyName.get(contributor);
          System.out.printf("\tContributor: %-10s %-5d Gold Coins\n", pharaohName, pharaohContribution);
          totalContribution += pharaohContribution;
          }
        }
        // Print total contribution
        System.out.printf("\tTotal Contribution: %-5d Gold Coins\n", totalContribution);
    }
}