package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Chepin {

    public static String valLine = "\\s*val\\s*\\w*\\s*:.*";
    public static String varLine = "\\s*var\\s*\\w*\\s*:.*";
    public static String equals = ".*=.*";

    private static Context[] contexts = {
            new Context(Context.Type.P,
                    new String[]{"\\s*.*\\s*=\\s*Console.in.read\\(\\)\\s*"}),
            new Context(Context.Type.M,
                    new String[]{varLine, "[a-zA-Z[\\s*][\\+][\\*][\\-][\\/][\\=]]", "\\s*print\\s*(.*).*", "\\s*println\\s*(.*).*"}),
            new Context(Context.Type.C,
                    new String[]{"\\s*if\\s*(.*).*", "\\s*for\\s*(.*).*", "\\s*while\\s*(.*).*", "\\s*\\w*\\s*match.*"}),
            new Context(Context.Type.T,
                    new String[]{}),
    };

    public static ObservableList<String> getContext(ObservableList<String>code, String var) {
        ObservableList<String> result = FXCollections.observableArrayList();
        for (String line : code) {
            if (checkContains(line, var))
                result.add(line);
        }
        return result;
    }

    public static void splitVariables(ObservableList<Spen> varList, ObservableList<String> code) {
        for (Spen s: Metrics.spenList) {
            String currentId = s.identifier;
            if (isP(currentId)) {
                Context c = new Context(currentId, Context.Type.P);
                c.isIO = false;
                Metrics.pList.add(c);
            } else if (isM(currentId)) {
                Context c = new Context(currentId, Context.Type.M);
                c.isIO = false;
                Metrics.mList.add(c);
            } else if (isC(currentId)) {
                Context c = new Context(currentId, Context.Type.C);
                c.isIO = false;
                Metrics.cList.add(c);
            } else {
                Context c = new Context(currentId, Context.Type.T);
                c.isIO = false;
                Metrics.tList.add(c);
            }
        }
    }

    private static ObservableList<Spen> getIOVars(ObservableList<Spen> fullList) {
        ObservableList<Spen> result = FXCollections.observableArrayList();
        for (Spen s : fullList) {
            for (String line : Metrics.code) {
                if (checkContains(line, s.identifier) && (line.matches(contexts[0].context[0]) || line.matches(contexts[1].context[1]) || line.matches(contexts[1].context[2]))
                && !line.matches(".*\\(" + s.identifier + "\\).*")) {
                    result.add(s);
                    break;
                }
            }
        }
        return result;
    }

    public static void splitIOVariables(ObservableList<Spen> varList, ObservableList<String> code) {
        for (Spen s: getIOVars(Metrics.spenList)) {
            String currentId = s.identifier;
            if (isP(currentId)) {
                Context c = new Context(currentId, Context.Type.P);
                c.isIO = true;
                Metrics.pList.add(c);
            } else if (isM(currentId)) {
                Context c = new Context(currentId, Context.Type.M);
                c.isIO = true;
                Metrics.mList.add(c);
            } else if (isC(currentId)) {
                Context c = new Context(currentId, Context.Type.C);
                c.isIO = true;
                Metrics.cList.add(c);
            } else {
                Context c = new Context(currentId, Context.Type.T);
                c.isIO = true;
                Metrics.tList.add(c);
            }
        }
    }

    private static boolean isP(String id) {
        boolean isVal = false;
        boolean cir = false;
        boolean afterEquals = false;
        for (String line : getContext(Metrics.code, id)) {
            if (line.matches(valLine)) {
                isVal = true;
                break;
            }
        }
        for (String line : getContext(Metrics.code, id)) {
            if (line.matches(contexts[0].context[0])) {
                cir = true;
                break;
            }
        }
        for (String line : getContext(Metrics.code, id)) {
            if (line.matches(equals + id + ".*")) {
                afterEquals = true;
                break;
            }
        }
        return cir && isVal && afterEquals;
    }

    private static boolean isM(String id) {
        boolean isVar = false;
        boolean counted = false;
        boolean afterEquals= false;
        boolean printed = false;
        boolean isControlling = false;
        for (String line : getContext(Metrics.code, id)) {
            if (line.matches(varLine)) {
                isVar = true;
                break;
            }
        }
        for (String line : getContext(Metrics.code, id)) {
            if (line.matches(contexts[1].context[0])) {
                counted = true;
                break;
            }
        }
        for (String line : getContext(Metrics.code, id)) {
            if (line.matches(contexts[1].context[1]) || line.matches(contexts[1].context[2])) {
                printed = true;
                break;
            }
        }
        for (String line : getContext(Metrics.code, id)) {
            if (line.matches(equals + id + ".*")) {
                afterEquals = true;
                break;
            }
        }
        for (String line : getContext(Metrics.code, id)) {
            if (line.matches(contexts[2].context[0]) || line.matches(contexts[2].context[1])
                    || line.matches(contexts[2].context[2]) || line.matches(contexts[2].context[3])) {
                isControlling = true;
                break;
            }
        }
        return (isVar && counted && printed && !isControlling) || (isVar && counted && afterEquals && !isControlling) || (isVar && counted && isControlling && printed);
    }

    private static boolean isC(String id) {
        boolean isVar = false;
        boolean isControlling = false;
        for (String line : getContext(Metrics.code, id)) {
            if (line.matches(varLine)) {
                isVar = true;
                break;
            }
        }
        for (String line : getContext(Metrics.code, id)) {
            if (line.matches(contexts[2].context[0]) || line.matches(contexts[2].context[1])
                    || line.matches(contexts[2].context[2]) || line.matches(contexts[2].context[3])) {
                isControlling = true;
                break;
            }
        }
        return isVar && isControlling;
    }

    private static boolean checkContains(String line, String var) {
        boolean contains = false;
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
                pos += var.length();
                return true;
            }
        }
        return false;
    }

    public static class Context {

        String identifier;
        Type type;
        String[] context;
        boolean isIO;

        public Context(String id, Type type) {
            this.identifier = id;
            this.type = type;
        }

        public Context(Type type, String[] context) {
            this.type = type;
            this.context = context;
        }

        enum Type {
            P,
            M,
            C,
            T
        }
    }
}
