package org.bonn.se.services.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class DatenStudentProfil {
  private static final   Collection<String>  collection  = new ArrayList<>(Arrays.asList(
          "Staatsexamen",
          "Diplom",
          "Magister",
          "Master",
          "Bachelor",
          "Abitur (allgemeine oder fachgebundene Hochschulreife)",
          "Fachhochschulreife (allgemeine oder fachgebundene Fachhochschulreife)",
          "Mittlerer Schulabschluss (Realschulabschluss und vergleichbare Schulabschlüsse)"));


  private static final   Collection<String>  collection2  = new ArrayList<>(Arrays.asList(
          "A1",
          "A2",
          "B1",
          "B2",
          "C1",
          "C2"));
  private static final   Collection<String>  collection3  = new ArrayList<>(Arrays.asList(
          "Grundkenntnisse",
          "Gut",
          "Sehr Gut"));

  public static Collection<String> getCollection() {
    return collection;
  }

  public static Collection<String> getCollection2() {
    return collection2;
  }

  public static Collection<String> getCollection3() {
    return collection3;
  }
}








