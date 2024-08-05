package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.vo.Item3;

public interface IItemService3 {
	void register(Item3 item);
	List<Item3> list();
	Item3 read(int itemId);
	List<String> getAttach(int itemId);
	void modify(Item3 item);
	void remove(int itemId);
	
}
