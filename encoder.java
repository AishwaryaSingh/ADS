/**
 * Encoder
 * @author Aishwarya Singh
 */

import java.io.*;
import java.util.*;


public class encoder extends BinaryHeap                                                         //uses huffman tree
{
    static HashMap<String, String> code_table = new HashMap<String, String>();
    static HashMap<String, Integer> frequency_table = new HashMap<String, Integer>();
    static HuffmanTree root;
    static String s = null;
    
    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        long start = System.nanoTime();
        
        FileReader input_file = new FileReader (args[0]);                                       //read input from system       
        BufferedReader input = new BufferedReader(input_file);
        build_frequency_table(input);                                                           //to build frequency table
        input_file.close();

        BinaryHeap bh = new BinaryHeap();
        for(String c: frequency_table.keySet())
        {
            bh.insert(c,frequency_table.get(c),null,null);
        }
        root = build_huffman_tree(bh);

        FileWriter fw = new FileWriter("code_table.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        StringBuilder code = new StringBuilder();
        build_code_table(root, bw, code);
        bw.flush();
        bw.close();

        BufferedOutputStream output_file = new BufferedOutputStream(new FileOutputStream("encoded.bin"));
        FileReader input_data = new FileReader (args[0]);       
        encode_input(code_table, input_data, output_file);
        output_file.flush();
        output_file.close();
        input_data.close();

        long elapsed = System.nanoTime() - start;
        System.out.println("Encoding time : "+elapsed);
    }

    //Build Frequency Table : HashMap
    public static void build_frequency_table(BufferedReader input_file) throws IOException
    {
        while((s=input_file.readLine()) != null )                                               //map key to frequency
        {	
            String word = s;
            if(!frequency_table.containsKey(word))
                frequency_table.put(word, 1);
            else
                frequency_table.put(word, frequency_table.get(word) + 1);
        }
    }

    //Build Code Table : HashMap
    public static void build_code_table(HuffmanTree node, BufferedWriter bw, StringBuilder code) throws IOException
    {   
        StringBuilder s1,s2;
        if (node.left == null && node.right == null)
        {	
            String make_null = "";
            bw.write(node.input+" "+code.toString()+"\n");
            code_table.put(node.input,code.toString());
            code = new StringBuilder(make_null);
        }
        if(node.left!= null)
        {   
            s1 = new StringBuilder(code);
            s1.append("0");
            build_code_table(node.left, bw, s1);
        }
        if(node.right != null)
        {
            s2 = new StringBuilder(code);
            s2.append("1");
            build_code_table(node.right, bw, s2);
        }
    }

    public static HuffmanTree build_huffman_tree(BinaryHeap bh)                                 //Extract 2 from Binary Heap and meld to one
    {              
       HuffmanTree ht1 = null;
       HuffmanTree ht2 = null;
       while(bh.heapSize > 1)
       {
            ht1 = bh.deleteMin();
            ht2 = bh.deleteMin();
            bh.insert("internal_node", ht1.frequency + ht2.frequency,ht1,ht2);
       }
       return(bh.deleteMin());
    }

    //To write to file when encoding in "encoding_input"
    public static int bitsToBytes(boolean[] bits)
    {
        if(bits == null || bits.length !=8){}
        int data = 0;
        for(int i =0;i <8; i++)
        {
            if(bits[i])
            data += (1 << (7 - i));
        }
        return data;
    }

    //Encode to give encoded.bin binary file
    public static void encode_input(HashMap code_table, FileReader input_file, BufferedOutputStream output_file) throws FileNotFoundException, IOException
    {
        BufferedReader input_data = new BufferedReader(input_file);
        boolean [] bits = new boolean[8];
        String encoded = "";
        while((s=input_data.readLine()) != null)
        {
            encoded = encoded + code_table.get(s);
            while(encoded.length() >= 8)
            {
                for(int i = 0 ; i < 8; i++)
                {  
                    if(encoded.charAt(i) == '1')
                        bits[i]=true;
                    else
                        bits[i]=false;
                }
                encoded = encoded.substring(8);
                output_file.write(bitsToBytes(bits));
            }               
        }
    }
}