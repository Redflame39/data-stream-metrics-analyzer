package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

public class Metrics {
    public static ObservableList<String> code = FXCollections.observableArrayList();
    public static ObservableList<Spen> spenList = FXCollections.observableArrayList();
    public static ObservableList<Chepin.Context> pList = FXCollections.observableArrayList();
    public static ObservableList<Chepin.Context> mList = FXCollections.observableArrayList();
    public static ObservableList<Chepin.Context> cList = FXCollections.observableArrayList();
    public static ObservableList<Chepin.Context> tList = FXCollections.observableArrayList();

    static int totalSpen;

    public static void calculateMetrics() {
        deleteComments(code);
        calculateSpen();
        calculateChepin();
    }

    private static void calculateSpen() {
        spenList = Spen.initIdentifiers(code);
        Spen.countSpens(spenList, code);
        for (Spen s : spenList) {
            totalSpen += s.count;
        }
    }

    private static void calculateChepin() {
        Chepin.splitVariables(spenList, code);
        Chepin.splitIOVariables(spenList, code);
    }

    private static void deleteComments(ObservableList<String> sourceList) {
        Iterator<String> iter = sourceList.iterator();
        while (iter.hasNext()) {
            String s = iter.next();
            if (s.trim().startsWith("/**") || s.trim().startsWith("/*")) {
                do {
                    iter.remove();
                    s = iter.next();
                } while (!(s.trim().startsWith("*/") || s.trim().endsWith("*/")));
            }
            if (s.trim().startsWith("*/") || s.trim().startsWith("//") || s.trim().endsWith("*/")) iter.remove();
        }
    }
}
