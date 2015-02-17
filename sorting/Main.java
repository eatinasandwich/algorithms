import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {
	public static void main(String[] args) {
		List<Integer> my_list = new ArrayList<Integer>();
		Random r = new Random();
		for (int i = 0; i < 1000; i++) {
			my_list.add(r.nextInt(10000));
		}
//		my_list = mergeSort(my_list);
//		bubbleSort(my_list);
		selectionSort(my_list);
		System.out.println(my_list);
		boolean sorted = true;
		for (int i = 1; i < my_list.size(); i++) {
			if (my_list.get(i) < my_list.get(i - 1)) {
				sorted = false;
			}
		}
		System.out.println(sorted);
	}
	
	
	public static List<Integer> mergeSort(List<Integer> list) {
		
		List<Integer> left, right;
		if (list.size() > 1) {
			left = list.subList(0, list.size() / 2);
			right = list.subList(list.size() / 2, list.size());
			left = mergeSort(left);
			right = mergeSort(right);
			return merge(left, right);
		} else {
			return list;
		}
	}
	
	
	private static List<Integer> merge(List<Integer> left_list, List<Integer> right_list) {
		List<Integer> new_list = new ArrayList<Integer>();
		
		int left_index = 0, right_index = 0;
		while (left_index < left_list.size() && right_index < right_list.size()) {
			if (left_list.get(left_index) < right_list.get(right_index)) {
				new_list.add(left_list.get(left_index++));
			} else {
				new_list.add(right_list.get(right_index++));
			}
		}
		
		while (left_index < left_list.size()) {
			new_list.add(left_list.get(left_index++));
		}
		
		while (right_index < right_list.size()) {
			new_list.add(right_list.get(right_index++));
		}
		
		return new_list;
	}
	
	
	public static void bubbleSort(List<Integer> list) {
		
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 0; j < list.size() - i - 1; j++) {
				if (list.get(j) > list.get(j + 1)) {
					swap(list, j, j + 1);
				}
			}
		}
	}
	
	public static void selectionSort(List<Integer> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			int lowest = i; //keeps track of the index of the lowest element found so far
			for (int j = i; j < list.size(); j++) {
				if (list.get(j) < list.get(lowest)) {
					lowest = j;
				}
			}
			swap(list, i, lowest);
		}
	}
	
	public static void swap(List<Integer> list, int index1, int index2) {
		int temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}
}
