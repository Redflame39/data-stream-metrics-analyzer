package com.company;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Spen {
    String identifier;
    int count;

    SimpleStringProperty idWrap;
    SimpleStringProperty countWrap;

    public Spen(String id) {
        this.identifier = id;
        this.idWrap = new SimpleStringProperty(id);
        this.count = -1;
        this.countWrap = new SimpleStringProperty(Integer.toString(0));
    }

    public SimpleStringProperty getIdWrap() {
        return idWrap;
    }

    public void setIdWrap(String id) {
        this.idWrap.set(id);
    }

    public SimpleStringProperty getCountWrap() {
        return countWrap;
    }

    public void setCountWrap(int count) {
        this.countWrap.set(Integer.toString(count));
    }

    public static ObservableList<Spen> initIdentifiers(ObservableList<String> code) {
        ObservableList<Spen> result = FXCollections.observableArrayList();
        for (String line : code) {
            if (line.trim().startsWith("var") || line.trim().startsWith("val")) {
                char[] lineChar = line.trim().toCharArray();
                int i = 0;
                while (lineChar[i++] != ':');
                String var = line.trim().substring(3, i-1).trim();
                boolean exists = false;
                for (Spen s: result) {
                    if (s.identifier.equals(var)) {
                        exists = true;
                        break;
                    }
                }
                if(!exists) {
                    result.add(new Spen(var));
                }
            }
        }
        return result;
    }

    public static void countSpens(ObservableList<Spen> spenList, ObservableList<String> code) {
        for (int i = 0; i < spenList.size(); i++) {
            String currentId = spenList.get(i).identifier;
            for (String line: code) {
                /*if (line.contains(currentId)) {
                    line = line.trim();
                    int pos = 0;
                    while (true) {
                        pos = line.indexOf(currentId, pos);
                        if (pos == -1)
                            break;
                        if (pos + currentId.length() != line.length()) {
                            if (pos != 0) {
                                if (Character.isLetterOrDigit(line.charAt(pos + currentId.length())) || Character.isLetterOrDigit(line.charAt(pos-1))) {
                                    while (Character.isLetterOrDigit(line.charAt(pos++)));
                                    continue;
                                }
                            } else if (Character.isLetterOrDigit(line.charAt(pos + currentId.length()))) {
                                while (Character.isLetterOrDigit(line.charAt(pos++)));
                                continue;
                            }
                        } else if (Character.isLetterOrDigit(line.charAt(pos-1))) {
                            while (Character.isLetterOrDigit(line.charAt(pos++)));
                            continue;
                        }
                        spenList.get(i).count++;
                        pos += currentId.length();
                    }
                }*/
                spenList.get(i).count += checkContains(line, currentId);
            }
        }
    }

    private static int checkContains(String line, String var){
        int count = 0;
        if (line.contains(var)) {
            line = line.trim();
            int pos = 0;
            while (true) {
                pos = line.indexOf(var, pos);
                if (pos == -1)
                    break;
                if (pos + var.length() != line.length()) {
                    if (pos != 0) {
                        if (Character.isLetterOrDigit(line.charAt(pos + var.length())) || Character.isLetterOrDigit(line.charAt(pos-1))) {
                            while (Character.isLetterOrDigit(line.charAt(pos++)));
                            continue;
                        }
                    } else if (Character.isLetterOrDigit(line.charAt(pos + var.length()))) {
                        while (Character.isLetterOrDigit(line.charAt(pos++)));
                        continue;
                    }
                } else if (Character.isLetterOrDigit(line.charAt(pos-1))) {
                    while (Character.isLetterOrDigit(line.charAt(pos++)));
                    continue;
                }
                count++;
                pos += var.length();
            }
        }
        return count;
    }

}
