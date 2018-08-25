/**
 * Decoder
 * @author Aishwarya Singh
 */

import java.io.*;

public class decoder extends BinaryHeap 
{
    static HuffmanTree root = null;
    static BinaryHeap bh = new BinaryHeap();
    static String s = null;

    public static void main(String args[]) throws FileNotFoundException, IOException
    {    
        long start = System.nanoTime();
        
        BufferedInputStream  input_file = new BufferedInputStream( new FileInputStream(args[0]));    
        FileReader code_table = new FileReader(args[1]);
        build_decoded_tree(code_table);
        FileWriter fw = new FileWriter("decoded.txt");
        decode_input(input_file, fw);
        fw.flush();
        fw.close();
        input_file.close();
        
        long elapsed = System.nanoTime() - start;
        System.out.println("Decoding time : "+elapsed);
    }

    //To Construct decoded tree
    public static void build_decoded_tree(FileReader input_file) throws FileNotFoundException, IOException
    {
        String key = "", code = "";
        BufferedReader input = new BufferedReader(input_file);
        HuffmanTree node = null;
        while((s=input.readLine()) != null)
        {   
            String splitArray[] = s.split(" ");
            key = splitArray[0];
            code = splitArray[1];
            
            if(root == null)
            {
                root= bh.new HuffmanTree("i",1);
            }
            node = root;
            for(int i=0; i < code.length(); i++)
            {
                if(code.charAt(i) == '0')
                {
                    if(node.left != null)
                    {
                        node = node.left;
                    }
                    else
                    {
                        HuffmanTree temp = bh.new HuffmanTree("i",1 );
                        node.left=temp;
                        node=temp;
                    }
                }
                else
                {
                    if(node.right != null)
                    {
                        node = node.right;
                    }
                    else
                    {
                        HuffmanTree temp = bh.new HuffmanTree("i",1 );
                        node.right=temp;
                        node=temp;
                    }
                }
            }
            node.input = key;
            node.left = null;
            node.right = null;
        }
        input.close();
    }

    //To Decode input
    public static void decode_input(BufferedInputStream input_file,FileWriter fw) throws IOException
    {
        HuffmanTree node = null;
        byte[] fileData = new byte[(int) input_file.available()];
        node = root;
        boolean bit[]= new boolean[8];
        
        while((input_file.read(fileData)) != -1){}
        for(byte b : fileData)
        {
            for(int k=0;k<8;k++){
                if((b&(1<<(7-(k))))>0)
                    bit[k]=true;
                else
                    bit[k]=false;
            }
                
            for(int q=0;q<bit.length;q++)
            {
                if(!bit[q])
                {
                    node = node.left;
                }
                if(bit[q]){
                    node = node.right;
                }
                if(node.left == null && node.right == null)
                {
                    fw.write(node.input+"\n");
                    node=root;
                }
            }
        }
    }
}
