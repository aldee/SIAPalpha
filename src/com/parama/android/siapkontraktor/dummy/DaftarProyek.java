package com.parama.android.siapkontraktor.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaftarProyek {
	public static List<Proyek> ITEMS = new ArrayList<Proyek>();
	public static Map<String, Proyek> ITEM_MAP = new HashMap<String, Proyek>();
	
	static {
		addItem(new Proyek("1", "Rumah", "212", "200000"));
	}
	
	public static void addItem(Proyek item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}
	
	public static class Proyek {
		public String id;
		public String nama;
		public String kode;
		public String nilai;
		
		public Proyek(String id,String nama,String kode,String nilai) {
			this.id = id;
			this.nama = nama;
			this.kode = kode;
			this.nilai = nilai;
		}
		
		public String toString() {
			return nama;
		}
	}
	
}
