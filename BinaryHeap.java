/**
 * Binary Heap Data Structure
 * @reference http://www.sanfoundry.com/java-program-implement-binary-heap/
 * 
 */

import java.util.*;

public class BinaryHeap
{
    //To make Huffman Tree Nodes
    class HuffmanTree{
    String input;
    int frequency;
    HuffmanTree left;
    HuffmanTree right;

        public HuffmanTree(String key, int freq) 
        {
            this.input = key;
            this.frequency = freq;
            this.left = null;
            this.right = null;
        }    
    }
    
    //Binary Node data
    private static final int d = 2;                                                                //Number of children a node can have
    public int heapSize;
    private HuffmanTree[] heap;
    int capacity=2000000;
    
    /** Constructor **/
    public BinaryHeap()
    {
        heapSize = 0;
        heap = new HuffmanTree[capacity + 1];
    }
 
    /** Function to check if heap is empty **/
    public boolean isEmpty( )
    {
        return heapSize == 0;
    }
 
    /** Check if heap is full **/
    public boolean isFull( )
    {
        return heapSize == heap.length;
    }
 
    /** Function to  get index parent of i **/
    private int parent(int i) 
    {
        return (i - 1)/d;
    }
 
    /** Function to get index of k th child of i **/
    private int kthChild(int i, int k) 
    {
        return d * i + k;
    }
 
    /** Function to insert element
     * @param key
     * @param freq
     * @param ht1
     * @param ht2 */
    public void insert(String key, int freq, HuffmanTree ht1, HuffmanTree ht2)
    {
        HuffmanTree x = new HuffmanTree(key,freq);
        x.left=ht1;
        x.right=ht2;
        if (isFull( ) )
            throw new NoSuchElementException("Overflow Exception");
        /** Percolate up **/
        heap[heapSize++] = x;
        heapifyUp(heapSize - 1);
    }
 
    /** Function to find least element **/
    public int findMin(int from, int to )
    {
        int minchild = from;
        for(int i = from+1;(i<=to&&i<heapSize);i++){
            if (heap[minchild].frequency>heap[i].frequency){
                minchild=i;
            }
        }
        return minchild;
    }
 
    /** Function to delete min element **/
    public HuffmanTree deleteMin()
    {
        HuffmanTree keyItem = heap[0];
        delete(0);
        return keyItem;
    }
 
    /** Function to delete element at an index **/
    public HuffmanTree delete(int ind)
    {
        if (isEmpty() )
            throw new NoSuchElementException("Underflow Exception");
        HuffmanTree keyItem = heap[ind];
        heap[ind] = heap[heapSize - 1];
        heapSize--;
        heapifyDown(ind);        
        return keyItem;
    }
 
    /** Function heapifyUp  **/
    private void heapifyUp(int childInd)
    {
        HuffmanTree tmp = heap[childInd];    
        while (childInd > 0 && tmp.frequency < heap[parent(childInd)].frequency )
        {
            heap[childInd] = heap[ parent(childInd) ];
            childInd = parent(childInd);
        }                   
        heap[childInd] = tmp;
    }
 
    /** Function heapifyDown **/
    private void heapifyDown(int ind)
    {
        int minchild = findMin(ind*d+1,ind*d+d);
       
        HuffmanTree tmp = heap[ ind ];
        while (minchild < heapSize&& heap[minchild].frequency<tmp.frequency)
        {   heap[ind] = heap[minchild];
        ind = minchild;
        minchild = findMin(minchild*d+1,minchild*d+d);
        }
        heap[ind] = tmp;
    }
 
    /** Function to get smallest child **/
    private int minChild(int ind) 
    {
        int bestChild = kthChild(ind, 1);
        int k = 2;
        int pos = kthChild(ind, k);
        while ((k <= d) && (pos < heapSize)) 
        {
            if (heap[pos].frequency < heap[bestChild].frequency) 
                bestChild = pos;
            pos = kthChild(ind, k++);
        }    
        return bestChild;
    }
}