// implements hash table with linear probing
import java.io.*;
import java.util.*;
import java.util.Scanner;

class DataItem {
    private int iData; // data item (key)

    public DataItem(int ii) // constructor
    {
        iData = ii;
    }

    public int getKey()	// getter
    {
        return iData;
    }

    //setter
    public void setKey(int newData) {
        iData = newData;
    }

} // end class DataItem

class HashTable {

    private DataItem[] hashArray; // array holds hash table
    private int arraySize;


    public HashTable(int size) // constructor
    {
        arraySize = size;
        hashArray = new DataItem[size];
    }

    public void displayTable()	// displays hash table
    {
       for (int i = 0; i < arraySize; i++) {
           if (hashArray[i] != null) {
               System.out.print(hashArray[i].getKey() + " ");
           }
           else {
               System.out.print("** ");
           }
        }
    }

    public int hashFunc(int key) // hash function
    {
        int value = key % arraySize;
        return value;
    }

    public void insert(DataItem item) {
        int value = hashFunc(item.getKey());
        int valueStatic = value;
        while (hashArray[value] != null) {
            if (hashArray[value].getKey() == item.getKey()) {
                return; // key already exists in hash table, do not insert
            }
            value += 1;
            value %= arraySize;
            if (value == valueStatic) {
                arraySize += 13;
                hashArray = Arrays.copyOf(hashArray, arraySize);
                rehash();
            }
        }
        hashArray[value] = item;
    }

    //rehash method
    public void rehash() {
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null) {
                int value = hashFunc(hashArray[i].getKey());
                int valueStatic = value;
                while (hashArray[value] != null) {
                    value += 1;
                    value %= arraySize;
                    if (value == valueStatic) {
                        arraySize += 13;
                        hashArray = Arrays.copyOf(hashArray, arraySize);
                        rehash();
                    }
                }
                hashArray[value] = hashArray[i];
                hashArray[i] = null;
            }
        }
    }
  

    public void delete(int key) // delete a DataItem
    {
        if (find(key) != null) {
            find(key).setKey(-1);
        }
    }


    public DataItem find(int key) // find item with key
    {
          int value = hashFunc(key);
            int valueStatic = value;
            while (hashArray[value] != null) {
                if (hashArray[value].getKey() == key) {
                    return hashArray[value];
                }
                value += 1;
                value %= arraySize;
                if (value == valueStatic) {
                    return null;
                }
            }
            return null;
    }
} // end class HashTable

class HashTableApp {
    public static void main(String[] args) throws IOException {
        HashTable hash = new HashTable(17);
        while(true) // interact with user
        {
            System.out.print("Enter first letter of ");
            System.out.print("show, insert, delete, or find: ");
            Scanner s = new Scanner(System.in);
            String choice = " ";
            choice = s.nextLine();
            switch(choice) {
                case "s":
                    hash.displayTable();
                    break;

                case "i":
                    System.out.print("Enter key value to insert: ");
                    hash.insert(new DataItem(s.nextInt()));
                    break;

                case "d":
                    System.out.print("Enter key value to delete: ");
                    hash.delete(s.nextInt());
                    break;

                case "f":
                    System.out.print("Enter key value to find: ");
                    DataItem hashFind = hash.find(s.nextInt());
                    if (hashFind != null) {
                        System.out.println("Found " + hashFind.getKey());
                    }
                    else {
                        System.out.println("Not found");
                    }
                    break;

                default:
                    System.out.print("Invalid entry\n");

            } // end switch

        } // end while

    } // end main()


} // end class HashTableApp
