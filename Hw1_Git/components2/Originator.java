package components2;

public class Originator {
	
	private String state;

	   public void setState(String state){
	      this.state = state;
	   }

	   public String getState(){
	      return state;
	   }

	   public Momento saveStateToMemento(){
	      return new Momento(state);
	   }

	   public void getStateFromMemento(Momento Memento){
	      state = Memento.getState();
	   }
}
