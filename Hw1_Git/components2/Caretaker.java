package components2;


public class Caretaker {
		private Momento state;

	   public void add(Momento state){
	      this.state = state;
	   }

	   public Momento getLastState(){
	      return state;
	   }
}
