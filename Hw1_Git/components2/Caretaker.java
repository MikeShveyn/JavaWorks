package components2;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
	private List<Momento> mementoList = new ArrayList<Momento>();

	   public void add(Momento state){
	      mementoList.add(state);
	   }

	   public Momento get(int index){
	      return mementoList.get(index);
	   }
}
