package sk.upjs.gursky.pdb;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sk.upjs.gursky.bplustree.BPTree;

public class ClusteredBPTree extends BPTree<PersonStringKey, PersonEntry>{

	public static final File INDEX_FILE = new File("person.tree");
	public static final File INPUT_INDEX_FILE = new File("person.tab");
 
	
	public ClusteredBPTree() throws IOException {
		super(PersonEntry.class, INDEX_FILE);	
	}
	
	public static ClusteredBPTree createOneByOne() throws IOException {
		long startTime = System.nanoTime();
		ClusteredBPTree tree = new ClusteredBPTree();
		tree.openNewFile();
		
		RandomAccessFile raf = new RandomAccessFile(INPUT_INDEX_FILE, "r");
		
		FileChannel channel = raf.getChannel();
		ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
		
		long fileSize = INPUT_INDEX_FILE.length();
		for(int offset = 0; offset < fileSize; offset += 4096) {
			System.out.println("processing page "+ (offset/4096));
			buffer.clear();
			channel.read(buffer, offset);
			buffer.rewind();
			int numberOfRecords = buffer.getInt();
			for (int i =0; i< numberOfRecords; i++) {
				PersonEntry entry = new PersonEntry();
				entry.load(buffer);
				tree.add(entry);
			}
		}
		
		channel.close();
		raf.close();
		System.out.println("Index created in " + (System.nanoTime() -startTime)/1_000_000.0 + " ms");
		return tree;
	}
	
	public static ClusteredBPTree createByBulkLoading() throws IOException{
		long startTime = System.nanoTime();
		ClusteredBPTree tree = new ClusteredBPTree();
		tree.openNewFile();
		
		RandomAccessFile raf = new RandomAccessFile(INPUT_INDEX_FILE, "r");
		
		List<PersonEntry> entries = new ArrayList<PersonEntry>();
		
		FileChannel channel = raf.getChannel();
		ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
		
		long fileSize = INPUT_INDEX_FILE.length();
		for(int offset = 0; offset < fileSize; offset += 4096) {
			System.out.println("processing page "+ (offset/4096));
			buffer.clear();
			channel.read(buffer, offset);
			buffer.rewind();
			int numberOfRecords = buffer.getInt();
			for (int i =0; i< numberOfRecords; i++) {
				PersonEntry entry = new PersonEntry();
				entry.load(buffer);
				entries.add(entry);
			}
		}
		Collections.sort(entries);
		tree.openAndBatchUpdate(entries.iterator(), entries.size());
		channel.close();
		raf.close();
		System.out.println("Index created in " + ((System.nanoTime() - startTime)/1_000_000.0) + " ms");
		
		return tree;
	}
}
