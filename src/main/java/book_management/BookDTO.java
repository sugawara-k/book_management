package book_management;

import java.io.Serializable;
import java.util.ArrayList;

public class BookDTO implements Serializable {
	private ArrayList<BookBean> list;
	
	public BookDTO() {
		list = new ArrayList<BookBean>();
	}
	
	public void add(BookBean bb) {
		list.add(bb);
	}
	
	public BookBean get(int i) {
		return list.get(i);
	}
	
	public int size() {
		return list.size();
	}

}
