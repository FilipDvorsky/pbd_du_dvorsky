package sk.upjs.gursky.pdb;

import java.nio.ByteBuffer;

import sk.upjs.gursky.bplustree.BPObject;

public class SalaryOffsetEntry implements BPObject<PersonSalaryKey, SalaryOffsetEntry>{

	private static final long serialVersionUID = 8379704066628996764L;
	int salary;
	long offset;
	
	public SalaryOffsetEntry() {
		
	}

	public SalaryOffsetEntry(int salary, long offset) {
		this.salary = salary;
		this.offset = offset;
	}
	
	@Override
	public int compareTo(SalaryOffsetEntry o) {
		return Integer.compare(this.salary, o.salary);
	}

	@Override
	public void load(ByteBuffer bb) {
		salary = bb.getInt();
		offset = bb.getLong();
	}

	@Override
	public void save(ByteBuffer bb) {
		bb.putInt(salary);
		bb.putLong(offset);
	}

	@Override
	public int getSize() {
		// int (4B) + long (8B) = 12B
		return 12;
	}

	@Override
	public PersonSalaryKey getKey() {
		return new PersonSalaryKey(salary);
	}

}
