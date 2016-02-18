
import java.util.*;
import java.lang.*;
import java.io.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class WaititemList implements Serializable {

    private static final long serialVersionUID = 1L;
    private List waititemList = new LinkedList();
    private static WaititemList WaititemList;

    private WaititemList() {
    }

    public static WaititemList instance() {
        if (WaititemList == null) {
            return (WaititemList = new WaititemList());
        } else {
            return WaititemList;
        }
    }

    public boolean insertWaititem(Waititem waititem) {
        waititemList.add(waititem);
        return true;
    }

    public Iterator getWaititemList() {
        return waititemList.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(WaititemList);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (WaititemList != null) {
                return;
            } else {
                input.defaultReadObject();
                if (WaititemList == null) {
                    WaititemList = (WaititemList) input.readObject();
                } else {
                    input.readObject();
                }
            }
        } catch (IOException ioe) {
            System.out.println("in WaititemList readObject \n" + ioe);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String toString() {
        return waititemList.toString();
    }

    public static Waititem checkWaititem(String waititemName) {
        Waititem tempWaititem = null;
        WaititemList waititemList = WaititemList.instance();
        Iterator ProductList = waititemList.getWaititemList();
        while (ProductList.hasNext()) {
            Waititem waititemCheck = (Waititem) ((Iterator) WaititemList).next();
            if (waititemName.equals(waititemCheck.getWaititemName())) {
                tempWaititem = waititemCheck;
                break;
            }
        }
        return tempWaititem;

    }

}
