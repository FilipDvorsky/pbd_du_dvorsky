package sk.upjs.gursky.pdb;

import java.nio.ByteBuffer;

import sk.upjs.gursky.bplustree.BPKey;

public class PersonSalaryKey implements BPKey<PersonSalaryKey>{

	private static final long serialVersionUID = 1618843651920973765L;

	private int key;
	
	public PersonSalaryKey() {}
	
	public PersonSalaryKey(int key) {
		this.key = key;
	}
	
	@Override
	public int compareTo(PersonSalaryKey personSalaryKey) {
		return Integer.compare(this.key, personSalaryKey.key);
	}

	@Override
	public void load(ByteBuffer bb) {
		key = bb.getInt();
	}

	@Override
	public void save(ByteBuffer bb) {
		bb.putInt(key);
	}

	@Override
	public int getSize() {
		// int = 4 byte
		return 4;
	}

}
